package com.estimulo.system.basicinfo.to;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerTO extends BaseTO  {
	 private String socialSecurityNumber;
	 private String customerBasicAddress;
	 private String customerBusinessConditions;
	 private String customerZipCode;
	 private String customerDetailAddress;
	 private String customerType;
	 private String customerNote;
	 private String businessLicenseNumber;
	 private String customerCeo;
	 private String customerName;
	 private String customerBusinessItems;
	 private String workplaceCode;
	 private String customerTelNumber;
	 private String customerCode;
	 private String customerFaxNumber;
	 private String producedProduct;
	 private String itemCode;
	 private String itemName;
	 private String itemGroupName;

}