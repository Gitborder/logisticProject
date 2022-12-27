package com.estimulo.business.sales.to;

import java.util.ArrayList;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContractInfoTO extends BaseTO {
	private String contractNo;
	private String estimateNo;
	private String contractType;
	private String contractTypeName;
	private String customerCode;
	private String customerName;
	private String estimateDate;
	private String contractDate;
	private String contractRequester;
	private String personCodeInCharge;
	private String empNameInCharge;
	private String description;
	private ArrayList<ContractDetailTO> contractDetailTOList;
	private String deliveryCompletionStatus;
	
	//이걸 뺴니까 에러가 나서 일단 그대로 뒀음.
	public ArrayList<ContractDetailTO> getContractDetailTOList() {
		return contractDetailTOList;
	}

	public void setContractDetailTOList(ArrayList<ContractDetailTO> contractDetailTOList) {
		this.contractDetailTOList = contractDetailTOList;
	}
}
