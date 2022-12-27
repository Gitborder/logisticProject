package com.estimulo.production.material.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.estimulo.production.material.applicationservice.BomApplicationService;
import com.estimulo.production.material.applicationservice.OrderApplicationService;
import com.estimulo.production.material.applicationservice.StockApplicationService;
import com.estimulo.production.material.to.BomDeployTO;
import com.estimulo.production.material.to.BomInfoTO;
import com.estimulo.production.material.to.BomTO;
import com.estimulo.production.material.to.OrderInfoTO;
import com.estimulo.production.material.to.StockChartTO;
import com.estimulo.production.material.to.StockLogTO;
import com.estimulo.production.material.to.StockTO;

@Service
public class MaterialServiceFacadeImpl implements MaterialServiceFacade {

	ModelMap modelMap = new ModelMap();
	   
	@Autowired
	private BomApplicationService bomAS;
	@Autowired
	private OrderApplicationService orderAS;
	@Autowired
	private StockApplicationService stockAS;

	@Override
	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode,
			String itemClassificationCondition) {
		ArrayList<BomDeployTO> bomDeployList = null;
		bomDeployList = bomAS.getBomDeployList(deployCondition, itemCode, itemClassificationCondition);
		
		return bomDeployList;
	}

	@Override
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {
		ArrayList<BomInfoTO> bomInfoList = bomAS.getBomInfoList(parentItemCode);
		
		return bomInfoList;
	}

	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {
        HashMap<String,Object> resultMap = orderAS.getOrderList(startDate, endDate);
		
		return resultMap;
	}

	@Override
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {
		 ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = bomAS.getAllItemWithBomRegisterAvailable();
			
		return allItemWithBomRegisterAvailable;
	}

	@Override
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {
		HashMap<String, Object> resultMap = bomAS.batchBomListProcess(batchBomList);
		
		return resultMap;
	}

	@Override
	public ModelMap getOrderDialogInfo(String mrpNoArr) {
		ModelMap resultMap = orderAS.getOrderDialogInfo(mrpNoArr);

		return resultMap;
	}

	@Override
	public ModelMap order(ArrayList<String> mrpGaNoArr) {
		ModelMap resultMap = orderAS.order(mrpGaNoArr);
    	
    	return resultMap;
	}

	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {
        HashMap<String,Object> resultMap = orderAS.optionOrder(itemCode, itemAmount);
        
    	return resultMap;
	}

	@Override
	public ArrayList<StockTO> getStockList() {
		ArrayList<StockTO>  stockList = stockAS.getStockList();
		
		return stockList;
	}

	@Override
	public ArrayList<StockLogTO> getStockLogList(String startDate, String endDate) {
		ArrayList<StockLogTO> stockLogList = stockAS.getStockLogList(startDate, endDate);
		
		return stockLogList;
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {
		ArrayList<OrderInfoTO> orderInfoListOnDelivery = orderAS.getOrderInfoListOnDelivery();
		
		return orderInfoListOnDelivery;
	}

	@Override
	public ModelMap warehousing(ArrayList<String> orderNoArr) {
		ModelMap resultMap = stockAS.warehousing(orderNoArr);
		
    	return resultMap;
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {
		ArrayList<OrderInfoTO> orderInfoList = orderAS.getOrderInfoList(startDate,endDate);
		
		return orderInfoList;
	}

	@Override
	public ModelMap changeSafetyAllowanceAmount(String itemCode, String itemName,
			String safetyAllowanceAmount) {
		ModelMap resultMap = stockAS.changeSafetyAllowanceAmount(itemCode, itemName, safetyAllowanceAmount);
		
		return resultMap;
	}

	@Override
	public ModelMap checkOrderInfo(ArrayList<String> orderNoArr) {
		ModelMap resultMap = orderAS.checkOrderInfo(orderNoArr);
		
		return resultMap;
	}
	
	@Override
	public ArrayList<StockChartTO> getStockChart() {
		ArrayList<StockChartTO> stockChart  = stockAS.getStockChart();
		
		return stockChart;
	}
	
	@Override
	public ArrayList<StockTO> getWarehouseStockList(String warehouseCode) {
		ArrayList<StockTO> stockList = stockAS.getWarehouseStockList(warehouseCode);

		return stockList;
	}
	
	@Override
	public void batchStockProcess(ArrayList<StockTO> stockTOList) {
		stockAS.batchStockProcess(stockTOList);
	}
}
