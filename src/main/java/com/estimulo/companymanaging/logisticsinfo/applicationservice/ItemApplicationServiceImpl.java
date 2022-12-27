package com.estimulo.companymanaging.logisticsinfo.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.companymanaging.logisticsinfo.dao.ItemDAO;
import com.estimulo.companymanaging.logisticsinfo.to.ItemGroupTO;
import com.estimulo.companymanaging.logisticsinfo.to.ItemInfoTO;
import com.estimulo.companymanaging.logisticsinfo.to.ItemTO;
import com.estimulo.production.material.dao.BomDAO;
import com.estimulo.production.material.to.BomTO;
import com.estimulo.system.base.dao.CodeDetailDAO;
import com.estimulo.system.base.to.CodeDetailTO;

@Component
public class ItemApplicationServiceImpl implements ItemApplicationService {
	
	@Autowired
	private ItemDAO itemDAO;
	@Autowired
	private CodeDetailDAO codeDetailDAO;
	@Autowired
	private BomDAO bomDAO;

	@Override
	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray) {
		HashMap<String, String> param = new HashMap<>();
		param.put("searchCondition", searchCondition);

		if (paramArray != null) {
			for (int i = 0; i < paramArray.length; i++) {
				switch (i + "") {
				case "0":
					if (searchCondition.equals("ITEM_CLASSIFICATION")) {
						param.put("itemClassification", paramArray[0]);
					} else if (searchCondition.equals("ITEM_GROUP_CODE")) {
						param.put("itemGroupCode", paramArray[0]);
					} else if (searchCondition.equals("STANDARD_UNIT_PRICE")) {
						param.put("minPrice", paramArray[0]);
					}
					break;
				case "1":
					param.put("maxPrice", paramArray[1]);
					break;
				}
			}
		}
		return itemDAO.selectItemList(param);
	}

	@Override
	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList) {
		HashMap<String, Object> resultMap = new HashMap<>();
	
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		CodeDetailTO detailCodeTO = new CodeDetailTO();
		BomTO bomTO = new BomTO();
		
		for (ItemTO TO : itemTOList) {

			String status = TO.getStatus();

			switch (status) {

			case "INSERT":

				itemDAO.insertItem(TO);
				insertList.add(TO.getItemCode());

				// CODE_DETAIL 테이블에 Insert
				detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
				detailCodeTO.setDetailCode(TO.getItemCode());
				detailCodeTO.setDetailCodeName(TO.getItemName());
				detailCodeTO.setDescription(TO.getDescription());

				codeDetailDAO.insertDetailCode(detailCodeTO);

				
				// 새로운 품목이 완제품 (ItemClassification : "IT-CI") , 반제품 (ItemClassification : "IT-SI") 일 경우 BOM 테이블에 Insert
				if( TO.getItemClassification().equals("IT-CI") || TO.getItemClassification().equals("IT-SI") ) {
					
					bomTO.setNo(1);
					bomTO.setParentItemCode("NULL");
					bomTO.setItemCode( TO.getItemCode() );
					bomTO.setNetAmount(1);
					
					bomDAO.insertBom(bomTO);
				}
				
				
				break;

			case "UPDATE":

				itemDAO.updateItem(TO);

				updateList.add(TO.getItemCode());

				// CODE_DETAIL 테이블에 Update
				detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
				detailCodeTO.setDetailCode(TO.getItemCode());
				detailCodeTO.setDetailCodeName(TO.getItemName());
				detailCodeTO.setDescription(TO.getDescription());

				codeDetailDAO.updateDetailCode(detailCodeTO);

				break;

			case "DELETE":

				itemDAO.deleteItem(TO);

				deleteList.add(TO.getItemCode());

				// CODE_DETAIL 테이블에 Delete
				detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
				detailCodeTO.setDetailCode(TO.getItemCode());
				detailCodeTO.setDetailCodeName(TO.getItemName());
				detailCodeTO.setDescription(TO.getDescription());

				codeDetailDAO.deleteDetailCode(detailCodeTO);

				break;
			}
		}
		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);

		return resultMap;

	}

	@Override
	public int getStandardUnitPrice(String itemCode) {
		int price = itemDAO.getStandardUnitPrice(itemCode);

		return price;
	}
	
	@Override
	public int getStandardUnitPriceBox(String itemCode) {
		int price = itemDAO.getStandardUnitPriceBox(itemCode);
		
		return price;
	}
	
	/* #################################################################### */
	/* #################################################################### */


	//품목상세 조회
	@Override
	public ArrayList<ItemInfoTO> getitemInfoList(HashMap<String, String> ableSearchConditionInfo) {
		ArrayList<ItemInfoTO> itemCodeList = itemDAO.selectitemInfoList(ableSearchConditionInfo);

		return itemCodeList;
	}

    //품목 그룹 조회
	@Override
	public ArrayList<ItemGroupTO> getitemGroupList(HashMap<String, String> ableSearchConditionInfo) {
		ArrayList<ItemGroupTO> itemGroupList = itemDAO.selectitemGroupList(ableSearchConditionInfo);

		return itemGroupList;
	}


	//품목 그룹 삭제
	@Override
	public void getdeleteitemgroup(HashMap<String, String> ableSearchConditionInfo) {
		itemDAO.deleteitemgroup(ableSearchConditionInfo);
	}

  //일괄처리
   @Override
   public void getbatchSave(ArrayList<ItemInfoTO> itemTOList) {
     for (ItemInfoTO bean : itemTOList) {
        String status = bean.getStatus();
        switch (status) {
           case "DELETE":
              System.out.println("*******************DELETE******************");
              itemDAO.deletebatchSave(bean);
              break;
           case "INSERT":
              System.out.println("*******************INSERT******************");
              itemDAO.insertbatchSave(bean);
              break;
           case "UPDATE":
              System.out.println("*******************UPDATE******************");
              itemDAO.updatebatchSave(bean);
        }
     }
   }

}
