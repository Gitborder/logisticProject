package com.estimulo.business.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.business.sales.to.DeliveryInfoTO;

@Mapper
public interface DeliveryDAO {

	public ArrayList<DeliveryInfoTO> selectDeliveryInfoList();
	
	public void deliver(HashMap<String, Object> map);
	
	public void insertDeliveryResult(DeliveryInfoTO TO);

	public void updateDeliveryResult(DeliveryInfoTO TO);

	public void deleteDeliveryResult(DeliveryInfoTO TO);
}
