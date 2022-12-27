package com.estimulo.production.operation.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.ui.ModelMap;

import com.estimulo.production.operation.to.ProductionPerformanceInfoTO;
import com.estimulo.production.operation.to.WorkOrderInfoTO;


public interface WorkOrderApplicationService {

	public ModelMap getWorkOrderableMrpList();

	public ModelMap getWorkOrderSimulationList(String mrpGatheringNoList,String mrpNoList);	
	
	public ModelMap workOrder(String mrpGatheringNo,String workPlaceCode,String productionProcess,String mrpNo);
	
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList();
	
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList();

	public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount);
	
	public ModelMap showWorkSiteSituation(String workSiteCourse,String workOrderNo, String itemClassIfication);
	
	public void workCompletion(String workOrderNo,String itemCode , ArrayList<String> itemCodeListArr);
	
	public HashMap<String,Object> workSiteLogList(String workSiteLogDate);
	
} 
 