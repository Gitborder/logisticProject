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
import com.estimulo.system.basicinfo.to.WorkplaceTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/basicinfo/*")
public class WorkplaceController {

	ModelMap map = new ModelMap();
	
	// serviceFacade 참조변수 선언
	@Autowired
	private	BasicInfoServiceFacade basicInfoServiceFacade;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	@RequestMapping(value = "/searchWorkplace.do", method = RequestMethod.GET)
	public ModelMap searchWorkplaceList(HttpServletRequest request, HttpServletResponse response) {
		String companyCode = request.getParameter("companyCode");
		ArrayList<WorkplaceTO> workplaceList = null;
		try {
			workplaceList = basicInfoServiceFacade.getWorkplaceList(companyCode);
	
			map.put("gridRowJson", workplaceList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value = "/batchWorkplaceListProcess.do", method = RequestMethod.GET)
	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList");
		try {
			ArrayList<WorkplaceTO> workplaceList = gson.fromJson(batchList, new TypeToken<ArrayList<WorkplaceTO>>() {
				}.getType());
			HashMap<String, Object> resultMap = basicInfoServiceFacade.batchWorkplaceListProcess(workplaceList);
	
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
