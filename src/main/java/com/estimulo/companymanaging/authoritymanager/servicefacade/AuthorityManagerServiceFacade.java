package com.estimulo.companymanaging.authoritymanager.servicefacade;

import java.util.ArrayList;

import com.estimulo.companymanaging.authoritymanager.exception.IdNotFoundException;
import com.estimulo.companymanaging.authoritymanager.exception.PwMissMatchException;
import com.estimulo.companymanaging.authoritymanager.exception.PwNotFoundException;
import com.estimulo.companymanaging.authoritymanager.to.AuthorityGroupTO;
import com.estimulo.companymanaging.authoritymanager.to.EmployeeAuthorityTO;
import com.estimulo.companymanaging.authoritymanager.to.MenuAuthorityTO;
import com.estimulo.hr.affair.to.EmpInfoTO;

public interface AuthorityManagerServiceFacade {

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException;

	public String[] getAllMenuList();

	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode);
	
	public ArrayList<AuthorityGroupTO> getAuthorityGroup();

	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode);
	
	public void insertEmployeeAuthorityGroup(String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList);
	
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList);
	
}
