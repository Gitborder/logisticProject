package com.estimulo.production.material.to;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StockChartTO extends BaseTO {
	
	private String itemName, stockAmount, saftyAmount, allowanceAmount;

}
