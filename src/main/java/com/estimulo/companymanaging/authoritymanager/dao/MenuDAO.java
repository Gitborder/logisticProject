package com.estimulo.companymanaging.authoritymanager.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.companymanaging.authoritymanager.to.MenuTO;

@Mapper
public interface MenuDAO {

	public ArrayList<MenuTO> selectAllMenuList();
	
}
