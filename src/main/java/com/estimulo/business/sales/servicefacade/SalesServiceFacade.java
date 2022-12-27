package com.estimulo.business.sales.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

import com.estimulo.business.sales.to.ContractDetailTO;
import com.estimulo.business.sales.to.ContractInfoTO;
import com.estimulo.business.sales.to.DeliveryInfoTO;
import com.estimulo.business.sales.to.EstimateDetailTO;
import com.estimulo.business.sales.to.EstimateTO;
import com.estimulo.business.sales.to.SalesPlanTO;

public interface SalesServiceFacade {

	
	// EstimateApplicationServiceImpl
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate);

	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo);
	
	public ModelMap addNewEstimate(String estimateDate, EstimateTO newEstimateTO);

	public HashMap<String, Object> batchEstimateDetailListProcess(ArrayList<EstimateDetailTO> estimateDetailTOList,String estimateNo);	
	
	
	// ContractApplicationServiceImpl
	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String startDate, String endDate, String customerCode);
	
	public ArrayList<ContractInfoTO> getDeliverableContractList(HashMap<String,String> ableSearchConditionInfo);
	
	public ArrayList<ContractDetailTO> getContractDetailList(String estimateNo);
	
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate);

	public ModelMap addNewContract(HashMap<String,String[]>  workingContractList);

	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList);
	
	public void changeContractStatusInEstimate(String estimateNo , String contractStatus);
	
	
	// SalesPlanApplicationServiceImpl
	public ArrayList<SalesPlanTO> getSalesPlanList(String dateSearchCondition, String startDate, String endDate);
	
	public HashMap<String, Object> batchSalesPlanListProcess(ArrayList<SalesPlanTO> salesPlanTOList);

	public HashMap<String, Object> batchDeliveryListProcess(List<DeliveryInfoTO> deliveryTOList);

	public ModelMap deliver(String contractDetailNo);
	
	public ArrayList<DeliveryInfoTO> getDeliveryInfoList();
	
}
