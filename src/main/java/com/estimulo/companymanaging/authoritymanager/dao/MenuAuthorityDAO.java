package com.estimulo.companymanaging.authoritymanager.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.companymanaging.authoritymanager.to.AuthorityGroupMenuTO;
import com.estimulo.companymanaging.authoritymanager.to.MenuAuthorityTO;

@Mapper
public interface MenuAuthorityDAO {
	
	public void deleteMenuAuthority(String authorityGroupCode);
	
	public void insertMenuAuthority(MenuAuthorityTO bean);
	
	public ArrayList<MenuAuthorityTO> selectMenuAuthorityList(String authorityGroupCode);
	
	public ArrayList<AuthorityGroupMenuTO> selectUserMenuAuthorityList(String empCode);
		
}
