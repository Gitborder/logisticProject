package com.estimulo.production.operation.to;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InputMaterialsTO extends BaseTO {
	 private String inputItemNo;
	 private String productionResultNo;
	 private String description;
	 private String itemCode;
	 private String unitOfInputMaterials;
	 private String warehouseCode;
	 private String itemName;
	 private int inputAmount;
	 private String inputDate;
}