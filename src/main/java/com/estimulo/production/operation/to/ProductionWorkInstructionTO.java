package com.estimulo.production.operation.to;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionWorkInstructionTO extends BaseTO  {
	 private int workInstructionAmount;
	 private String workInstructionNo;
	 private String description;
	 private String itemCode;
	 private String productionStatus;
	 private String instructionDate;
	 private String mrpGatheringNo;
	 private String itemName;
	 private String unitOfWorkInstruction;
}