package com.estimulo.production.material.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.ui.ModelMap;

import com.estimulo.production.material.to.OrderInfoTO;

public interface OrderApplicationService {
	
	public HashMap<String,Object> getOrderList(String startDate, String endDate);

	public ModelMap getOrderDialogInfo(String mrpNoArr);

	public ModelMap order(ArrayList<String> mrpGaNoArr);
	
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount);
	
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate,String endDate);

	public ModelMap checkOrderInfo(ArrayList<String> orderNoArr);

}
