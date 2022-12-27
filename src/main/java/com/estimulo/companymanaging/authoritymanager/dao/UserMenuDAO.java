package com.estimulo.companymanaging.authoritymanager.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.companymanaging.authoritymanager.to.UserMenuTO;

@Mapper
public interface UserMenuDAO {

	public HashMap<String, UserMenuTO> selectUserMenuCodeList(String workplaceCode, String deptCode, String positionCode);

}
