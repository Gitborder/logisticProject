package com.estimulo.business.sales.to;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContractDetailTO extends BaseTO {
	private String contractDetailNo;
	private String contractNo;
	private String itemCode;
	private String itemName;	
	private String unitOfContract; 
	private String dueDateOfContract;	
	private String estimateAmount;
	private String stockAmountUse; 
	private String productionRequirement;     
	private String unitPriceOfContract;	
	private String sumPriceOfContract;
	private String processingStatus;
	private String operationCompletedStatus;	
	private String deliveryCompletionStatus;	
	private String description;
	private String mrpGatheringNo;
}