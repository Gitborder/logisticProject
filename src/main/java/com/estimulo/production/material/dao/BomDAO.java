package com.estimulo.production.material.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.production.material.to.BomDeployTO;
import com.estimulo.production.material.to.BomInfoTO;
import com.estimulo.production.material.to.BomTO;

@Mapper
public interface BomDAO {
	
	public ArrayList<BomDeployTO> selectBomDeployList(String deployCondition, String itemCode,String itemClassificationCondition);
	
	public ArrayList<BomInfoTO> selectBomInfoList(String parentItemCode);
	
	public ArrayList<BomInfoTO> selectAllItemWithBomRegisterAvailable();
	
	public void insertBom(BomTO TO);
	
	public void updateBom(BomTO TO);
	
	public void deleteBom(BomTO TO);
	
}
