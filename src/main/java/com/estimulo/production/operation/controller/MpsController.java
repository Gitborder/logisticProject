package com.estimulo.production.operation.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.production.operation.servicefacade.ProductionServiceFacade;
import com.estimulo.production.operation.to.ContractDetailInMpsAvailableTO;
import com.estimulo.production.operation.to.MpsTO;
import com.estimulo.production.operation.to.SalesPlanInMpsAvailableTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/operation/*")
public class MpsController {

	ModelMap map = new ModelMap();
	
	@Autowired
	private ProductionServiceFacade productionServiceFacade;

	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/searchMpsInfo.do", method=RequestMethod.POST)
	public ModelMap searchMpsInfo(HttpServletRequest request, HttpServletResponse response) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String includeMrpApply = request.getParameter("includeMrpApply"); 
								// 포함 = includeMrpApply, 미포함 = excludeMrpApply;
		System.out.println("MPS 컨트롤러 값확인  - stDate : "+startDate+", endDate : "+endDate+", includeMrpApply:" +includeMrpApply);
		try {
			ArrayList<MpsTO> mpsTOList = productionServiceFacade.getMpsList(startDate, endDate, includeMrpApply);

			map.put("gridRowJson", mpsTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/searchContractDetailInMpsAvailable.do", method=RequestMethod.POST)
	public ModelMap searchContractDetailListInMpsAvailable(HttpServletRequest request,
			HttpServletResponse response) {
		String searchCondition = request.getParameter("searchCondition"); //수주일자 = contractDate, 납기일 = dueDateOfContract
		System.out.println(searchCondition);
		String startDate = request.getParameter("startDate");
		System.out.println(startDate);
		String endDate = request.getParameter("endDate");
		System.out.println(endDate);
		try {
			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = productionServiceFacade
					.getContractDetailListInMpsAvailable(searchCondition, startDate, endDate);
													   //contractDate, 2019-07-01, 2019-07-31
			map.put("gridRowJson", contractDetailInMpsAvailableList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value="/searchSalesPlanInMpsAvailable.do", method=RequestMethod.POST)
	public ModelMap searchSalesPlanListInMpsAvailable(HttpServletRequest request, HttpServletResponse response) {
		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		try {
			ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = productionServiceFacade
					.getSalesPlanListInMpsAvailable(searchCondition, startDate, endDate);

			map.put("gridRowJson", salesPlanInMpsAvailableList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value="/convertContractDetailToMps.do", method=RequestMethod.POST)
	public ModelMap convertContractDetailToMps(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList"); 
		System.out.println("batchList = "+batchList);
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = gson.fromJson(batchList,
				new TypeToken<ArrayList<ContractDetailInMpsAvailableTO>>() {
				}.getType());
				System.out.println("contractDetailInMpsAvailableList = "+contractDetailInMpsAvailableList);
		try {
			HashMap<String, Object> resultMap = productionServiceFacade
					.convertContractDetailToMps(contractDetailInMpsAvailableList);

			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/convertSalesPlanToMps.do", method=RequestMethod.POST)
	public ModelMap convertSalesPlanToMps(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList");
		try {
			ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = gson.fromJson(batchList,
					new TypeToken<ArrayList<SalesPlanInMpsAvailableTO>>() {
					}.getType());
			HashMap<String, Object> resultMap = productionServiceFacade.convertSalesPlanToMps(salesPlanInMpsAvailableList);

			map.put("result", resultMap);
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
