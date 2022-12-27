package com.estimulo.business.sales.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.ui.ModelMap;

import com.estimulo.business.sales.to.ContractDetailTO;
import com.estimulo.business.sales.to.ContractInfoTO;
import com.estimulo.business.sales.to.EstimateTO;

public interface ContractApplicationService {

	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String startDate, String endDate, String customerCode);

	public ArrayList<ContractDetailTO> getContractDetailList(String estimateNo);
	
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate);

	// ApplicationService 안에서만 호출
	public String getNewContractNo(String contractDate);

	public ModelMap addNewContract(HashMap<String,String[]>  workingContractList);

	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList);

	public void changeContractStatusInEstimate(String estimateNo , String contractStatus);
	
	public ArrayList<ContractInfoTO> getDeliverableContractList(HashMap<String,String> ableSearchConditionInfo);

}
