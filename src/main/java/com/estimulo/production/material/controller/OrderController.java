package com.estimulo.production.material.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.production.material.servicefacade.MaterialServiceFacade;
import com.estimulo.production.material.to.OrderInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/material/*")
public class OrderController {
	
	private ModelMap map = new ModelMap();
	
	@Autowired
	private MaterialServiceFacade materialServiceFacade;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환
	
	@RequestMapping(value="/checkOrderInfo.do" , method=RequestMethod.POST)
	public ModelMap checkOrderInfo(HttpServletRequest request, HttpServletResponse response) {
		String orderNoListStr = request.getParameter("orderNoList");
		try {
			ArrayList<String> orderNoArr = gson.fromJson(orderNoListStr,
					new TypeToken<ArrayList<String>>(){}.getType());
			
			map = materialServiceFacade.checkOrderInfo(orderNoArr);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}
	
	@RequestMapping(value="/getOrderList.do" , method=RequestMethod.POST)
	public ModelMap getOrderList(HttpServletRequest request, HttpServletResponse response) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		try {
			HashMap<String, Object> resultMap = materialServiceFacade.getOrderList(startDate, endDate);
			map.put("gridRowJson",resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/showOrderDialog.do" , method=RequestMethod.POST)
	public ModelMap openOrderDialog(HttpServletRequest request, HttpServletResponse response) {
		String mrpNoListStr = request.getParameter("mrpGatheringNoList");
		System.out.println("@@@@@@"+mrpNoListStr);
		try {
			map = materialServiceFacade.getOrderDialogInfo(mrpNoListStr);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/showOrderInfo.do" , method=RequestMethod.POST)
	public ModelMap showOrderInfo(HttpServletRequest request, HttpServletResponse response) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		try {
			ArrayList<OrderInfoTO> orderInfoList = materialServiceFacade.getOrderInfoList(startDate,endDate);
			map.put("gridRowJson", orderInfoList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}
	
	@RequestMapping(value="/searchOrderInfoListOnDelivery.do" , method=RequestMethod.POST)
	public ModelMap searchOrderInfoListOnDelivery(HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<OrderInfoTO> orderInfoListOnDelivery = materialServiceFacade.getOrderInfoListOnDelivery();
			map.put("gridRowJson", orderInfoListOnDelivery);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/order.do" , method=RequestMethod.POST)
	public ModelMap order(HttpServletRequest request, HttpServletResponse response) {
		String mrpGatheringNoListStr = request.getParameter("mrpGatheringNoList");
		try {
			ArrayList<String> mrpGaNoArr = gson.fromJson(mrpGatheringNoListStr , new TypeToken<ArrayList<String>>() {
				}.getType());
			
			map = materialServiceFacade.order(mrpGaNoArr);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/optionOrder.do" , method=RequestMethod.POST)
	public ModelMap optionOrder(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resultMap = new HashMap<>();
		String itemCode = request.getParameter("itemCode");
		String itemAmount = request.getParameter("itemAmount");
		try {
			resultMap = materialServiceFacade.optionOrder(itemCode, itemAmount);
			map.put("gridRowJson",resultMap);
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
