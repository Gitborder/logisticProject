package com.estimulo.business.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.business.sales.servicefacade.SalesServiceFacade;
import com.estimulo.business.sales.to.EstimateDetailTO;
import com.estimulo.business.sales.to.EstimateTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


@RestController
@RequestMapping("/sales/*")
public class EstimateController {
	ModelMap map=new ModelMap();

	// serviceFacade 참조변수 선언
	@Autowired
	private SalesServiceFacade salesServiceFacade;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/searchEstimate.do", method=RequestMethod.POST)
	public ModelMap searchEstimateInfo(HttpServletRequest request, HttpServletResponse response) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String dateSearchCondition = request.getParameter("dateSearchCondition");
		try {
			ArrayList<EstimateTO> estimateTOList = salesServiceFacade.getEstimateList(dateSearchCondition, startDate, endDate);

			map.put("gridRowJson", estimateTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
		
	
	}

	@RequestMapping(value="/searchEstimateDetailInfo.do", method=RequestMethod.POST)
	public ModelMap searchEstimateDetailInfo(HttpServletRequest request, HttpServletResponse response) {
		String estimateNo = request.getParameter("estimateNo");
		try {
			ArrayList<EstimateDetailTO> estimateDetailTOList = salesServiceFacade.getEstimateDetailList(estimateNo);

			map.put("gridRowJson", estimateDetailTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value="/addNewEstimate.do", method=RequestMethod.POST)
	public ModelMap addNewEstimate(HttpServletRequest request, HttpServletResponse response) {
		String estimateDate = request.getParameter("estimateDate");
		String newEstimateInfo = request.getParameter("newEstimateInfo"); //견적과 견적상세의 데이터값의 배열
		EstimateTO newEstimateTO = gson.fromJson(newEstimateInfo, EstimateTO.class);
		// newEstimateInfo 는 뷰단에서 보낸 json 문자열 => 문자열을 자바 객체로 변환
		try {
			HashMap<String, Object> resultList = salesServiceFacade.addNewEstimate(estimateDate, newEstimateTO);
			//견적일자와 견적,견적상세의 json객체를 EstimateTO로 변환한 newEstimateTO를 map에 담음
			
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

	@RequestMapping(value="/batchEstimateDetailListProcess.do", method=RequestMethod.POST)
	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList");
//		String estimateNo = request.getParameter("estimateNo");
		ArrayList<EstimateDetailTO> estimateDetailTOList = gson.fromJson(batchList,
				new TypeToken<ArrayList<EstimateDetailTO>>() {
				}.getType());
		try {
			HashMap<String, Object> resultList = salesServiceFacade.batchEstimateDetailListProcess(estimateDetailTOList,estimateDetailTOList.get(0).getEstimateNo());

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
