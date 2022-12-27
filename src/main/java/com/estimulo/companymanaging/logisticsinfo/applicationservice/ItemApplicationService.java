package com.estimulo.companymanaging.logisticsinfo.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.companymanaging.logisticsinfo.to.ItemGroupTO;
import com.estimulo.companymanaging.logisticsinfo.to.ItemInfoTO;
import com.estimulo.companymanaging.logisticsinfo.to.ItemTO;

public interface ItemApplicationService {

	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray);
	
	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList);
		
	public int getStandardUnitPrice(String itemCode);
	
	public int getStandardUnitPriceBox(String itemCode);
	
	/* #################################################################### */
	/* #################################################################### */

	//품목상세 조회
	public ArrayList<ItemInfoTO> getitemInfoList(HashMap<String, String> ableSearchConditionInfo);
	//품목그룹 조회
	public ArrayList<ItemGroupTO> getitemGroupList(HashMap<String, String> ableSearchConditionInfo);
	//품목그룹 삭제
	public void getdeleteitemgroup(HashMap<String, String> ableSearchConditionInfo);
	//일괄 처리
	public void getbatchSave(ArrayList<ItemInfoTO> itemTOList) ;
}
