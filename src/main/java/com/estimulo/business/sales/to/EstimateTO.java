package com.estimulo.business.sales.to;

import java.util.ArrayList;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EstimateTO extends BaseTO {
	private String effectiveDate;
	private String estimateNo;
	private String estimateRequester;
	private String description;
	private String contractStatus;
	private String customerCode;
	private String personCodeInCharge;
	private String personNameCharge;
	private String estimateDate;
	private String customerName;
	private ArrayList<EstimateDetailTO> estimateDetailTOList;
	
	 public ArrayList<EstimateDetailTO> getEstimateDetailTOList() {
		return estimateDetailTOList;
	}
	public void setEstimateDetailTOList(ArrayList<EstimateDetailTO> estimateDetailTOList) {
		this.estimateDetailTOList = estimateDetailTOList;
	}
}