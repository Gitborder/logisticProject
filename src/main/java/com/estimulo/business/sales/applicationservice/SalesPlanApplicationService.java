package com.estimulo.business.sales.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.business.sales.to.SalesPlanTO;

public interface SalesPlanApplicationService {
	
	public ArrayList<SalesPlanTO> getSalesPlanList(String dateSearchCondition, String startDate, String endDate);
	
	// ApplicationService 안에서만 호출
	public String getNewSalesPlanNo(String salesPlanDate);

	public HashMap<String, Object> batchSalesPlanListProcess(ArrayList<SalesPlanTO> salesPlanTOList);

	
}
