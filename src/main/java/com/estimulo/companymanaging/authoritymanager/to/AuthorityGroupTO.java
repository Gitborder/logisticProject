package com.estimulo.companymanaging.authoritymanager.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorityGroupTO {
	String authorityGroupCode, authorityGroupName, authority;
	public String getUserAuthorityGroupCode() {
		return authorityGroupCode;
	}

	public void setAuthorityGroupCode(String authorityGroupCode) {
		this.authorityGroupCode = authorityGroupCode;
	}

	public String getUserAuthorityGroupName() {
		return authorityGroupName;
	}

	public void setAuthorityGroupName(String authorityGroupName) {
		this.authorityGroupName = authorityGroupName;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}