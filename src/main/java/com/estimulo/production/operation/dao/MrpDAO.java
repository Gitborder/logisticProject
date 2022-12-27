package com.estimulo.production.operation.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.production.operation.to.MrpTO;

@Mapper
public interface MrpDAO {

	public ArrayList<MrpTO> selectMrpList(HashMap<String, String> map) ;
	
	public ArrayList<MrpTO> selectMrpList(String dateSearchCondtion, String startDate, String endDate); 

	public ArrayList<MrpTO> selectMrpListAsMrpGatheringNo(String mrpGatheringNo);
	
	public void openMrp(HashMap<String, String> map);
	
	public int selectMrpCount(String mrpRegisterDate);

	public void insertMrp(MrpTO TO);
	
	public void updateMrp(MrpTO TO);
	
	public void  changeMrpGatheringStatus(HashMap<String, String> map);
	
	public void deleteMrp(MrpTO TO);

	public ArrayList<MrpTO> insertMrpList(HashMap<String,Object> map);
	
}
