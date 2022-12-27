package com.estimulo.companymanaging.authoritymanager.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.companymanaging.authoritymanager.dao.AuthorityGroupDAO;
import com.estimulo.companymanaging.authoritymanager.dao.MenuAuthorityDAO;
import com.estimulo.companymanaging.authoritymanager.exception.IdNotFoundException;
import com.estimulo.companymanaging.authoritymanager.exception.PwMissMatchException;
import com.estimulo.companymanaging.authoritymanager.exception.PwNotFoundException;
import com.estimulo.companymanaging.authoritymanager.to.AuthorityGroupMenuTO;
import com.estimulo.companymanaging.authoritymanager.to.AuthorityGroupTO;
import com.estimulo.hr.affair.dao.EmpSearchingDAO;
import com.estimulo.hr.affair.dao.EmployeeSecretDAO;
import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.hr.affair.to.EmployeeSecretTO;

@Component
public class LogInApplicationServiceImpl implements LogInApplicationService {
	
	// DAO 참조변수 선언
	@Autowired
	private EmpSearchingDAO empSearchDAO;
	@Autowired
	private EmployeeSecretDAO empSecretDAO;
	@Autowired
	private MenuAuthorityDAO menuAuthorityDAO;
	@Autowired
	private AuthorityGroupDAO authorityGroupDAO;

	@Override
	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException {

		EmpInfoTO bean = checkEmpInfo(companyCode, workplaceCode, inputId);	// 데이터 베이스 에서 우리가 로그인 화면에서 입력한 값을 보내줘서 비교한후 있으면 들고와서 그사람의 정보를 bean 에 담는다
		checkPassWord(companyCode, bean.getEmpCode(), inputPassWord); // 비밀번호를 확인 해주는 메서드
		
		String[] userAuthorityGroupList = getUserAuthorityGroup(bean.getEmpCode()); // 사용자의 권한그룹 리스트를 가져오는 메서드
		bean.setAuthorityGroupList(userAuthorityGroupList); 
		 
		String[] menuList = getUserAuthorityGroupMenu(bean.getEmpCode()); // 권한그룹별 메뉴 리스트를 가져오는 메서드 
		bean.setAuthorityGroupMenuList(menuList); 

		return bean;
	}

	private EmpInfoTO checkEmpInfo(String companyCode, String workplaceCode, String userId)
			throws IdNotFoundException {
		EmpInfoTO bean = null;
		ArrayList<EmpInfoTO> empInfoTOList = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("companyCode", companyCode);
		map.put("workplaceCode", workplaceCode);
		map.put("userId", userId);
		empInfoTOList = empSearchDAO.getTotalEmpInfo(map);
		System.out.println("********************");
		if (empInfoTOList.size() == 1) {

			for (EmpInfoTO e : empInfoTOList) {
				bean = e;
			}

		} else if (empInfoTOList.size() == 0) {
			throw new IdNotFoundException("입력된 정보에 해당하는 사원은 없습니다.");
		}

		return bean;
	}

	private void checkPassWord(String companyCode, String empCode, String inputPassWord)
			throws PwMissMatchException, PwNotFoundException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("companyCode", companyCode);
		map.put("empCode", empCode);
		EmployeeSecretTO bean = empSecretDAO.selectUserPassWord(map);
		
		StringBuffer userPassWord = new StringBuffer();
		if (bean != null) {
			userPassWord.append(bean.getUserPassword());
	
			// 회원ID 는 있으나 passWord Data 가 없는 경우
		} else if (bean == null || bean.getUserPassword().equals("") || bean.getUserPassword() == null) {
			throw new PwNotFoundException("비밀번호 정보를 찾을 수 없습니다.");
		}
	
		if (!inputPassWord.equals(userPassWord.toString())) {
			throw new PwMissMatchException("비밀번호가 가입된 정보와 같지 않습니다.");
		}
	}
		
	private String[] getUserAuthorityGroupMenu(String empCode) {
		String[] authorityGroupMenuList = null;

		ArrayList<AuthorityGroupMenuTO> authorityGroupMenuTOList = menuAuthorityDAO.selectUserMenuAuthorityList(empCode);

		// ArrayList 요소를 배열에 담음 
		int size = authorityGroupMenuTOList.size();
		authorityGroupMenuList = new String[size];
		for(int i=0; i<size; i++ ) { 
			authorityGroupMenuList[i] = authorityGroupMenuTOList.get(i).getMenuCode(); 
		}
		return authorityGroupMenuList;
	}
	
	private String[] getUserAuthorityGroup(String empCode) {
		String[] userAuthorityGroupList = null;
		ArrayList<AuthorityGroupTO> userAuthorityGroupTOList = authorityGroupDAO.selectUserAuthorityGroupList(empCode);
		
		// 해당 사용자에게 부여되지 않은 권한그룹 목록 삭제 
		Iterator<AuthorityGroupTO> iter=userAuthorityGroupTOList.iterator();
		while(iter.hasNext()){	
			if(iter.next().getAuthority().equals("0")) {
				iter.remove();
			}
		}
		
		// ArrayList 요소를 배열에 담음 
		int size = userAuthorityGroupTOList.size();
		userAuthorityGroupList = new String[size];
		  for(int i=0; i<size; i++ ) { 
			  userAuthorityGroupList[i] = userAuthorityGroupTOList.get(i).getUserAuthorityGroupCode();
		  }

		return userAuthorityGroupList;
	}
}