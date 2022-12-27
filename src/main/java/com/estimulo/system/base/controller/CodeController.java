package com.estimulo.system.base.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.system.base.servicefacade.BaseServiceFacade;
import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.base.to.CodeTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping(value = {"/logisticsinfo/*", "/base/*"})
public class CodeController {

	ModelMap map = new ModelMap();
	
	@Autowired
	private BaseServiceFacade baseServiceFacade;

	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	/////////////////////////////////코드상세///////////////////////////////////   
	@RequestMapping(value = "/searchCodeDetilList.do", method = RequestMethod.POST)
	public ModelMap findCodeDetailList(HttpServletRequest request, HttpServletResponse response) {
		String CodeDetail  = request.getParameter("divisionCodeNo");
		try {
			ArrayList<CodeDetailTO> codeLists = baseServiceFacade.getCodeDetailList(CodeDetail);

			map.put("codeList", codeLists);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	////////저장
	@RequestMapping(value = "/codechange.do", method = RequestMethod.POST)
	public ModelMap addCodeInFormation(HttpServletRequest request, HttpServletResponse response) {
		String newcodeInfo = request.getParameter("newCodeInfo"); //견적과 견적상세의 데이터값의 배열
		System.out.println("            newcodeInfo : " + newcodeInfo);
		try { 
			ArrayList<CodeTO> CodeTOList = gson.fromJson(newcodeInfo,
				new TypeToken<ArrayList<CodeTO>>() {}.getType());
			System.out.println("          여기사람있어요 : " + CodeTOList);
			
			baseServiceFacade.addCodeInFormation(CodeTOList);
			     
		    map.put("errorCode", 1);
		    map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value = "/searchCodeList.do", method = RequestMethod.POST)
	public ModelMap findCodeList(HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<CodeTO> codeList = baseServiceFacade.getCodeList();

			map.put("codeList", codeList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value = "/codeList.do", method = RequestMethod.POST)
	public ModelMap findDetailCodeList(HttpServletRequest request, HttpServletResponse response) {
		String divisionCode = request.getParameter("divisionCodeNo");	
		System.out.println("		  @"+divisionCode);
		try {
			ArrayList<CodeDetailTO> detailCodeList = baseServiceFacade.getDetailCodeList(divisionCode); 

			map.put("detailCodeList", detailCodeList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value = "/checkCodeDuplication.do", method = RequestMethod.POST)
	public ModelMap checkCodeDuplication(HttpServletRequest request, HttpServletResponse response) {
		String divisionCode = request.getParameter("divisionCode");
		String newDetailCode = request.getParameter("newCode");
		try {
			Boolean flag = baseServiceFacade.checkCodeDuplication(divisionCode, newDetailCode);

			map.put("result", flag);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value = "/batchListProcess.do", method = RequestMethod.POST)
	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList");
		String tableName = request.getParameter("tableName");
		try {
			ArrayList<CodeTO> codeList = null;
			ArrayList<CodeDetailTO> detailCodeList = null;
			HashMap<String, Object> resultMap = null;
			if (tableName.equals("CODE")) {
				codeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeTO>>() {
				}.getType());
				//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
				resultMap = baseServiceFacade.batchCodeListProcess(codeList);
			} else if (tableName.equals("CODE_DETAIL")) {
				detailCodeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeDetailTO>>() {
				}.getType());
				//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
				resultMap = baseServiceFacade.batchDetailCodeListProcess(detailCodeList);
			}
			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} return map;
	}

	@RequestMapping(value = "/changeCodeUseCheckProcess.do", method = RequestMethod.POST)
	public ModelMap changeCodeUseCheckProcess(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList");
		try {
			ArrayList<CodeDetailTO> detailCodeList = null;
			HashMap<String, Object> resultMap = null;

			detailCodeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeDetailTO>>() {
			}.getType());
			//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
			resultMap = baseServiceFacade.changeCodeUseCheckProcess(detailCodeList);

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
