package com.estimulo.companymanaging.logisticsinfo.to;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ItemTO extends BaseTO {
	 private String itemGroupCode;
	 private String leadTime;
	 private String unitOfStock;
	 private int standardUnitPrice;
	 private String description;
	 private String itemCode;
	 private String itemClassification;
	 private String lossRate;
	 private String itemName;
}