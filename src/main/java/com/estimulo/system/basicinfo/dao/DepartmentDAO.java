package com.estimulo.system.basicinfo.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.system.basicinfo.to.DepartmentTO;

@Mapper
public interface DepartmentDAO {

	public ArrayList<DepartmentTO> selectDepartmentListByCompany(String companyCode);

	public ArrayList<DepartmentTO> selectDepartmentListByWorkplace(String workplaceCode);

	public void insertDepartment(DepartmentTO TO);

	public void updateDepartment(DepartmentTO TO);

	public void deleteDepartment(DepartmentTO TO);
}
