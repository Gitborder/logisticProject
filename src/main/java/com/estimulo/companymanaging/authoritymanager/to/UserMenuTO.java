package com.estimulo.companymanaging.authoritymanager.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserMenuTO {
	private int no;
	private int menuLevel;
	private int menuOrder;
	private String menuName;
	private int leaf;
	private String url;
	private String isAccessDenied;
}
