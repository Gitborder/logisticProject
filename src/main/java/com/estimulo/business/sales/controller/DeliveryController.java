package com.estimulo.business.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.business.sales.servicefacade.SalesServiceFacade;
import com.estimulo.business.sales.to.ContractInfoTO;
import com.estimulo.business.sales.to.DeliveryInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/sales/*")
public class DeliveryController {
	ModelMap map=new ModelMap();
	
	// serviceFacade 참조변수 선언
	@Autowired
	private SalesServiceFacade salesServiceFacade;
	
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 변환

	@RequestMapping(value="/searchDeliveryInfoList.do" ,method=RequestMethod.POST)
	public ModelMap searchDeliveryInfoList(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<DeliveryInfoTO> deliveryInfoList = null;
		try {
			deliveryInfoList = salesServiceFacade.getDeliveryInfoList();

			map.put("gridRowJson", deliveryInfoList);
			map.put("errorCode", 0);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	// batchListProcess
	@RequestMapping(value="/batchListProcess.do" ,method=RequestMethod.POST)
	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList");
		try {
			List<DeliveryInfoTO> deliveryTOList = gson.fromJson(batchList, new TypeToken<ArrayList<DeliveryInfoTO>>() {
			}.getType());
			HashMap<String, Object> resultMap = salesServiceFacade.batchDeliveryListProcess(deliveryTOList);

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

	@RequestMapping(value="/searchDeliverableContractList.do" ,method=RequestMethod.POST)
	public ModelMap searchDeliverableContractList(HttpServletRequest request, HttpServletResponse response) {
		String ableContractInfo =request.getParameter("ableContractInfo");
		try {
			HashMap<String,String> ableSearchConditionInfo = gson.fromJson(ableContractInfo, new TypeToken<HashMap<String,String>>() {
			}.getType());
			
			ArrayList<ContractInfoTO> deliverableContractList = null;
			deliverableContractList = salesServiceFacade.getDeliverableContractList(ableSearchConditionInfo);
			
			map.put("gridRowJson", deliverableContractList);
			map.put("errorCode", 0);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/deliver.do" ,method=RequestMethod.POST)
	public ModelMap deliver(HttpServletRequest request, HttpServletResponse response) {
		String contractDetailNo = request.getParameter("contractDetailNo");
		try {
			map = salesServiceFacade.deliver(contractDetailNo);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
}
