package com.estimulo.business.sales.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

import com.estimulo.business.sales.to.DeliveryInfoTO;


public interface DeliveryApplicationService {

	public ArrayList<DeliveryInfoTO> getDeliveryInfoList();

	public HashMap<String, Object> batchDeliveryListProcess(List<DeliveryInfoTO> deliveryTOList);

	public ModelMap deliver(String contractDetailNo);
	
}


