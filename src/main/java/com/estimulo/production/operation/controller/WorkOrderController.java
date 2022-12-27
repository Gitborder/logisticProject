package com.estimulo.production.operation.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.production.operation.servicefacade.ProductionServiceFacade;
import com.estimulo.production.operation.to.ProductionPerformanceInfoTO;
import com.estimulo.production.operation.to.WorkOrderInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/operation/*")
public class WorkOrderController {

	ModelMap map = new ModelMap();
	
	@Autowired
	private ProductionServiceFacade productionServiceFacade;

	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // serializeNulls() 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/getWorkOrderableMrpList.do" , method=RequestMethod.POST)	
	public ModelMap getWorkOrderableMrpList(HttpServletRequest request, HttpServletResponse response) {
		try {
			map = productionServiceFacade.getWorkOrderableMrpList();
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/showWorkOrderDialog.do" , method=RequestMethod.POST)
	public ModelMap showWorkOrderDialog(HttpServletRequest request, HttpServletResponse response) {
		String mrpGatheringNoList = request.getParameter("mrpGatheringNoList");
		String mrpNoList = request.getParameter("mrpNoList");
		try {
			map = productionServiceFacade.getWorkOrderSimulationList(mrpGatheringNoList,mrpNoList);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/workOrder.do" , method=RequestMethod.POST)
	public ModelMap workOrder(HttpServletRequest request, HttpServletResponse response) {
		String mrpGatheringNo = request.getParameter("mrpGatheringNo"); // mrpGatheringNo
		String workPlaceCode = request.getParameter("workPlaceCode"); 
		String productionProcess = request.getParameter("productionProcessCode"); 
		String mrpNo = request.getParameter("mrpNo");
		try {
													//	  BRC-01		PP002
			map = productionServiceFacade.workOrder(mrpGatheringNo,workPlaceCode,productionProcess,mrpNo);

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/showWorkOrderInfoList.do" , method=RequestMethod.POST)
	public ModelMap showWorkOrderInfoList(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<WorkOrderInfoTO> workOrderInfoList = null;
		try {
			workOrderInfoList = productionServiceFacade.getWorkOrderInfoList();
			
			map.put("gridRowJson", workOrderInfoList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}
	
	@RequestMapping(value="/workOrderCompletion.do" , method=RequestMethod.POST)
	public ModelMap workOrderCompletion(HttpServletRequest request, HttpServletResponse response) {
		String workOrderNo=request.getParameter("workOrderNo");
		String actualCompletionAmount=request.getParameter("actualCompletionAmount");

		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			resultMap = productionServiceFacade.workOrderCompletion(workOrderNo,actualCompletionAmount);
			
			map.put("errorCode",resultMap.get("ERROR_CODE"));
			map.put("errorMsg", resultMap.get("ERROR_MSG"));
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}
	
	@RequestMapping(value="/getProductionPerformanceInfoList.do" , method=RequestMethod.POST)
	public ModelMap getProductionPerformanceInfoList(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;
		try {
			productionPerformanceInfoList = productionServiceFacade.getProductionPerformanceInfoList();

			map.put("gridRowJson", productionPerformanceInfoList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}
	
	@RequestMapping(value="/showWorkSiteSituation.do" , method=RequestMethod.POST)
	public ModelMap showWorkSiteSituation(HttpServletRequest request, HttpServletResponse response) {
		String workSiteCourse = request.getParameter("workSiteCourse");//원재료검사:RawMaterials,제품제작:Production,판매제품검사:SiteExamine
		String workOrderNo = request.getParameter("workOrderNo");//작업지시일련번호	
		String itemClassIfication = request.getParameter("itemClassIfication");//품목분류:완제품,반제품,재공품	
		try {
			map = productionServiceFacade.showWorkSiteSituation(workSiteCourse,workOrderNo,itemClassIfication);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}
	
	@RequestMapping(value="/workCompletion.do" , method=RequestMethod.POST)
	public ModelMap workCompletion(HttpServletRequest request, HttpServletResponse response) {
		String workOrderNo = request.getParameter("workOrderNo"); //작업지시번호
		String itemCode = request.getParameter("itemCode"); //제작품목코드 DK-01 , DK-AP01
		String itemCodeList = request.getParameter("itemCodeList"); //작업품목코드 
		
		//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
		try {
			ArrayList<String> itemCodeListArr = gson.fromJson(itemCodeList,
					new TypeToken<ArrayList<String>>() {}.getType());
			productionServiceFacade.workCompletion(workOrderNo,itemCode,itemCodeListArr);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}
	
	@RequestMapping(value="/workSiteLog.do" , method=RequestMethod.POST)
	public ModelMap workSiteLogList(HttpServletRequest request, HttpServletResponse response) {
		String workSiteLogDate = request.getParameter("workSiteLogDate");

		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			resultMap = productionServiceFacade.workSiteLogList(workSiteLogDate);
			
			map.put("gridRowJson", resultMap);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
}
