package com.estimulo.production.operation.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.estimulo.production.operation.dao.WorkOrderDAO;
import com.estimulo.production.operation.to.ProductionPerformanceInfoTO;
import com.estimulo.production.operation.to.WorkOrderInfoTO;
import com.estimulo.production.operation.to.WorkSiteLog;

@Component
public class WorkOrderApplicationServiceImpl implements WorkOrderApplicationService {
	
	ModelMap resultMap = new ModelMap();
	
	@Autowired
	private WorkOrderDAO workOrderDAO;
		
	@Override
	public ModelMap getWorkOrderableMrpList() {
		HashMap<String, Object> map = new HashMap<String, Object>();
        workOrderDAO.getWorkOrderableMrpList(map);
        
        resultMap.put("gridRowJson", map.get("RESULT"));
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));
		
		return resultMap;
	}

	@Override
	public ModelMap getWorkOrderSimulationList(String mrpGatheringNo,String mrpNo) {
		mrpGatheringNo=mrpGatheringNo.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
		mrpNo=mrpNo.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
		System.out.println(mrpGatheringNo);
		System.out.println(mrpNo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mrpGatheringNo", mrpGatheringNo);
		map.put("mrpNo", mrpNo);
        
		workOrderDAO.getWorkOrderSimulationList(map);
		
		resultMap.put("gridRowJson", map.get("RESULT"));
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));
		
		return resultMap;
	}

	@Override
	public ModelMap workOrder(String mrpGatheringNo, String workPlaceCode,String productionProcess,String mrpNo) {
		mrpGatheringNo=mrpGatheringNo.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
		mrpNo=mrpNo.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("mrpGatheringNo", mrpGatheringNo);
        map.put("workPlaceCode", workPlaceCode);
        map.put("productionProcess", productionProcess);
        map.put("mrpNo", mrpNo);
		workOrderDAO.workOrder(map);
    	
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));
		
		return resultMap;
	}

	@Override
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList() {
		ArrayList<WorkOrderInfoTO> workOrderInfoList = null;
		workOrderInfoList = workOrderDAO.selectWorkOrderInfoList();
		
		return workOrderInfoList;
	}

	@Override
	public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("workOrderNo", workOrderNo);
        map.put("actualCompletionAmount", actualCompletionAmount);
		workOrderDAO.workOrderCompletion(map);
		
		return map;
	}
	
	@Override
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList() {
		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;
		productionPerformanceInfoList = workOrderDAO.selectProductionPerformanceInfoList();
		
		return productionPerformanceInfoList;
	}

	@Override
	public ModelMap showWorkSiteSituation(String workSiteCourse,String workOrderNo,String itemClassIfication) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("workOrderNo", workOrderNo);
		map.put("workSiteCourse", workSiteCourse);
		map.put("itemClassIfication", itemClassIfication);
		workOrderDAO.selectWorkSiteSituation(map);

		resultMap.put("gridRowJson", map.get("RESULT"));
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));
		System.out.println("RESULT : "+map.get("RESULT"));
		System.out.println("ERROR_CODE : "+map.get("ERROR_CODE"));
		System.out.println("ERROR_MSG : "+map.get("ERROR_MSG"));
		return resultMap;
	}

	@Override
	public void workCompletion(String workOrderNo, String itemCode ,  ArrayList<String> itemCodeListArr) {
		String itemCodeList=itemCodeListArr.toString().replace("[", "").replace("]", "");
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("workOrderNo", workOrderNo);
		map.put("itemCode", itemCode);
		map.put("itemCodeList", itemCodeList);
		workOrderDAO.updateWorkCompletionStatus(map);
	}

	@Override
	public HashMap<String, Object> workSiteLogList(String workSiteLogDate) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("workSiteLogDate", workSiteLogDate);
		
		ArrayList<WorkSiteLog> list = workOrderDAO.workSiteLogList(map);
		resultMap.put("gridRowJson",list);
		
		return resultMap;
	}
}
