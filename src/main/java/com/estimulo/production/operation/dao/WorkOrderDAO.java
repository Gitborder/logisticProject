package com.estimulo.production.operation.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.ui.ModelMap;

import com.estimulo.production.operation.to.ProductionPerformanceInfoTO;
import com.estimulo.production.operation.to.WorkOrderInfoTO;
import com.estimulo.production.operation.to.WorkSiteLog;

@Mapper
public interface WorkOrderDAO {

	public ModelMap getWorkOrderableMrpList(HashMap<String, Object> map);
	
	public ModelMap getWorkOrderSimulationList(HashMap<String, Object> map);	
	
	public ModelMap workOrder(HashMap<String,Object> map);
	
	public ArrayList<WorkOrderInfoTO> selectWorkOrderInfoList();
	
	public HashMap<String,Object> workOrderCompletion(HashMap<String,Object> map);
	
	public ArrayList<ProductionPerformanceInfoTO> selectProductionPerformanceInfoList();
	
	public ModelMap selectWorkSiteSituation(HashMap<String,Object> map);
	
	public void updateWorkCompletionStatus(HashMap<String,Object> map);
	
	public ArrayList<WorkSiteLog> workSiteLogList(HashMap<String,String> map);
	
}
