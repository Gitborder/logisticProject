package com.estimulo.system.basicinfo.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.system.basicinfo.applicationservice.CompanyApplicationService;
import com.estimulo.system.basicinfo.applicationservice.CustomerApplicationService;
import com.estimulo.system.basicinfo.applicationservice.DepartmentApplicationService;
import com.estimulo.system.basicinfo.applicationservice.FinancialAccountAssociatesApplicationService;
import com.estimulo.system.basicinfo.applicationservice.WorkplaceApplicationService;
import com.estimulo.system.basicinfo.to.CompanyTO;
import com.estimulo.system.basicinfo.to.CustomerTO;
import com.estimulo.system.basicinfo.to.DepartmentTO;
import com.estimulo.system.basicinfo.to.FinancialAccountAssociatesTO;
import com.estimulo.system.basicinfo.to.WorkplaceTO;

@Service
public class BasicInfoServiceFacadeImpl implements BasicInfoServiceFacade{
	
	@Autowired
    private CustomerApplicationService customerAS;
	@Autowired
	private FinancialAccountAssociatesApplicationService associatsAS;
	@Autowired
	private CompanyApplicationService companyAS;
	@Autowired
	private WorkplaceApplicationService workplaceAS;
	@Autowired
	private DepartmentApplicationService deptAS;

    @Override
    public ArrayList<CustomerTO> getCustomerList(String searchCondition, String companyCode, String workplaceCode,String itemGroupCode) {
        ArrayList<CustomerTO> customerList = customerAS.getCustomerList(searchCondition, companyCode, workplaceCode,itemGroupCode);
        
        return customerList;
    }

    @Override
    public HashMap<String, Object> batchCustomerListProcess(ArrayList<CustomerTO> customerList) {
        HashMap<String, Object> resultMap = customerAS.batchCustomerListProcess(customerList);
        
        return resultMap;
    }

    @Override
    public ArrayList<FinancialAccountAssociatesTO> getFinancialAccountAssociatesList(String searchCondition,
                                                                                     String workplaceCode) {
        ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = associatsAS.getFinancialAccountAssociatesList(searchCondition,
                workplaceCode);
    
        return financialAccountAssociatesList;
    }

    @Override
    public HashMap<String, Object> batchFinancialAccountAssociatesListProcess(
            ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList) {
        HashMap<String, Object> resultMap = associatsAS.batchFinancialAccountAssociatesListProcess(financialAccountAssociatesList);
        
        return resultMap;
    }
    
    @Override
	public ArrayList<CompanyTO> getCompanyList() {
		ArrayList<CompanyTO> companyList = companyAS.getCompanyList();

		return companyList;
	}

	@Override
	public ArrayList<WorkplaceTO> getWorkplaceList(String companyCode) {
		ArrayList<WorkplaceTO> workplaceList = workplaceAS.getWorkplaceList(companyCode);

		return workplaceList;
	}

	@Override
	public ArrayList<DepartmentTO> getDepartmentList(String searchCondition, String companyCode,
			String workplaceCode) {
		ArrayList<DepartmentTO> departmentList = deptAS.getDepartmentList(searchCondition, companyCode, workplaceCode);
		
		return departmentList;
	}

	@Override
	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList) {
		HashMap<String, Object> resultMap = companyAS.batchCompanyListProcess(companyList);
		
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchWorkplaceListProcess(ArrayList<WorkplaceTO> workplaceList) {
		HashMap<String, Object> resultMap = workplaceAS.batchWorkplaceListProcess(workplaceList);
		
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchDepartmentListProcess(ArrayList<DepartmentTO> departmentList) {
		HashMap<String, Object> resultMap = deptAS.batchDepartmentListProcess(departmentList);
		
		return resultMap;
	}

}
