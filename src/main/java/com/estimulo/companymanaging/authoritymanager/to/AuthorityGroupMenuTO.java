package com.estimulo.companymanaging.authoritymanager.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorityGroupMenuTO {
	String authorityGroupCode, menuCode, menuName;
}
