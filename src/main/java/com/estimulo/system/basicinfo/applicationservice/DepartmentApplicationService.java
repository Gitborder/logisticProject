package com.estimulo.system.basicinfo.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.basicinfo.to.DepartmentTO;

public interface DepartmentApplicationService {
	
	public ArrayList<DepartmentTO> getDepartmentList(String searchCondition, String companyCode,
			String workplaceCode);
	
	public String getNewDepartmentCode(String companyCode);
	
	public HashMap<String, Object> batchDepartmentListProcess(ArrayList<DepartmentTO> departmentList);

}
