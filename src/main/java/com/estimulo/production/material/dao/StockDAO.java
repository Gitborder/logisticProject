package com.estimulo.production.material.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.ui.ModelMap;

import com.estimulo.production.material.to.StockChartTO;
import com.estimulo.production.material.to.StockLogTO;
import com.estimulo.production.material.to.StockTO;

@Mapper
public interface StockDAO {
	
	public ArrayList<StockTO> selectStockList();
	
	public ArrayList<StockTO> selectWarehouseStockList(String warehouseCode);
	
	public ArrayList<StockLogTO> selectStockLogList(String startDate,String endDate);
	
	public void warehousing(HashMap<String, Object> map);
	
	public ModelMap updatesafetyAllowance(HashMap<String, String> map);
	
	public ArrayList<StockChartTO> selectStockChart();
	
	public void insertStock(StockTO bean);
	
	public void updateStock(StockTO bean);
	
	public void deleteStock(StockTO bean);
}
