package com.estimulo.companymanaging.authoritymanager.applicationservice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.companymanaging.authoritymanager.dao.AuthorityGroupDAO;
import com.estimulo.companymanaging.authoritymanager.to.AuthorityGroupTO;
import com.estimulo.companymanaging.authoritymanager.to.EmployeeAuthorityTO;

@Component
public class AuthorityGroupApplicationServiceImpl implements AuthorityGroupApplicationService {
	
	// DAO 참조변수 선언 
	@Autowired
	private AuthorityGroupDAO authorityGroupDAO;
	
	@Override
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode) {   
		ArrayList<AuthorityGroupTO> authorityGroupTOList = authorityGroupDAO.selectUserAuthorityGroupList(empCode);

	    return authorityGroupTOList;
	}
	
	@Override
	public ArrayList<AuthorityGroupTO> getAuthorityGroup() {
	      ArrayList<AuthorityGroupTO> authorityGroupTOList = authorityGroupDAO.selectAuthorityGroupList();
	       
	      return authorityGroupTOList;
	}

	@Override
	public void insertEmployeeAuthorityGroup(
			String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList) {
		// 해당 사원의 기존 그룹권한 정보 삭제
		authorityGroupDAO.deleteEmployeeAuthorityGroup(empCode);
		  
		// 새로운 그룹권한 정보 insert 
		for(EmployeeAuthorityTO bean : employeeAuthorityTOList) {
			authorityGroupDAO.insertEmployeeAuthorityGroup(bean);
		}
	}
}
