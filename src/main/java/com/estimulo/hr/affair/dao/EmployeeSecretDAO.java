package com.estimulo.hr.affair.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.hr.affair.to.EmployeeSecretTO;

@Mapper
public interface EmployeeSecretDAO {

	public ArrayList<EmployeeSecretTO> selectEmployeeSecretList(HashMap<String,String> map);

	public EmployeeSecretTO selectUserPassWord(HashMap<String, String> map);

	public void insertEmployeeSecret(EmployeeSecretTO TO);
	
	public int selectUserPassWordCount(HashMap<String, String> map);

}
