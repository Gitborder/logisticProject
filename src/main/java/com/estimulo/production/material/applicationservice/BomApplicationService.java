package com.estimulo.production.material.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.production.material.to.BomDeployTO;
import com.estimulo.production.material.to.BomInfoTO;
import com.estimulo.production.material.to.BomTO;

public interface BomApplicationService {

	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode, String itemClassificationCondition);
	
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode);
	
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable();
	
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList);

}