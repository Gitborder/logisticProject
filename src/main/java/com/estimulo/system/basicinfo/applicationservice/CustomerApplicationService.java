package com.estimulo.system.basicinfo.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.basicinfo.to.CustomerTO;

public interface CustomerApplicationService {

	public ArrayList<CustomerTO> getCustomerList(String searchCondition, String companyCode,
			String workplaceCode,String itemGroupCode);
	
	public String getNewCustomerCode(String workplaceCode);
	
	public HashMap<String, Object> batchCustomerListProcess(ArrayList<CustomerTO> customerList);
	
}
