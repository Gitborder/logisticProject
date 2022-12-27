package com.estimulo.production.operation.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.production.operation.to.MpsTO;

@Mapper
public interface MpsDAO {

	public ArrayList<MpsTO> selectMpsList(HashMap<String, String> map);
	
	public List<MpsTO> selectMpsCount(String mpsPlanDate);
	
	public void insertMps(MpsTO TO);
	
	public void updateMps(MpsTO TO);
	
	public void changeMrpApplyStatus(HashMap<String, String> map);
	
	public void deleteMps(MpsTO TO);
	
}
