package com.estimulo.production.material.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.production.material.servicefacade.MaterialServiceFacade;
import com.estimulo.production.material.to.StockChartTO;
import com.estimulo.production.material.to.StockLogTO;
import com.estimulo.production.material.to.StockTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/material/*")
public class StockController {
	
	private ModelMap map = new ModelMap();
	
	// serviceFacade 참조변수 선언
	@Autowired
	private MaterialServiceFacade materialServiceFacade;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/searchStockList.do" , method=RequestMethod.POST)
	public ModelMap searchStockList(HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<StockTO> stockList = materialServiceFacade.getStockList();
			
			map.put("gridRowJson", stockList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value="/searchStockLogList.do" , method=RequestMethod.POST)
	public ModelMap searchStockLogList(HttpServletRequest request, HttpServletResponse response) {
		map = new ModelMap();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		try {
			ArrayList<StockLogTO> stockLogList = materialServiceFacade.getStockLogList(startDate,endDate);

			map.put("gridRowJson", stockLogList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/warehousing.do" , method=RequestMethod.POST)
	public ModelMap warehousing(HttpServletRequest request, HttpServletResponse response) {
		String orderNoListStr = request.getParameter("orderNoList");
		ArrayList<String> orderNoArr = gson.fromJson(orderNoListStr,new TypeToken<ArrayList<String>>(){}.getType());
		try {
			map = materialServiceFacade.warehousing(orderNoArr);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/safetyAllowanceAmountChange.do" , method=RequestMethod.POST)
	public ModelMap safetyAllowanceAmountChange(HttpServletRequest request, HttpServletResponse response) {
		String itemCode  = request.getParameter("itemCode");
		String itemName  = request.getParameter("itemName");
		String safetyAllowanceAmount  = request.getParameter("safetyAllowanceAmount");

		System.out.println("itemCode:"+itemCode+"itemName:"+itemName+"safetyAllowanceAmount:"+safetyAllowanceAmount);
		try {
			map = materialServiceFacade.changeSafetyAllowanceAmount(itemCode,itemName,safetyAllowanceAmount);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/searchStockChart.do" , method=RequestMethod.POST)
	public ModelMap getStockChart(HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<StockChartTO> stockList = materialServiceFacade.getStockChart();

			map.put("gridRowJson", stockList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/searchWarehouseStockList.do" , method=RequestMethod.POST)
	public ModelMap searchWarehouseStockList(HttpServletRequest request, HttpServletResponse response) {
		String warehouseCode  = request.getParameter("warehouseCode");
		try {
			ArrayList<StockTO> stockList = materialServiceFacade.getWarehouseStockList(warehouseCode);
	
			map.put("gridRowJson", stockList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/batchStockProcess.do" , method=RequestMethod.POST)
	public ModelMap modifyStockInfo(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList");
		try {
			ArrayList<StockTO> stockTOList = gson.fromJson(batchList,
					new TypeToken<ArrayList<StockTO>>() {}.getType());
		
			materialServiceFacade.batchStockProcess(stockTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
}
