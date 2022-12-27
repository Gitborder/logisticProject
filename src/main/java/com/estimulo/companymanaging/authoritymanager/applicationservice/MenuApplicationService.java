package com.estimulo.companymanaging.authoritymanager.applicationservice;

import java.util.ArrayList;

import com.estimulo.companymanaging.authoritymanager.to.MenuAuthorityTO;

public interface MenuApplicationService {
	
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList);

	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode);
	
	public String[] getAllMenuList();
	
}
