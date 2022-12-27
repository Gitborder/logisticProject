package com.estimulo.system.basicinfo.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.basicinfo.to.CompanyTO;

public interface CompanyApplicationService {

	public ArrayList<CompanyTO> getCompanyList();
	
	public String getNewCompanyCode();
	
	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList);
	
	
	
}
