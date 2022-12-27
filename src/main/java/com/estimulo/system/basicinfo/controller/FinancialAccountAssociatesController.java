package com.estimulo.system.basicinfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.system.basicinfo.servicefacade.BasicInfoServiceFacade;
import com.estimulo.system.basicinfo.to.FinancialAccountAssociatesTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/basicinfo/*")
public class FinancialAccountAssociatesController {

	ModelMap map = new ModelMap();
	
	// serviceFacade 참조변수 선언
	@Autowired
	private	BasicInfoServiceFacade basicInfoServiceFacade;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	@RequestMapping(value = "/searchFinancialAccountAssociatesList.do", method = RequestMethod.POST)
	public ModelMap searchFinancialAccountAssociatesList(HttpServletRequest request, HttpServletResponse response) {
		String searchCondition = request.getParameter("searchCondition");
		String workplaceCode = request.getParameter("workplaceCode");
		ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = null;
		try {
			financialAccountAssociatesList = basicInfoServiceFacade.getFinancialAccountAssociatesList(searchCondition,
					workplaceCode);
	
			map.put("gridRowJson", financialAccountAssociatesList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value = "/batchFinancialAccountAssociatesListProcess.do", method = RequestMethod.POST)
	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList");
		try {
			ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = gson.fromJson(batchList,
					new TypeToken<ArrayList<FinancialAccountAssociatesTO>>() {
					}.getType());
			HashMap<String, Object> resultMap = basicInfoServiceFacade
					.batchFinancialAccountAssociatesListProcess(financialAccountAssociatesList);
	
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
