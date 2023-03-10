package com.estimulo.production.operation.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.production.operation.dao.MpsDAO;
import com.estimulo.production.operation.dao.MrpDAO;
import com.estimulo.production.operation.dao.MrpGatheringDAO;
import com.estimulo.production.operation.to.MrpGatheringTO;
import com.estimulo.production.operation.to.MrpTO;

@Component
public class MrpApplicationServiceImpl implements MrpApplicationService {
	
	@Autowired
	private MpsDAO mpsDAO;
	@Autowired
	private MrpDAO mrpDAO;
	@Autowired
	private MrpGatheringDAO mrpGatheringDAO;

   public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition) {
      ArrayList<MrpTO> mrpList = null;
      HashMap<String,String> map = new HashMap<>();
      map.put("mrpGatheringStatusCondition",mrpGatheringStatusCondition);
      mrpList = mrpDAO.selectMrpList(map);

      return mrpList;
   }
   public ArrayList<MrpTO> selectMrpListAsDate(String dateSearchCondtion, String startDate, String endDate) {
      ArrayList<MrpTO> mrpList = null;
      HashMap<String, String> map = new HashMap<String, String>();
      map.put("dateSearchCondtion", dateSearchCondtion);
      map.put("startDate", startDate);
      map.put("endDate", endDate);
      mrpList = mrpDAO.selectMrpList(dateSearchCondtion, startDate, endDate);

      return mrpList;
   }

   public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo) {
      ArrayList<MrpTO> mrpList = null;
      mrpList = mrpDAO.selectMrpListAsMrpGatheringNo(mrpGatheringNo);

      return mrpList;

   }

   public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate,
         String endDate) {
      ArrayList<MrpGatheringTO> mrpGatheringList = null;
      mrpGatheringList = mrpGatheringDAO.selectMrpGatheringList(dateSearchCondtion, startDate, endDate);
      for(MrpGatheringTO bean : mrpGatheringList)    {
    	  bean.setMrpTOList(  mrpDAO.selectMrpListAsMrpGatheringNo( bean.getMrpGatheringNo()) );
      }   
      return mrpGatheringList;
   }


   public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr) {
        String mpsNoList = mpsNoArr.toString().replace("[", "").replace("]", "");
        System.out.println("mpsNoList ????????? : "+mpsNoList);
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mpsNoList", mpsNoList);
        mrpDAO.openMrp(map);

		resultMap.put("gridRowJson", map.get("RESULT"));
		resultMap.put("errorCode",map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));
		return resultMap;
   }

   public HashMap<String, Object> registerMrp(String mrpRegisterDate, ArrayList<String> mpsList) {
      HashMap<String, Object> resultMap = new HashMap<String, Object>();
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("mrpRegisterDate", mrpRegisterDate);
      ArrayList<MrpTO> list = mrpDAO.insertMrpList(map);

      resultMap.put("gridRowJson", list);
	  resultMap.put("errorCode", map.get("ERROR_CODE"));
	  resultMap.put("errorMsg", map.get("ERROR_MSG"));
      // MPS ??????????????? ?????? mpsNo ??? MRP ??????????????? "Y" ??? ??????
      for (String mpsNo : mpsList) {
    	 HashMap<String, String> mpsMap = new HashMap<>();
    	 mpsMap.put("mpsNo", mpsNo);
    	 mpsMap.put("mrpStatus","Y");		
		 System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ mpsNO : "+mpsNo);
		 mpsDAO.changeMrpApplyStatus(mpsMap);
      }
      return resultMap;
   }

   public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList) {
     HashMap<String, Object> resultMap = new HashMap<>();
     ArrayList<String> insertList = new ArrayList<>();
     ArrayList<String> updateList = new ArrayList<>();
     ArrayList<String> deleteList = new ArrayList<>();

     for (MrpTO bean : mrpTOList) {
        String status = bean.getStatus();
        switch (status) {
	        case "INSERT":
	           // dao ?????? ??????
	           mrpDAO.insertMrp(bean);
	           // dao ?????? ???
	           insertList.add(bean.getMrpNo());
	
	           break;
	
	        case "UPDATE":
	           // dao ?????? ??????
	           mrpDAO.updateMrp(bean);
	           // dao ?????? ???
	           updateList.add(bean.getMrpNo());
	           break;
	
	        case "DELETE":
	           // dao ?????? ??????
	           mrpDAO.deleteMrp(bean);
	           // dao ?????? ???
	           deleteList.add(bean.getMrpNo());
	           break;
        }
     }
     resultMap.put("INSERT", insertList);
     resultMap.put("UPDATE", updateList);
     resultMap.put("DELETE", deleteList);

     return resultMap;
   }

   public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr) {
      ArrayList<MrpGatheringTO> mrpGatheringList = null;
     // mrp?????? ?????? [mrp??????,mrp??????, ...] => "mrp??????,mrp??????, ..." ????????? ???????????? ??????
     String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
     System.out.println("mrpNoArr = "+mrpNoArr);
     System.out.println("mrpNoList = "+mrpNoList);
     mrpGatheringList = mrpGatheringDAO.getMrpGathering(mrpNoList);

      return mrpGatheringList;

   }
   public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate,ArrayList<String> mrpNoArr,HashMap<String, String> mrpNoAndItemCodeMap) {
	// ???????????????

			HashMap<String, Object> resultMap = null;
			int seq = 0;
			ArrayList<MrpGatheringTO> mrpGatheringList = null;
			// ????????? ??????????????? ????????? ????????? ???????????? ??????
			int i=1;
			List<MrpGatheringTO> list= mrpGatheringDAO.selectMrpGatheringCount(mrpGatheringRegisterDate); // ???????????????
			TreeSet<Integer> intSet = new TreeSet<>();
			for(MrpGatheringTO bean : list) {
				String mrpGatheringNo = bean.getMrpGatheringNo();
				int no = Integer.parseInt(mrpGatheringNo.substring(mrpGatheringNo.length() - 2, mrpGatheringNo.length()));
				intSet.add(no);
			}
			if (!intSet.isEmpty()) {
				i=intSet.pollLast() + 1;
			}
			/*
			 * ( itemCode : ????????? mrpGathering ???????????? ) ???/??? Map => itemCode ??? mrpNo ???
			 * mrpGatheringNo ??? ??????
			 */
			HashMap<String, String> itemCodeAndMrpGatheringNoMap = new HashMap<>();

			// ????????? mrpGathering ???????????? ?????? ?????? : ???????????? '2020-04-28' => ???????????? 'MG20200428-'
			StringBuffer newMrpGatheringNo = new StringBuffer();
			newMrpGatheringNo.append("MG");
			newMrpGatheringNo.append(mrpGatheringRegisterDate.replace("-", ""));
			newMrpGatheringNo.append("-");

			seq = mrpGatheringDAO.getMGSeqNo(); //

			mrpGatheringList = getMrpGathering(mrpNoArr);
			// ????????? mrpGathering ?????? ???????????? ?????? / status ??? "INSERT" ??? ??????
			for (MrpGatheringTO bean : mrpGatheringList) { // newMrpGatheringList : ????????? ???????????? ???????????? ????????? ????????????
				System.out.println("################################ : " + bean.getMrpGatheringNo());
				bean.setMrpGatheringNo(newMrpGatheringNo.toString() + String.format("%03d", i++));
				bean.setStatus("INSERT"); // bean ???, MrpGatheringTO??? ?????????????????? ????????????????????? + INSERT set
				bean.setMrpGatheringSeq(seq);
				// mrpGathering ?????? itemCode ??? mrpGatheringNo ??? ??????:??????????????? map ??? ??????
				itemCodeAndMrpGatheringNoMap.put(bean.getItemCode(), bean.getMrpGatheringNo());

			}

			// ????????? mrpGathering ?????? batchListProcess ??? ???????????? ??????, ?????? Map ??????
			resultMap = batchMrpGatheringListProcess(mrpGatheringList);// ????????? ???????????? ???????????? ????????? ???????????? //?????????????????????, INSERT ?????????

			// ????????? mrp ??????????????? ????????? TreeSet
			TreeSet<String> mrpGatheringNoSet = new TreeSet<>();

			// "INSERT_LIST" ????????? "itemCode - mrpGatheringNo" ???/??? Map ??? ??????
			@SuppressWarnings("unchecked")
			HashMap<String, String> mrpGatheringNoList = (HashMap<String, String>) resultMap.get("INSERT_MAP");// key(ItemCode):value(?????????????????????)

			for (String mrpGatheringNo : mrpGatheringNoList.values()) {
				mrpGatheringNoSet.add(mrpGatheringNo); // mrpGatheringNoList ??? mrpGathering ?????????????????? TreeSet ??? ??????

			}

			resultMap.put("firstMrpGatheringNo", mrpGatheringNoSet.pollFirst()); // ?????? mrpGathering ??????????????? ?????? Map ??? ??????
			resultMap.put("lastMrpGatheringNo", mrpGatheringNoSet.pollLast()); // ????????? mrpGathering ??????????????? ?????? Map ??? ??????

			// MRP ??????????????? ?????? mrpNo ??? mrpGatheringNo ??????, ??????????????? ??????????????? "Y" ??? ??????
			// itemCode ??? mrpNo ??? mrpGatheringNo ??? ???????????????
			for (String mrpNo : mrpNoAndItemCodeMap.keySet()) {
				String itemCode = mrpNoAndItemCodeMap.get(mrpNo);
				String mrpGatheringNo = itemCodeAndMrpGatheringNoMap.get(itemCode);
				HashMap<String, String> map = new HashMap<>();
				map.put("mrpNo", mrpNo);
				map.put("mrpGatheringNo", mrpGatheringNo);
				map.put("mrpGatheringStatus", "Y");
				mrpDAO.changeMrpGatheringStatus(map);
			}

			String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
			// MRP ??????????????? "Y" ??? ????????? MRP ???????????? ?????? Map ??? ??????
			resultMap.put("changeMrpGatheringStatus", mrpNoList);

			StringBuffer sb = new StringBuffer();

			// ?????? ??????????????? ?????? ?????? ????????????, mrpGatheringNoList.values()??? ????????? ????????? for????????? ????????????
			for (String mrpGatheringNo : mrpGatheringNoList.values()) {
				sb.append(mrpGatheringNo);
				sb.append(",");
			}

			sb.delete(sb.toString().length() - 1, sb.toString().length());

			System.out.println("sb");
			System.out.println(sb.toString());
			
			HashMap<String, String> map = new HashMap<>();
			map.put("mrpGatheringNo",sb.toString());
			mrpGatheringDAO.updateMrpGatheringContract(map);
			return resultMap;
  }

   public HashMap<String, Object> batchMrpGatheringListProcess(ArrayList<MrpGatheringTO> mrpGatheringTOList) {
	   	HashMap<String, Object> resultMap = new HashMap<>();
		HashMap<String, String> insertListMap = new HashMap<>(); // "itemCode : mrpGatheringNo" ??? ???
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();
		
		for (MrpGatheringTO bean : mrpGatheringTOList) {//????????? ???????????? ???????????? ????????? ????????????
			String status = bean.getStatus();
			switch (status) {
				case "INSERT":
				   mrpGatheringDAO.insertMrpGathering(bean);
				   insertList.add(bean.getMrpGatheringNo());
				   //????????????????????? ??????
				   insertListMap.put(bean.getItemCode(), bean.getMrpGatheringNo());
				   //map??? key(ItemCode) : value(getMrpGatheringNo)
				   break;
				
				case "UPDATE":
				   mrpGatheringDAO.updateMrpGathering(bean);
				   updateList.add(bean.getMrpGatheringNo());
				   break;
				
				case "DELETE":
				       mrpGatheringDAO.deleteMrpGathering(bean);
				       deleteList.add(bean.getMrpGatheringNo());
				       break;
				    }
			resultMap.put("INSERT_MAP", insertListMap); //key(ItemCode) : value(getMrpGatheringNo)
			resultMap.put("INSERT", insertList); //?????????????????????
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);
		}
		return resultMap;
   }
}