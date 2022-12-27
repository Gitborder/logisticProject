package com.estimulo.hr.affair.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.hr.affair.dao.EmpSearchingDAO;
import com.estimulo.hr.affair.dao.EmployeeBasicDAO;
import com.estimulo.hr.affair.dao.EmployeeDetailDAO;
import com.estimulo.hr.affair.dao.EmployeeSecretDAO;
import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.hr.affair.to.EmployeeBasicTO;
import com.estimulo.hr.affair.to.EmployeeDetailTO;
import com.estimulo.hr.affair.to.EmployeeSecretTO;
import com.estimulo.system.base.dao.CodeDetailDAO;
import com.estimulo.system.base.to.CodeDetailTO;

@Component
public class EmpApplicationServiceImpl implements EmpApplicationService {
	
	// DAO 참조변수 선언
	@Autowired
	private EmployeeBasicDAO empBasicDAO;
	@Autowired
	private EmployeeDetailDAO empDetailDAO;
	@Autowired
	private EmployeeSecretDAO empSecretDAO;
	@Autowired
	private EmpSearchingDAO empSearchDAO;
	@Autowired
	private CodeDetailDAO codeDetailDAO;

	@Override
	public ArrayList<EmpInfoTO> getAllEmpList(String searchCondition, String[] paramArray) {
		ArrayList<EmpInfoTO> empList = null;
		HashMap<String,String> map=new HashMap<>();
		map.put("searchCondition",searchCondition);
		
		for (int i = 0; i < paramArray.length; i++) {
			switch (i + "") {
			case "0":
				map.put("companyCode", paramArray[0]);
				break;
			case "1":
				map.put("workplaceCode", paramArray[1]);
				break;
			case "2":
				map.put("deptCode", paramArray[2]);
				break;
			}
		}
		
		empList = empSearchDAO.selectAllEmpList(map);
		
		for (EmpInfoTO bean : empList) {
			map.clear();
			map.put("companyCode", bean.getCompanyCode());
			map.put("empCode", bean.getEmpCode());
			
			bean.setEmpDetailTOList(
					empDetailDAO.selectEmployeeDetailList(map));

			bean.setEmpSecretTOList(
					empSecretDAO.selectEmployeeSecretList(map));
		}
		return empList;
	}

	@Override
	public EmpInfoTO getEmpInfo(String companyCode, String empCode) {
		EmpInfoTO bean = new EmpInfoTO();
		HashMap<String,String> map=new HashMap<>();

		map.put("companyCode", companyCode);
		map.put("empCode", empCode);
		ArrayList<EmployeeDetailTO> empDetailTOList = empDetailDAO.selectEmployeeDetailList(map);
		ArrayList<EmployeeSecretTO> empSecretTOList = empSecretDAO.selectEmployeeSecretList(map);

		bean.setEmpDetailTOList(empDetailTOList);
		bean.setEmpSecretTOList(empSecretTOList);

		EmployeeBasicTO basicBean = empBasicDAO.selectEmployeeBasicTO(companyCode, empCode);

		if (basicBean != null) {

			bean.setCompanyCode(companyCode);
			bean.setEmpCode(empCode);
			bean.setEmpName(basicBean.getEmpName());
			bean.setEmpEngName(basicBean.getEmpEngName());
			bean.setSocialSecurityNumber(basicBean.getSocialSecurityNumber());
			bean.setHireDate(basicBean.getHireDate());
			bean.setRetirementDate(basicBean.getRetirementDate());
			bean.setUserOrNot(basicBean.getUserOrNot());
			bean.setBirthDate(basicBean.getBirthDate());
			bean.setGender(basicBean.getGender());
		}
		return bean;
	}

	@Override
	public String getNewEmpCode(String companyCode) {
		ArrayList<EmployeeBasicTO> empBasicList = null;
		String newEmpCode = null;
		empBasicList = empBasicDAO.selectEmployeeBasicList(companyCode);

		TreeSet<Integer> empCodeNoSet = new TreeSet<>();

		for (EmployeeBasicTO TO : empBasicList) {
			if (TO.getEmpCode().startsWith("EMP-")) {
				try {
					Integer no = Integer.parseInt(TO.getEmpCode().split("EMP-")[1]);
					empCodeNoSet.add(no);
				} catch (NumberFormatException e) {
					// "EMP-" 다음 부분을 Integer 로 변환하지 못하는 경우 : 그냥 다음 반복문 실행
				}
			}
		}
		if (empCodeNoSet.isEmpty()) {
			newEmpCode = "EMP-" + String.format("%03d", 1);
		} else {
			newEmpCode = "EMP-" + String.format("%03d", empCodeNoSet.pollLast() + 1);
		}
		return newEmpCode;
	}

