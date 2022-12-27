package com.estimulo.production.operation.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.estimulo.production.operation.applicationservice.MpsApplicationService;
import com.estimulo.production.operation.applicationservice.MrpApplicationService;
import com.estimulo.production.operation.applicationservice.WorkOrderApplicationService;
import com.estimulo.production.operation.to.ContractDetailInMpsAvailableTO;
import com.estimulo.production.operation.to.MpsTO;
import com.estimulo.production.operation.to.MrpGatheringTO;
import com.estimulo.production.operation.to.MrpTO;
import com.estimulo.production.operation.to.ProductionPerformanceInfoTO;
import com.estimulo.production.operation.to.SalesPlanInMpsAvailableTO;
import com.estimulo.production.operation.to.WorkOrderInfoTO;

@Service
public class ProductionServiceFacadeImpl implements ProductionServiceFacade {
	
	@Autowired
	private MpsApplicationService mpsAS;
	@Autowired
	private MrpApplicationService mrpAS;
	@Autowired
	private WorkOrderApplicationService workOrderAS;

	@Override
	public ArrayList<MpsTO> getMpsList(String startDate, String endDate, String includeMrpApply) {
		ArrayList<MpsTO> mpsTOList = null;
		mpsTOList = mpsAS.getMpsList(startDate, endDate, includeMrpApply);
		
		return mpsTOList;
	}

	@Override
	public ArrayList<ContractDetailInMpsAvailableTO> getContractDetailListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = null;
		contractDetailInMpsAvailableList = mpsAS.getContractDetailListInMpsAvailable(searchCondition, startDate,endDate);
		
		return contractDetailInMpsAvailableList;
	}

	@Override
	public ArrayList<SalesPlanInMpsAvailableTO> getSalesPlanListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {
		ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = null;
		salesPlanInMpsAvailableList = mpsAS.getSalesPlanListInMpsAvailable(searchCondition, startDate, endDate);

		return salesPlanInMpsAvailableList;

	}

	@Override
	public HashMap<String, Object> convertContractDetailToMps(
			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList) {
		HashMap<String, Object> resultMap = null;
		resultMap = mpsAS.convertContractDetailToMps(contractDetailInMpsAvailableList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> convertSalesPlanToMps(
			ArrayList<SalesPlanInMpsAvailableTO> contractDetailInMpsAvailableList) {
		HashMap<String, Object> resultMap = null;
		resultMap = mpsAS.convertSalesPlanToMps(contractDetailInMpsAvailableList);
		
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchMpsListProcess(ArrayList<MpsTO> mpsTOList) {
		HashMap<String, Object> resultMap = null;
		resultMap = mpsAS.batchMpsListProcess(mpsTOList);

		return resultMap;
	}

	@Override
	public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition) {
		ArrayList<MrpTO> mrpList = null;
		mrpList = mrpAS.searchMrpList(mrpGatheringStatusCondition);

		return mrpList;
	}

	@Override
	public ArrayList<MrpTO> selectMrpListAsDate(String dateSearchCondtion, String startDate, String endDate) {
		ArrayList<MrpTO> mrpList = null;
		mrpList = mrpAS.selectMrpListAsDate(dateSearchCondtion, startDate, endDate);
	
		return mrpList;
	}

	@Override
	public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo) {
		ArrayList<MrpTO> mrpList = null;
		mrpList = mrpAS.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
		
		return mrpList;
	}

	@Override
	public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate,
			String endDate) {
		ArrayList<MrpGatheringTO> mrpGatheringList = null;
		mrpGatheringList = mrpAS.searchMrpGatheringList(dateSearchCondtion, startDate, endDate);
		
		return mrpGatheringList;
	}

	@Override
	public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = mrpAS.openMrp(mpsNoArr);

		return resultMap;
	}
	
	@Override
	public HashMap<String, Object> registerMrp(String mrpRegisterDate, ArrayList<String> mpsList) {
		HashMap<String, Object> resultMap = null;
		resultMap = mrpAS.registerMrp(mrpRegisterDate, mpsList);
	
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList) {
		HashMap<String, Object> resultMap = null;
		resultMap = mrpAS.batchMrpListProcess(mrpTOList);

		return resultMap;
	}

	@Override
	public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr) {
		ArrayList<MrpGatheringTO> mrpGatheringList = null;
		mrpGatheringList = mrpAS.getMrpGathering(mrpNoArr);

		return mrpGatheringList;
	}
	
	@Override
	public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate,ArrayList<String> mrpNoArr,HashMap<String, String> mrpNoAndItemCodeMap) {
		HashMap<String, Object> resultMap = null;
		resultMap = mrpAS.registerMrpGathering(mrpGatheringRegisterDate, mrpNoArr,mrpNoAndItemCodeMap);
		//											선택한날짜             	getRowData		MRP-NO : DK-AP01
		
		return resultMap;
	}
	
	@Override
	public ModelMap getWorkOrderableMrpList() {
        ModelMap resultMap = null;
		resultMap = workOrderAS.getWorkOrderableMrpList();
		
		return resultMap;
	}
	
	@Override
	public ModelMap getWorkOrderSimulationList(String mrpGatheringNoList ,String mrpNoList) {
       	ModelMap resultMap = null;
		resultMap = workOrderAS.getWorkOrderSimulationList(mrpGatheringNoList,mrpNoList);
		
		return resultMap;
	}
	
	@Override
	public ModelMap workOrder(String mrpGatheringNo,String workPlaceCode,String productionProcess,String mrpNo) {
		ModelMap resultMap = null;
		resultMap = workOrderAS.workOrder(mrpGatheringNo,workPlaceCode,productionProcess,mrpNo);
		
    	return resultMap;
	}

	@Override
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList() {
		ArrayList<WorkOrderInfoTO> workOrderInfoList = null;
		workOrderInfoList = workOrderAS.getWorkOrderInfoList();

		return workOrderInfoList;
	}

	@Override
	public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount) {
		HashMap<String,Object> resultMap = null;
		resultMap = workOrderAS.workOrderCompletion(workOrderNo,actualCompletionAmount);
		
    	return resultMap;
	}

	@Override
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList() {
		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;
		productionPerformanceInfoList = workOrderAS.getProductionPerformanceInfoList();
		
		return productionPerformanceInfoList;
	}

	@Override
	public ModelMap showWorkSiteSituation(String workSiteCourse,String workOrderNo,String itemClassIfication) {
		ModelMap resultMap = null;
		resultMap = workOrderAS.showWorkSiteSituation(workSiteCourse,workOrderNo,itemClassIfication);
		
		return resultMap;
	}

	@Override
	public void workCompletion(String workOrderNo, String itemCode ,  ArrayList<String> itemCodeListArr) {
		workOrderAS.workCompletion(workOrderNo,itemCode,itemCodeListArr);
	}

	@Override
	public HashMap<String, Object> workSiteLogList(String workSiteLogDate) {
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap=workOrderAS.workSiteLogList(workSiteLogDate);
		
		return resultMap;
	}
}
