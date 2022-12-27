package com.estimulo.companymanaging.logisticsinfo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.companymanaging.logisticsinfo.servicefacade.LogisticsInfoServiceFacade;
import com.estimulo.companymanaging.logisticsinfo.to.WarehouseTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/logisticsinfo/*")
public class WarehouseController {
	
	ModelMap map = new ModelMap();
	
	// serviceFacade 참조변수 선언
	@Autowired
	private LogisticsInfoServiceFacade logisticsInfoServiceFacade;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value = "/searchwarehouseList.do", method = RequestMethod.POST)
	public ModelMap getWarehouseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<WarehouseTO> WarehouseTOList = logisticsInfoServiceFacade.getWarehouseInfoList();
			map.put("gridRowJson", WarehouseTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value = "/batchwarehouseProcess.do", method = RequestMethod.POST)
	public ModelMap modifyWarehouseInfo(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList");
		try {
			ArrayList<WarehouseTO> warehouseTOList = gson.fromJson(batchList,
					new TypeToken<ArrayList<WarehouseTO>>() {
					}.getType());
			
			logisticsInfoServiceFacade.batchWarehouseInfo(warehouseTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}
	
	@RequestMapping(value = "/warehousePageList.do", method = RequestMethod.POST)
	public ModelMap findLastWarehouseCode(HttpServletRequest request, HttpServletResponse response){
		try {
			String warehouseCode = logisticsInfoServiceFacade.findLastWarehouseCode();
			map.put("lastWarehouseCode", warehouseCode);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}
}