	@Override
	public HashMap<String, Object> batchEmpBasicListProcess(ArrayList<EmployeeBasicTO> empBasicList) {
		HashMap<String, Object> resultMap = new HashMap<>();
		ArrayList<String> insertList = new ArrayList<>();
		// ArrayList<String> updateList = new ArrayList<>();
		// ArrayList<String> deleteList = new ArrayList<>();

		CodeDetailTO detailCodeTO = new CodeDetailTO();

		for (EmployeeBasicTO TO : empBasicList) {
			String status = TO.getStatus();
			switch (status) {
				case "INSERT":
					empBasicDAO.insertEmployeeBasic(TO);
					insertList.add(TO.getEmpCode());
					// CODE_DETAIL 테이블에 Insert
					detailCodeTO.setDivisionCodeNo("HR-02");
					detailCodeTO.setDetailCode(TO.getEmpCode());
					detailCodeTO.setDetailCodeName(TO.getEmpEngName());
					codeDetailDAO.insertDetailCode(detailCodeTO);
					break;
			}
		}
		resultMap.put("INSERT", insertList);
		// resultMap.put("UPDATE", updateList);
		// resultMap.put("DELETE", deleteList);

		return resultMap;

	}

	@Override
	public HashMap<String, Object> batchEmpDetailListProcess(ArrayList<EmployeeDetailTO> empDetailList) {
		HashMap<String, Object> resultMap = new HashMap<>();
		ArrayList<String> insertList = new ArrayList<>();
		// ArrayList<String> updateList = new ArrayList<>();
		// ArrayList<String> deleteList = new ArrayList<>();

		for (EmployeeDetailTO bean : empDetailList) {
			String status = bean.getStatus();
			switch (status) {
				case "INSERT":
					empDetailDAO.insertEmployeeDetail(bean);
					insertList.add(bean.getEmpCode());
	
					// 사원 계정 정지 => EMPLOYEE_BASIC 테이블의 USER_OR_NOT 컬럼을 "N" 으로 변경
					// 새로운 userPassWord 를 null 로 입력
					if (bean.getUpdateHistory().equals("계정 정지")) {
	
						changeEmpAccountUserStatus(bean.getCompanyCode(), bean.getEmpCode(), "N");
	
						// 사원 계정 정지 => EMPLOYEE_SECRET 테이블에 userPassWord 가 null 인 새로운 EmployeeSecretTO
						// 생성, Insert
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("companyCode", bean.getCompanyCode());
						map.put("empCode", bean.getEmpCode());
						int newSeq = empSecretDAO.selectUserPassWordCount(map);
	
						EmployeeSecretTO newSecretBean = new EmployeeSecretTO();
	
						newSecretBean.setCompanyCode(bean.getCompanyCode());
						newSecretBean.setEmpCode(bean.getEmpCode());
						newSecretBean.setSeq(newSeq);
	
						empSecretDAO.insertEmployeeSecret(newSecretBean);
					}
					break;
			}
		}
		resultMap.put("INSERT", insertList);
		// resultMap.put("UPDATE", updateList);
		// resultMap.put("DELETE", deleteList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchEmpSecretListProcess(ArrayList<EmployeeSecretTO> empSecretList) {
		HashMap<String, Object> resultMap = new HashMap<>();
		ArrayList<String> insertList = new ArrayList<>();
		// ArrayList<String> updateList = new ArrayList<>();
		// ArrayList<String> deleteList = new ArrayList<>();

		for (EmployeeSecretTO TO : empSecretList) {
			String status = TO.getStatus();
			switch (status) {
				case "INSERT":
					empSecretDAO.insertEmployeeSecret(TO);
					insertList.add(TO.getEmpCode());
					break;
				}
		}
		resultMap.put("INSERT", insertList);
		// resultMap.put("UPDATE", updateList);
		// resultMap.put("DELETE", deleteList);
		return resultMap;

	}

	@Override
	public Boolean checkUserIdDuplication(String companyCode, String newUserId) {
		ArrayList<EmployeeDetailTO> empDetailList = null;
		Boolean duplicated = false;
		empDetailList = empDetailDAO.selectUserIdList(companyCode);

		for (EmployeeDetailTO TO : empDetailList) {
			if (TO.getUserId().equals(newUserId)) {
				duplicated = true;
			}
		}
		return duplicated; // 중복된 코드이면 true 반환
	}

	@Override
	public Boolean checkEmpCodeDuplication(String companyCode, String newEmpCode) {
		ArrayList<EmployeeBasicTO> empBasicList = null;
		Boolean duplicated = false;
		empBasicList = empBasicDAO.selectEmployeeBasicList(companyCode);

		for (EmployeeBasicTO TO : empBasicList) {
			if (TO.getEmpCode().equals(newEmpCode)) {
				duplicated = true;
			}
		}
		return duplicated; // 중복된 코드이면 true 반환
	}

	@Override
	public void changeEmpAccountUserStatus(String companyCode, String empCode, String userStatus) {
		empBasicDAO.changeUserAccountStatus(companyCode, empCode, userStatus);
	}
}
