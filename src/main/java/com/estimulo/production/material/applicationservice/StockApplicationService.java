package com.estimulo.production.material.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.ui.ModelMap;

import com.estimulo.production.material.to.StockChartTO;
import com.estimulo.production.material.to.StockLogTO;
import com.estimulo.production.material.to.StockTO;

public interface StockApplicationService {
	
	public ArrayList<StockTO> getStockList();
	
	public ArrayList<StockTO> getWarehouseStockList(String warehouseCode);
	
	public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate);
	
	public ModelMap warehousing(ArrayList<String> orderNoArr);
	
	public ModelMap changeSafetyAllowanceAmount(String itemCode, String itemName,
			String safetyAllowanceAmount);
	
	public ArrayList<StockChartTO> getStockChart();
	
	public void batchStockProcess(ArrayList<StockTO> stockTOList);
}
