package com.estimulo.companymanaging.authoritymanager.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.companymanaging.authoritymanager.to.AuthorityGroupTO;
import com.estimulo.companymanaging.authoritymanager.to.EmployeeAuthorityTO;

@Mapper
public interface AuthorityGroupDAO {
	
	public ArrayList<AuthorityGroupTO> selectUserAuthorityGroupList(String empCode);

	public ArrayList<AuthorityGroupTO> selectAuthorityGroupList();
	
	public void insertEmployeeAuthorityGroup(EmployeeAuthorityTO bean);
	
	public void deleteEmployeeAuthorityGroup(String empCode);

}
