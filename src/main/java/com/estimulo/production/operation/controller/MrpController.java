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
import com.estimulo.production.operation.to.MrpGatheringTO;
import com.estimulo.production.operation.to.MrpTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/operation/*")
public class MrpController {

	ModelMap map = new ModelMap();
	
	@Autowired
	private ProductionServiceFacade productionServiceFacade;
	
	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환
	
	@RequestMapping(value="/getMrpList.do", method=RequestMethod.POST)
	public ModelMap getMrpList(HttpServletRequest request, HttpServletResponse response) {
		String mrpGatheringStatusCondition = request.getParameter("mrpGatheringStatusCondition");	
		String dateSearchCondition = request.getParameter("dateSearchCondition");
		String mrpStartDate = request.getParameter("mrpStartDate");
		String mrpEndDate = request.getParameter("mrpEndDate");
		String mrpGatheringNo = request.getParameter("mrpGatheringNo");
		try {
			ArrayList<MrpTO> mrpList = null;
			
			if(mrpGatheringStatusCondition != null ) {
				mrpList = productionServiceFacade.searchMrpList(mrpGatheringStatusCondition);
			} else if (dateSearchCondition != null) {
				mrpList = productionServiceFacade.selectMrpListAsDate(dateSearchCondition, mrpStartDate, mrpEndDate);
			} else if (mrpGatheringNo != null) {
				mrpList = productionServiceFacade.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
			}
			map.put("gridRowJson", mrpList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/openMrp.do", method=RequestMethod.POST)
	public ModelMap openMrp(HttpServletRequest request, HttpServletResponse response) {
		String mpsNoListStr = request.getParameter("mpsNoList"); 
		System.out.println("mpsNoListStr 파라메터값 : "+mpsNoListStr);
		try {
			ArrayList<String> mpsNoArr = gson.fromJson(mpsNoListStr,
					new TypeToken<ArrayList<String>>() { }.getType());		
			System.out.println("mpsNoArr 값 : "+mpsNoArr);
			HashMap<String, Object> mrpMap = productionServiceFacade.openMrp(mpsNoArr);
			map.put("gridRowJson", mrpMap);
			map.put("errorCode", mrpMap.get("ERROR_CODE"));
			map.put("errorMsg", mrpMap.get("ERROR_MSG"));
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value="/registerMrp.do", method=RequestMethod.POST)
	public ModelMap registerMrp(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList"); 
		String mrpRegisterDate = request.getParameter("mrpRegisterDate"); 
		try {
			ArrayList<String> mpsList = gson.fromJson(batchList, 
					new TypeToken<ArrayList<String>>() { }.getType()); 
			System.out.println("batchList : "+batchList);
			System.out.println("mrpRegisterDate"+mrpRegisterDate);
			HashMap<String, Object> resultMap = productionServiceFacade.registerMrp(mrpRegisterDate, mpsList);	 
			
			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/getMrpGatheringList.do", method=RequestMethod.POST)
	public ModelMap getMrpGatheringList(HttpServletRequest request, HttpServletResponse response) {
		String mrpNoList = request.getParameter("mrpNoList");
		try {
			ArrayList<String> mrpNoArr = gson.fromJson(mrpNoList,
					new TypeToken<ArrayList<String>>() { }.getType());
			ArrayList<MrpGatheringTO> mrpGatheringList = productionServiceFacade.getMrpGathering(mrpNoArr);
			
			map.put("gridRowJson", mrpGatheringList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/registerMrpGathering.do", method=RequestMethod.POST)
	public ModelMap registerMrpGathering(HttpServletRequest request, HttpServletResponse response) {
		String mrpGatheringRegisterDate = request.getParameter("mrpGatheringRegisterDate"); 
		String mrpNoList = request.getParameter("mrpNoList");
		String mrpNoAndItemCodeList = request.getParameter("mrpNoAndItemCodeList");
		System.out.println("mrpGatheringRegisterDate : "+mrpGatheringRegisterDate);
		try {
			ArrayList<String> mrpNoArr = gson.fromJson(mrpNoList,
					new TypeToken<ArrayList<String>>() { }.getType());
			HashMap<String, String> mrpNoAndItemCodeMap =  gson.fromJson(mrpNoAndItemCodeList, //mprNO : ItemCode 
	              new TypeToken<HashMap<String, String>>() { }.getType());
			HashMap<String, Object> resultMap  = productionServiceFacade.registerMrpGathering(mrpGatheringRegisterDate, mrpNoArr,mrpNoAndItemCodeMap);	 
	//															                				getRowData		MRP-NO : DK-AP01
			System.out.println("resultMap : " + resultMap);
			
			map.put("gridRowJson", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/searchMrpGathering.do", method=RequestMethod.POST)
	public ModelMap searchMrpGathering(HttpServletRequest request, HttpServletResponse response) {
		String searchDateCondition = request.getParameter("searchDateCondition");
		String startDate = request.getParameter("mrpGatheringStartDate");
		String endDate = request.getParameter("mrpGatheringEndDate");
		try {
			ArrayList<MrpGatheringTO> mrpGatheringList = 
					productionServiceFacade.searchMrpGatheringList(searchDateCondition, startDate, endDate);
				
			map.put("gridRowJson", mrpGatheringList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
}
