package com.estimulo.companymanaging.authoritymanager.applicationservice;

import java.util.ArrayList;

import com.estimulo.companymanaging.authoritymanager.to.AuthorityGroupTO;
import com.estimulo.companymanaging.authoritymanager.to.EmployeeAuthorityTO;

public interface AuthorityGroupApplicationService {
	
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode);
	
	public ArrayList<AuthorityGroupTO> getAuthorityGroup();
	
	public void insertEmployeeAuthorityGroup(String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList);

}
