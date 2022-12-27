
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
import com.estimulo.business.sales.to.ContractDetailTO;
import com.estimulo.business.sales.to.ContractInfoTO;
import com.estimulo.business.sales.to.EstimateTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/sales/*")
public class ContractController {

	// serviceFacade 참조변수 선언
	@Autowired
	private SalesServiceFacade salesServiceFacade;
	
	ModelMap map=new ModelMap();
	private static Gson gson = new GsonBuilder().serializeNulls().create();

	@RequestMapping(value="/serchContract.do" , method=RequestMethod.POST)
	public ModelMap searchContract(HttpServletRequest request, HttpServletResponse response) {
		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String customerCode = request.getParameter("customerCode");
		try {
			ArrayList<ContractInfoTO> contractInfoTOList = null;
			contractInfoTOList = salesServiceFacade.getContractList(searchCondition, startDate ,endDate ,customerCode);
			map.put("gridRowJson", contractInfoTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	/*
	@RequestMapping(value="/searchContractNO.do" , method=RequestMethod.POST)
	public ModelMap searchContractNO(HttpServletRequest request, HttpServletResponse response) {
		String searchCondition = request.getParameter("searchCondition");
		try {
			ArrayList<ContractInfoTO> contractInfoTOList = null;
			if (searchCondition.equals("searchByDate")) {
				String customerCode = "";
				String[] paramArray = { customerCode };
				contractInfoTOList = salesServiceFacade.getContractList("searchByCustomer", paramArray);
			}
			map.put("gridRowJson", contractInfoTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	*/

	@RequestMapping(value="/serchContractDetail.do" , method=RequestMethod.POST)
	public ModelMap searchContractDetail(HttpServletRequest request, HttpServletResponse response) {
		String contractNo = request.getParameter("contractNo");
		try {
			ArrayList<ContractDetailTO> contractDetailTOList = salesServiceFacade.getContractDetailList(contractNo);

			map.put("gridRowJson", contractDetailTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value= "/searchEstimateInContractAvailable.do", method=RequestMethod.POST)
	public ModelMap searchEstimateInContractAvailable(HttpServletRequest request, HttpServletResponse response) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		System.out.println(startDate+"::::"+endDate);
		try {
			ArrayList<EstimateTO> estimateListInContractAvailable = salesServiceFacade.getEstimateListInContractAvailable(startDate, endDate);

			map.put("gridRowJson", estimateListInContractAvailable);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value="/addNewContract.do" , method=RequestMethod.POST)
	public ModelMap addNewContract(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList"); 
		System.out.println("@batchList="+batchList);
		try {	
			HashMap<String,String[]>workingContractList = gson.fromJson(batchList,new TypeToken<HashMap<String,String[]>>() {}.getType()) ;
			map = salesServiceFacade.addNewContract(workingContractList);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}

	@RequestMapping(value="/cancleEstimate.do" , method=RequestMethod.POST)
	public ModelMap cancleEstimate(HttpServletRequest request, HttpServletResponse response) {
		String estimateNo = request.getParameter("estimateNo");
		try {
			salesServiceFacade.changeContractStatusInEstimate(estimateNo, "N");

			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");
			map.put("cancledEstimateNo", estimateNo);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return map;
	}
}
