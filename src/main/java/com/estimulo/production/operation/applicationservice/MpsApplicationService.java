package com.estimulo.production.operation.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.production.operation.to.ContractDetailInMpsAvailableTO;
import com.estimulo.production.operation.to.MpsTO;
import com.estimulo.production.operation.to.SalesPlanInMpsAvailableTO;

public interface MpsApplicationService {

	public ArrayList<MpsTO> getMpsList(String startDate, String endDate, String includeMrpApply);
	
	public ArrayList<ContractDetailInMpsAvailableTO> 
		getContractDetailListInMpsAvailable(String searchCondition, String startDate, String endDate);

	public ArrayList<SalesPlanInMpsAvailableTO> 
		getSalesPlanListInMpsAvailable(String searchCondition, String startDate, String endDate);

	public HashMap<String, Object> convertContractDetailToMps(
			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList);

	public HashMap<String, Object> convertSalesPlanToMps(
			ArrayList<SalesPlanInMpsAvailableTO> contractDetailInMpsAvailableList);
	
	public HashMap<String, Object> batchMpsListProcess(ArrayList<MpsTO> mpsTOList);

	
	// applicationService 내부 메서드
	public String getNewMpsNo(String mpsPlanDate);
	
	// applicationService 내부 메서드	
	public void changeMpsStatusInContractDetail(String mpsStatus, String contractDetailNo);
	
	// applicationService 내부 메서드
	public void changeMpsStatusInSalesPlan(String mpsStatus, String salesPlanNo);
}
