package com.estimulo.companymanaging.authoritymanager.servicefacade;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.companymanaging.authoritymanager.applicationservice.AuthorityGroupApplicationService;
import com.estimulo.companymanaging.authoritymanager.applicationservice.LogInApplicationService;
import com.estimulo.companymanaging.authoritymanager.applicationservice.MenuApplicationService;
import com.estimulo.companymanaging.authoritymanager.exception.IdNotFoundException;
import com.estimulo.companymanaging.authoritymanager.exception.PwMissMatchException;
import com.estimulo.companymanaging.authoritymanager.exception.PwNotFoundException;
import com.estimulo.companymanaging.authoritymanager.to.AuthorityGroupTO;
import com.estimulo.companymanaging.authoritymanager.to.EmployeeAuthorityTO;
import com.estimulo.companymanaging.authoritymanager.to.MenuAuthorityTO;
import com.estimulo.hr.affair.to.EmpInfoTO;

@Service
public class AuthorityManagerServiceFacadeImpl implements AuthorityManagerServiceFacade {
	
	// AS 참조변수 선언
	@Autowired
	private  LogInApplicationService logInAS;
	@Autowired
	private  MenuApplicationService menuAS;
	@Autowired
	private  AuthorityGroupApplicationService authorityGroupAS;

	@Override
	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException {
		EmpInfoTO TO = logInAS.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);		
		
		return TO;
	}

	@Override
	public String[] getAllMenuList() {
		String[] allMenuList = menuAS.getAllMenuList();
		
		return allMenuList;
	}
	
	@Override
	public ArrayList<AuthorityGroupTO> getAuthorityGroup() {
		System.out.println(" 실행");
		ArrayList<AuthorityGroupTO> authorityGroupTOList = authorityGroupAS.getAuthorityGroup();
		
		return authorityGroupTOList;
	}

	@Override
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode) {
		ArrayList<AuthorityGroupTO> authorityGroupTOList= authorityGroupAS.getUserAuthorityGroup(empCode);
		
		return authorityGroupTOList;
	}

	@Override
	public void insertEmployeeAuthorityGroup(String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList) {
		authorityGroupAS.insertEmployeeAuthorityGroup(empCode, employeeAuthorityTOList);
	}

	@Override
	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode) {
		ArrayList<MenuAuthorityTO> menuAuthorityTOList = menuAS.getMenuAuthority(authorityGroupCode);
		
		return menuAuthorityTOList;
	}

	@Override
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList) {
		menuAS.insertMenuAuthority(authorityGroupCode, menuAuthorityTOList);
	}
}
