package com.estimulo.companymanaging.logisticsinfo.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.companymanaging.logisticsinfo.to.ItemGroupTO;
import com.estimulo.companymanaging.logisticsinfo.to.ItemInfoTO;
import com.estimulo.companymanaging.logisticsinfo.to.ItemTO;
import com.estimulo.companymanaging.logisticsinfo.to.WarehouseTO;

public interface LogisticsInfoServiceFacade {

	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray);
	
	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList);

	public ArrayList<WarehouseTO> getWarehouseInfoList();

	public void batchWarehouseInfo(ArrayList<WarehouseTO> warehouseTOList);

	public String findLastWarehouseCode();
	
	public int getStandardUnitPrice(String itemCode);
	
	public int getStandardUnitPriceBox(String itemCode);

	/*
	 * #############################################################################
	 * #############################################################################
	 */

	//품목상세 조회
	public ArrayList<ItemInfoTO> getitemInfoList(HashMap<String, String> ableSearchConditionInfo);
	//품목그룹 조회
	public  ArrayList<ItemGroupTO> getitemGroupList(HashMap<String, String> ableSearchConditionInfo);
	//품목그룹 삭제
	public void getdeleteitemgroup(HashMap<String, String> ableSearchConditionInfo);
	//일괄처리
	public void getbatchSave(ArrayList<ItemInfoTO> itemTOList);
}
