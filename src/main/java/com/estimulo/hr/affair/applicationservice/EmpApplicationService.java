package com.estimulo.hr.affair.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.hr.affair.to.EmployeeBasicTO;
import com.estimulo.hr.affair.to.EmployeeDetailTO;
import com.estimulo.hr.affair.to.EmployeeSecretTO;

public interface EmpApplicationService {

	public ArrayList<EmpInfoTO> getAllEmpList(String searchCondition, String[] paramArray);

	public EmpInfoTO getEmpInfo(String companyCode, String empCode);
	
	public String getNewEmpCode(String companyCode);
	
	public Boolean checkUserIdDuplication(String companyCode, String newUserId);
	
	public Boolean checkEmpCodeDuplication(String companyCode, String newEmpCode);
	
	public HashMap<String, Object> batchEmpBasicListProcess(ArrayList<EmployeeBasicTO> empBasicList);

	public HashMap<String, Object> batchEmpDetailListProcess(ArrayList<EmployeeDetailTO> empDetailList);
	
	public HashMap<String, Object> batchEmpSecretListProcess(ArrayList<EmployeeSecretTO> empSecretList);
	
	public void changeEmpAccountUserStatus(String companyCode, String empCode, String userStatus);
	
}
