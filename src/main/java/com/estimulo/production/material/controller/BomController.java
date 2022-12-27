package com.estimulo.production.material.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.production.material.servicefacade.MaterialServiceFacade;
import com.estimulo.production.material.to.BomDeployTO;
import com.estimulo.production.material.to.BomInfoTO;
import com.estimulo.production.material.to.BomTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/material/*")
public class BomController {
	
	private ModelMap map= new ModelMap();

	@Autowired
	private MaterialServiceFacade materialServiceFacade;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/searchBomDeploy.do", method=RequestMethod.POST)
	public ModelMap searchBomDeploy(HttpServletRequest request, HttpServletResponse response) {
		String deployCondition = request.getParameter("deployCondition");
		// System.out.println(deployCondition);
		// forward 정전개 || reverse 역전개
		String itemCode = request.getParameter("itemCode");
		// System.out.println(itemCode);
		// CodeController를 사용하여 검색한 후 선택하여 텍스트박스에 들어있던 값을 파라미터로 받아옴
		// ex ] DK-01
		String itemClassificationCondition = request.getParameter("itemClassificationCondition");
		try {
			ArrayList<BomDeployTO> bomDeployList = materialServiceFacade.getBomDeployList(deployCondition, itemCode, itemClassificationCondition);

			map.put("gridRowJson", bomDeployList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value="/searchBomInfo.do", method=RequestMethod.POST)
	public ModelMap searchBomInfo(HttpServletRequest request, HttpServletResponse response) {
		String parentItemCode = request.getParameter("parentItemCode");
		try {
			ArrayList<BomInfoTO> bomInfoList = materialServiceFacade.getBomInfoList(parentItemCode);

			map.put("gridRowJson", bomInfoList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		
		return map;
	}

	@RequestMapping(value="/searchAllItemWithBomRegisterAvailable.do", method=RequestMethod.POST)
	public ModelMap searchAllItemWithBomRegisterAvailable(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = materialServiceFacade.getAllItemWithBomRegisterAvailable();

			map.put("gridRowJson", allItemWithBomRegisterAvailable);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/batchBomListProcess.do", method=RequestMethod.POST)
	public ModelMap batchBomListProcess(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList");

		ArrayList<BomTO> batchBomList = gson.fromJson(batchList, new TypeToken<ArrayList<BomTO>>() {
		}.getType());
		try {
			HashMap<String, Object> resultList = materialServiceFacade.batchBomListProcess(batchBomList);

			map.put("result", resultList);
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
