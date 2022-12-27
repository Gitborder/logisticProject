package com.estimulo.business.sales.applicationservice;

import java.util.ArrayList;

import org.springframework.ui.ModelMap;

import com.estimulo.business.sales.to.EstimateDetailTO;
import com.estimulo.business.sales.to.EstimateTO;

public interface EstimateApplicationService {
	
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate);

	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo);
	
	// ApplicationService 안에서만 호출
	public String getNewEstimateNo(String estimateDate);

	public ModelMap addNewEstimate(String estimateDate, EstimateTO newEstimateTO);

	public ModelMap batchEstimateDetailListProcess(ArrayList<EstimateDetailTO> estimateDetailTOList,String estimateNo);	
	
	public String getNewEstimateDetailNo(String estimateDate);
}