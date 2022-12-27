package com.estimulo.business.sales.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.estimulo.business.sales.applicationservice.ContractApplicationService;
import com.estimulo.business.sales.applicationservice.DeliveryApplicationService;
import com.estimulo.business.sales.applicationservice.EstimateApplicationService;
import com.estimulo.business.sales.applicationservice.SalesPlanApplicationService;
import com.estimulo.business.sales.to.ContractDetailTO;
import com.estimulo.business.sales.to.ContractInfoTO;
import com.estimulo.business.sales.to.DeliveryInfoTO;
import com.estimulo.business.sales.to.EstimateDetailTO;
import com.estimulo.business.sales.to.EstimateTO;
import com.estimulo.business.sales.to.SalesPlanTO;

@Service
public class SalesServiceFacadeImpl implements SalesServiceFacade {

	@Autowired
	private EstimateApplicationService estimateApplicationService;
	@Autowired
	private ContractApplicationService contractApplicationService;
	@Autowired
	private SalesPlanApplicationService salesPlanApplicationService;
	@Autowired
	private DeliveryApplicationService deliveryApplicationService;

	@Override
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate) {
		ArrayList<EstimateTO> estimateTOList = null;

		estimateTOList = estimateApplicationService.getEstimateList(dateSearchCondition, startDate, endDate);

		return estimateTOList;
	}

	@Override
	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo) {
		ArrayList<EstimateDetailTO> estimateDetailTOList = null;

		estimateDetailTOList = estimateApplicationService.getEstimateDetailList(estimateNo);

		return estimateDetailTOList;
	}

	@Override
	public ModelMap addNewEstimate(String estimateDate, EstimateTO newEstimateTO) {
		ModelMap resultMap = null;
		resultMap = estimateApplicationService.addNewEstimate(estimateDate, newEstimateTO);
		System.out.println("              SalesSF에서resultMap값은 : " + resultMap);
		//신청한 날짜 뒤에 견적등록 갯수 번호
		// SalesSF에서resultMap값은 : {DELETE=[], 
								//	newEstimateNo=ES2020100702, 
								//	INSERT=[ES2020100702-01, ES2020100702-02], 
								//	UPDATE=[]}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchEstimateDetailListProcess(ArrayList<EstimateDetailTO> estimateDetailTOList,String estimateNo) {
		HashMap<String, Object> resultMap = null;

		resultMap = estimateApplicationService.batchEstimateDetailListProcess(estimateDetailTOList,estimateNo);

		return resultMap;
	}

	@Override
	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String startDate, String endDate, String customerCode) {
		ArrayList<ContractInfoTO> contractInfoTOList = null;

		contractInfoTOList = contractApplicationService.getContractList(searchCondition, startDate, endDate, customerCode);

		return contractInfoTOList;
	}

	@Override
	public ArrayList<ContractInfoTO> getDeliverableContractList(HashMap<String,String> ableSearchConditionInfo) {
		ArrayList<ContractInfoTO> deliverableContractList = null;
		
		deliverableContractList = contractApplicationService.getDeliverableContractList(ableSearchConditionInfo);
		System.out.println("**********Service Facade");
		return deliverableContractList;
	}
	
	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String estimateNo) {
		ArrayList<ContractDetailTO> contractDetailTOList = null;
		
		contractDetailTOList = contractApplicationService.getContractDetailList(estimateNo);
		
		return contractDetailTOList;
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {
		System.out.println("##$#$#$#$$");
		ArrayList<EstimateTO> estimateListInContractAvailable = null;

		estimateListInContractAvailable = contractApplicationService.getEstimateListInContractAvailable(startDate, endDate);

		return estimateListInContractAvailable;
	}

	@Override
	public ModelMap addNewContract(HashMap<String,String[]>  workingContractList) {
		ModelMap resultMap = null;

		resultMap = contractApplicationService.addNewContract(workingContractList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {
		HashMap<String, Object> resultMap = null;

		resultMap = contractApplicationService.batchContractDetailListProcess(contractDetailTOList);
		
		return resultMap;
	}

	@Override
	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {
												//estimateNo, "N"
		contractApplicationService.changeContractStatusInEstimate(estimateNo, contractStatus);
	}

	@Override
	public ArrayList<SalesPlanTO> getSalesPlanList(String dateSearchCondition, String startDate, String endDate) {
		ArrayList<SalesPlanTO> salesPlanTOList = null;

		salesPlanTOList = salesPlanApplicationService.getSalesPlanList(dateSearchCondition, startDate, endDate);

		return salesPlanTOList;
	}

	@Override
	public HashMap<String, Object> batchSalesPlanListProcess(ArrayList<SalesPlanTO> salesPlanTOList) {
		HashMap<String, Object> resultMap = null;

		resultMap = salesPlanApplicationService.batchSalesPlanListProcess(salesPlanTOList);

		return resultMap;
	}

	@Override
	public ArrayList<DeliveryInfoTO> getDeliveryInfoList() {
		ArrayList<DeliveryInfoTO> deliveryInfoList = null;

		deliveryInfoList = deliveryApplicationService.getDeliveryInfoList();

		return deliveryInfoList;
	}

	@Override
	public HashMap<String, Object> batchDeliveryListProcess(List<DeliveryInfoTO> deliveryTOList) {
		HashMap<String, Object> resultMap = null;

		resultMap = deliveryApplicationService.batchDeliveryListProcess(deliveryTOList);

		return resultMap;
	}

	@Override
	public ModelMap deliver(String contractDetailNo) {
        ModelMap resultMap = null;

		resultMap = deliveryApplicationService.deliver(contractDetailNo);

		return resultMap;
	}
	
}
