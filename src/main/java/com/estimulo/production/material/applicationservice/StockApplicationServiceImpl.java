package com.estimulo.production.material.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.estimulo.production.material.dao.StockDAO;
import com.estimulo.production.material.to.StockChartTO;
import com.estimulo.production.material.to.StockLogTO;
import com.estimulo.production.material.to.StockTO;

@Component
public class StockApplicationServiceImpl implements StockApplicationService{
	
	@Autowired
	private StockDAO stockDAO;

	@Override
	public ArrayList<StockTO> getStockList() {
		ArrayList<StockTO> stockList = stockDAO.selectStockList();

		return stockList;
	}

	@Override
	public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate) {
		ArrayList<StockLogTO> StockLogList = stockDAO.selectStockLogList(startDate,endDate);

		return StockLogList;
	}

	@Override
	public ModelMap warehousing(ArrayList<String> orderNoArr) {
		String orderNoList = orderNoArr.toString().replace("[", "").replace("]", "");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("orderNoList", orderNoList);
		stockDAO.warehousing(map);
		
		ModelMap resultMap = new ModelMap();
		resultMap.put("errorCode", map.get("ERROR_CODE")); //성공시 0
		resultMap.put("errorMsg", map.get("ERROR_MSG")); //## MPS등록완료 ##
		
    	return resultMap;
	}
	
	@Override
	public ModelMap changeSafetyAllowanceAmount(String itemCode, String itemName,
			String safetyAllowanceAmount) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("itemCode", itemCode);
		map.put("itemName", itemName);
		map.put("safetyAllowanceAmount", safetyAllowanceAmount);
		stockDAO.updatesafetyAllowance(map);
			
		ModelMap resultMap = new ModelMap();
		resultMap.put("errorCode", map.get("ERROR_CODE")); //성공시 0
		resultMap.put("errorMsg", map.get("ERROR_MSG")); //## MPS등록완료 ##
		
    	return resultMap;
	}
	
	@Override
	public ArrayList<StockChartTO> getStockChart() {
		ArrayList<StockChartTO> stockChart = stockDAO.selectStockChart();

		return stockChart;
	}
	
	@Override
	public ArrayList<StockTO> getWarehouseStockList(String warehouseCode) {
		ArrayList<StockTO> stockList = stockDAO.selectWarehouseStockList(warehouseCode);
		
		return stockList;
	}
	
	@Override
	public void batchStockProcess(ArrayList<StockTO> stockTOList) {
		for (StockTO bean : stockTOList) {
			String status = bean.getStatus();
			switch (status) {
				case "DELETE":
					System.out.println("*******************DELETE******************");
					stockDAO.deleteStock(bean);
					break;
				case "INSERT":
					System.out.println("*******************INSERT******************");
					stockDAO.insertStock(bean);
					break;
				case "UPDATE":
					System.out.println("*******************UPDATE******************");
					stockDAO.updateStock(bean);
			}
		}
	}
}
