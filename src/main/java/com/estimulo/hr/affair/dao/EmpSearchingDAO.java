package com.estimulo.hr.affair.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.hr.affair.to.EmpInfoTO;

@Mapper
public interface EmpSearchingDAO {

	public ArrayList<EmpInfoTO> selectAllEmpList(HashMap<String, String> map);

	public ArrayList<EmpInfoTO> getTotalEmpInfo(HashMap<String, String> map);
	
	
}
