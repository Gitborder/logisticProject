package com.estimulo.production.material.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.production.material.dao.BomDAO;
import com.estimulo.production.material.to.BomDeployTO;
import com.estimulo.production.material.to.BomInfoTO;
import com.estimulo.production.material.to.BomTO;

@Component
public class BomApplicationServiceImpl implements BomApplicationService {
	
	// DAO 참조변수 선언
	@Autowired
	private BomDAO bomDAO;

	@Override
	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode, String itemClassificationCondition) {
		ArrayList<BomDeployTO> bomDeployList = bomDAO.selectBomDeployList(deployCondition, itemCode, itemClassificationCondition);

		return bomDeployList;
	}
	
	@Override
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {
		ArrayList<BomInfoTO> bomInfoList = bomDAO.selectBomInfoList(parentItemCode);

		return bomInfoList;
	}

	@Override
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {
		ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = bomDAO.selectAllItemWithBomRegisterAvailable();

		return allItemWithBomRegisterAvailable;
	}

	@Override
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {
		HashMap<String, Object> resultMap = new HashMap<>();

		int insertCount = 0;
		int updateCount = 0;
		int deleteCount = 0;
		for (BomTO TO : batchBomList) {
			String status = TO.getStatus();
			switch (status) {
				case "INSERT":
					bomDAO.insertBom(TO);
					insertCount++;
					break;
				case "UPDATE":
					bomDAO.updateBom(TO);
					updateCount++;
					break;
				case "DELETE":
					bomDAO.deleteBom(TO);
					deleteCount++;
					break;
			}
		}
		resultMap.put("INSERT", insertCount);
		resultMap.put("UPDATE", updateCount);
		resultMap.put("DELETE", deleteCount);
		return resultMap;
	}
}
