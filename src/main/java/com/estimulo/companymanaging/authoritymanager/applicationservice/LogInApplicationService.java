package com.estimulo.companymanaging.authoritymanager.applicationservice;

import com.estimulo.companymanaging.authoritymanager.exception.IdNotFoundException;
import com.estimulo.companymanaging.authoritymanager.exception.PwMissMatchException;
import com.estimulo.companymanaging.authoritymanager.exception.PwNotFoundException;
import com.estimulo.hr.affair.to.EmpInfoTO;

public interface LogInApplicationService {

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException;

}
