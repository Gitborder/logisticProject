package com.estimulo.companymanaging.logisticsinfo.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.companymanaging.logisticsinfo.applicationservice.ItemApplicationService;
import com.estimulo.companymanaging.logisticsinfo.applicationservice.WarehouseApplicationService;
import com.estimulo.companymanaging.logisticsinfo.to.ItemGroupTO;
import com.estimulo.companymanaging.logisticsinfo.to.ItemInfoTO;
import com.estimulo.companymanaging.logisticsinfo.to.ItemTO;
import com.estimulo.companymanaging.logisticsinfo.to.WarehouseTO;

@Service
public class LogisticsInfoServiceFacadeImpl implements LogisticsInfoServiceFacade {
	
	@Autowired
	private ItemApplicationService itemAS;
	@Autowired
	private WarehouseApplicationService warehouseAS;
	
	@Override
	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray) {
		ArrayList<ItemInfoTO>itemInfoList = itemAS.getItemInfoList(searchCondition, paramArray);
		
		return itemInfoList;
	}

	@Override
	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList) {
		HashMap<String, Object> resultMap = itemAS.batchItemListProcess(itemTOList);
		return resultMap;
	}

	@Override
	public ArrayList<WarehouseTO> getWarehouseInfoList() {
		ArrayList<WarehouseTO> warehouseInfoList = warehouseAS.getWarehouseInfoList();
		
		return warehouseInfoList;
	}

	@Override
	public void batchWarehouseInfo(ArrayList<WarehouseTO> warehouseTOList) {
		warehouseAS.batchWarehouseProcess(warehouseTOList);
	}

	@Override
	public String findLastWarehouseCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStandardUnitPrice(String itemCode) {
		int price = itemAS.getStandardUnitPrice(itemCode);
		
		return price;
	}
	
	@Override
	public int getStandardUnitPriceBox(String itemCode) {
		int price = itemAS.getStandardUnitPriceBox(itemCode);
		
		return price;
	}
	
	/*
	 * #############################################################################
	 * #############################################################################
	 */


	//품목상세 조회
	@Override
	public ArrayList<ItemInfoTO> getitemInfoList(HashMap<String, String> ableSearchConditionInfo) {
		ArrayList<ItemInfoTO> itemCodeList = itemAS.getitemInfoList(ableSearchConditionInfo);

		return itemCodeList;
	}

	//품목그룹 조회
	@Override
	public ArrayList<ItemGroupTO> getitemGroupList(HashMap<String, String> ableSearchConditionInfo) {
		ArrayList<ItemGroupTO> itemGroupList = itemAS.getitemGroupList(ableSearchConditionInfo);

		return itemGroupList;
	}

	//품목그룹삭제
	@Override
	public void getdeleteitemgroup(HashMap<String, String> ableSearchConditionInfo) {
		itemAS.getdeleteitemgroup(ableSearchConditionInfo);
	}

	//일괄처리
	@Override
	public void getbatchSave(ArrayList<ItemInfoTO> itemTOList) {
		itemAS.getbatchSave(itemTOList);
	}
}
