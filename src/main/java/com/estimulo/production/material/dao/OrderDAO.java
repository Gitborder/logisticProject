package com.estimulo.production.material.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.production.material.to.OrderInfoTO;

@Mapper
public interface OrderDAO {
	
	 public void getOrderList(HashMap<String, Object> map);
	 
	 public void getOrderDialogInfo(HashMap<String, String> map);
	 
	 public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	 
	 public ArrayList<OrderInfoTO> getOrderInfoList(HashMap<String, String> map);

	 public void order(HashMap<String, String> map);
	 
	 public HashMap<String,Object> optionOrder(HashMap<String, String> map);

	 public void updateOrderInfo(HashMap<String, String> map);
}
