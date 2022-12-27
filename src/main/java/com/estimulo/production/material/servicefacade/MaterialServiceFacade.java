package com.estimulo.production.material.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.ui.ModelMap;

import com.estimulo.production.material.to.BomDeployTO;
import com.estimulo.production.material.to.BomInfoTO;
import com.estimulo.production.material.to.BomTO;
import com.estimulo.production.material.to.OrderInfoTO;
import com.estimulo.production.material.to.StockChartTO;
import com.estimulo.production.material.to.StockLogTO;
import com.estimulo.production.material.to.StockTO;



public interface MaterialServiceFacade {

	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode, String itemClassificationCondition);
	
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode);
	
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable();
	
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList);
	
	public HashMap<String,Object> getOrderList(String startDate, String endDate);
	
	public ModelMap getOrderDialogInfo(String mrpNoArr);
	
	public ModelMap order(ArrayList<String> mrpGaNoArr);
	
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount);
	
	public ModelMap warehousing(ArrayList<String> orderNoArr);
	
	public ArrayList<StockTO> getStockList();
	
	public void batchStockProcess(ArrayList<StockTO> stockTOList);
	
	public ArrayList<StockTO> getWarehouseStockList(String warehouseCode);
	
	public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate);
	
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate,String endDate);

	public ModelMap changeSafetyAllowanceAmount(String itemCode, String itemName,
			String safetyAllowanceAmount);

	public ModelMap checkOrderInfo(ArrayList<String> orderNoArr);
	
	public ArrayList<StockChartTO> getStockChart();
}
