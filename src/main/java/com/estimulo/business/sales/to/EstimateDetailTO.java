package com.estimulo.business.sales.to;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EstimateDetailTO extends BaseTO {
	private String unitOfEstimate;
	private String estimateNo;
	private int unitPriceOfEstimate;
	private String estimateDetailNo;
	private long sumPriceOfEstimate;
	private String description;
	private String itemCode;
	private int estimateAmount;
	private String dueDateOfEstimate;
	private String itemName;
}