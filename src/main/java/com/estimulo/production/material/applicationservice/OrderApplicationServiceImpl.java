package com.estimulo.production.material.applicationservice;


import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.estimulo.production.material.dao.OrderDAO;
import com.estimulo.production.material.to.OrderInfoTO;

@Component
public class OrderApplicationServiceImpl implements OrderApplicationService {

	// DAO 참조변수 선언
	@Autowired
	private OrderDAO orderDAO;

	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		orderDAO.getOrderList(map);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("gridRowJson", map.get("RESULT"));
		resultMap.put("errorCode",map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));
		
		System.out.println(map.get("RESULT"));
		
		return resultMap;
	}

	@Override
	public ModelMap getOrderDialogInfo(String mrpNoArr) {
		/* 	gson.fromJson 썼을 때
		 * 	String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
			System.out.println("mrpNoList = "+mrpNoList);
			resultMap = orderDAO.getOrderDialogInfo(mrpNoList);
		*/	
		String mrpNoList = mrpNoArr.replace("[", "").replace("]", "").replace("\"", "");
		System.out.println("mrpNoList = "+mrpNoList);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mrpNoList", mrpNoList);
		orderDAO.getOrderDialogInfo(map);
		
		ModelMap resultMap = new ModelMap();
		resultMap.put("gridRowJson", map.get("RESULT"));
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));
		
		return resultMap;
	}

	@Override
	public ModelMap order(ArrayList<String> mrpGaNoArr) {
		String mpsNoList = mrpGaNoArr.toString().replace("[", "").replace("]", "");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mpsNoList", mpsNoList);
		orderDAO.order(map);
		
		ModelMap resultMap = new ModelMap();
		resultMap.put("gridRowJson", map.get("RESULT"));
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));

    	return resultMap;
		
	}

	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("itemCode", itemCode);
		map.put("itemAmount", itemAmount);
        HashMap<String,Object> resultMap = orderDAO.optionOrder(map);

    	return resultMap;
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {
		 ArrayList<OrderInfoTO> orderInfoListOnDelivery = orderDAO.getOrderInfoListOnDelivery();

		return orderInfoListOnDelivery;
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		ArrayList<OrderInfoTO> orderInfoList = orderDAO.getOrderInfoList(map);
		 
		return orderInfoList;
	}

	@Override
	public ModelMap checkOrderInfo(ArrayList<String> orderNoArr) {
		String orderNoList = orderNoArr.toString().replace("[", "").replace("]", "");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("orderNoList", orderNoList);
		orderDAO.updateOrderInfo(map);

		ModelMap resultMap = new ModelMap();
		resultMap.put("gridRowJson", map.get("RESULT"));
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));
		
		return resultMap;
	}
}