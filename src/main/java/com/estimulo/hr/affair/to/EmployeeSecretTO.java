package com.estimulo.hr.affair.to;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeSecretTO extends BaseTO {
	 private String companyCode;
	 private String empCode;
	 private int seq;
	 private String userPassword;
}