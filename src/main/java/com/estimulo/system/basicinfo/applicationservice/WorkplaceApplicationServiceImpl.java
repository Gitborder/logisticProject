package com.estimulo.system.basicinfo.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.system.base.dao.CodeDetailDAO;
import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.basicinfo.dao.WorkplaceDAO;
import com.estimulo.system.basicinfo.to.WorkplaceTO;

@Component
public class WorkplaceApplicationServiceImpl implements WorkplaceApplicationService {
	
	// 참조변수 선언
	@Autowired
	private WorkplaceDAO workplaceDAO;
	@Autowired
	private CodeDetailDAO codeDetailDAO;

	public ArrayList<WorkplaceTO> getWorkplaceList(String companyCode) {
		System.out.println(companyCode);
		ArrayList<WorkplaceTO> workplaceList = workplaceDAO.selectWorkplaceList(companyCode);
		return workplaceList;
	}

	public String getNewWorkplaceCode(String companyCode) {
		ArrayList<WorkplaceTO> workplaceList = null;
		String newWorkplaceCode = null;

		workplaceList = workplaceDAO.selectWorkplaceList(companyCode);

		TreeSet<Integer> workplaceCodeNoSet = new TreeSet<>();

		for (WorkplaceTO bean : workplaceList) {

			if (bean.getWorkplaceCode().startsWith("BRC-")) {

				try {

					Integer no = Integer.parseInt(bean.getWorkplaceCode().split("BRC-")[1]);
					workplaceCodeNoSet.add(no);

				} catch (NumberFormatException e) {

					// "BRC-" 다음 부분을 Integer 로 변환하지 못하는 경우 : 그냥 다음 반복문 실행

				}

			}

		}

		if (workplaceCodeNoSet.isEmpty()) {
			newWorkplaceCode = "BRC-" + String.format("%02d", 1);
		} else {
			newWorkplaceCode = "BRC-" + String.format("%02d", workplaceCodeNoSet.pollLast() + 1);
		}
		return newWorkplaceCode;
	}

	public HashMap<String, Object> batchWorkplaceListProcess(ArrayList<WorkplaceTO> workplaceList) {
		HashMap<String, Object> resultMap = new HashMap<>();

		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		CodeDetailTO detailCodeBean = new CodeDetailTO();

		for (WorkplaceTO bean : workplaceList) {

			String status = bean.getStatus();

			switch (status) {

			case "INSERT":

				// 새로운 사업장번호 생성 후 bean 에 set
				String newWorkplaceCode = getNewWorkplaceCode(bean.getCompanyCode());
				bean.setWorkplaceCode(newWorkplaceCode);

				// 사업장 테이블에 insert
				workplaceDAO.insertWorkplace(bean);
				insertList.add(bean.getWorkplaceCode());

				// CODE_DETAIL 테이블에 Insert
				detailCodeBean.setDivisionCodeNo("CO-02");
				detailCodeBean.setDetailCode(bean.getWorkplaceCode());
				detailCodeBean.setDetailCodeName(bean.getWorkplaceName());

				codeDetailDAO.insertDetailCode(detailCodeBean);

				break;

			case "UPDATE":

				workplaceDAO.updateWorkplace(bean);
				updateList.add(bean.getWorkplaceCode());

				// CODE_DETAIL 테이블에 Update
				detailCodeBean.setDivisionCodeNo("CO-02");
				detailCodeBean.setDetailCode(bean.getWorkplaceCode());
				detailCodeBean.setDetailCodeName(bean.getWorkplaceName());

				codeDetailDAO.updateDetailCode(detailCodeBean);

				break;

			case "DELETE":

				workplaceDAO.deleteWorkplace(bean);
				deleteList.add(bean.getWorkplaceCode());

				// CODE_DETAIL 테이블에 Delete
				detailCodeBean.setDivisionCodeNo("CO-02");
				detailCodeBean.setDetailCode(bean.getWorkplaceCode());
				detailCodeBean.setDetailCodeName(bean.getWorkplaceName());

				codeDetailDAO.deleteDetailCode(detailCodeBean);

				break;

			}

		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);

		return resultMap;
	}
	
}
