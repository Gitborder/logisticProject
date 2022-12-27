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
        System.out.println("mpsNoList 값확인 : "+mpsNoList);
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
      // MPS 테이블에서 해당 mpsNo 의 MRP 적용상태를 "Y" 로 변경
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
	           // dao 파트 시작
	           mrpDAO.insertMrp(bean);
	           // dao 파트 끝
	           insertList.add(bean.getMrpNo());
	
	           break;
	
	        case "UPDATE":
	           // dao 파트 시작
	           mrpDAO.updateMrp(bean);
	           // dao 파트 끝
	           updateList.add(bean.getMrpNo());
	           break;
	
	        case "DELETE":
	           // dao 파트 시작
	           mrpDAO.deleteMrp(bean);
	           // dao 파트 끝
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
     // mrp번호 배열 [mrp번호,mrp번호, ...] => "mrp번호,mrp번호, ..." 형식의 문자열로 변환
     String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
     System.out.println("mrpNoArr = "+mrpNoArr);
     System.out.println("mrpNoList = "+mrpNoList);
     mrpGatheringList = mrpGatheringDAO.getMrpGathering(mrpNoList);

      return mrpGatheringList;

   }
   public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate,ArrayList<String> mrpNoArr,HashMap<String, String> mrpNoAndItemCodeMap) {
	// 선택한날짜

			HashMap<String, Object> resultMap = null;
			int seq = 0;
			ArrayList<MrpGatheringTO> mrpGatheringList = null;
			// 소요량 취합일자로 새로운 소요량 취합번호 확인
			int i=1;
			List<MrpGatheringTO> list= mrpGatheringDAO.selectMrpGatheringCount(mrpGatheringRegisterDate); // 선택한날짜
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
			 * ( itemCode : 새로운 mrpGathering 일련번호 ) 키/값 Map => itemCode 로 mrpNo 와
			 * mrpGatheringNo 를 매칭
			 */
			HashMap<String, String> itemCodeAndMrpGatheringNoMap = new HashMap<>();

			// 새로운 mrpGathering 일련번호 양식 생성 : 등록일자 '2020-04-28' => 일련번호 'MG20200428-'
			StringBuffer newMrpGatheringNo = new StringBuffer();
			newMrpGatheringNo.append("MG");
			newMrpGatheringNo.append(mrpGatheringRegisterDate.replace("-", ""));
			newMrpGatheringNo.append("-");

			seq = mrpGatheringDAO.getMGSeqNo(); //

			mrpGatheringList = getMrpGathering(mrpNoArr);
			// 새로운 mrpGathering 빈에 일련번호 입력 / status 를 "INSERT" 로 변경
			for (MrpGatheringTO bean : mrpGatheringList) { // newMrpGatheringList : 소요량 취합결과 그리드에 뿌려진 데이터값
				System.out.println("################################ : " + bean.getMrpGatheringNo());
				bean.setMrpGatheringNo(newMrpGatheringNo.toString() + String.format("%03d", i++));
				bean.setStatus("INSERT"); // bean 즉, MrpGatheringTO의 클래스주소에 소요량취합번호 + INSERT set
				bean.setMrpGatheringSeq(seq);
				// mrpGathering 빈의 itemCode 와 mrpGatheringNo 를 키값:밸류값으로 map 에 저장
				itemCodeAndMrpGatheringNoMap.put(bean.getItemCode(), bean.getMrpGatheringNo());

			}

			// 새로운 mrpGathering 빈을 batchListProcess 로 테이블에 저장, 결과 Map 반환
			resultMap = batchMrpGatheringListProcess(mrpGatheringList);// 소요량 취합결과 그리드에 뿌려진 데이터값 //소요량취합번호, INSERT 추가됨

			// 생성된 mrp 일련번호를 저장할 TreeSet
			TreeSet<String> mrpGatheringNoSet = new TreeSet<>();

			// "INSERT_LIST" 목록에 "itemCode - mrpGatheringNo" 키/값 Map 이 있음
			@SuppressWarnings("unchecked")
			HashMap<String, String> mrpGatheringNoList = (HashMap<String, String>) resultMap.get("INSERT_MAP");// key(ItemCode):value(소요량취합번호)

			for (String mrpGatheringNo : mrpGatheringNoList.values()) {
				mrpGatheringNoSet.add(mrpGatheringNo); // mrpGatheringNoList 의 mrpGathering 일련번호들을 TreeSet 에 저장

			}

			resultMap.put("firstMrpGatheringNo", mrpGatheringNoSet.pollFirst()); // 최초 mrpGathering 일련번호를 결과 Map 에 저장
			resultMap.put("lastMrpGatheringNo", mrpGatheringNoSet.pollLast()); // 마지막 mrpGathering 일련번호를 결과 Map 에 저장

			// MRP 테이블에서 해당 mrpNo 의 mrpGatheringNo 저장, 소요량취합 적용상태를 "Y" 로 변경
			// itemCode 로 mrpNo 와 mrpGatheringNo 를 매칭시킨다
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
			// MRP 적용상태를 "Y" 로 변경한 MRP 번호들을 결과 Map 에 저장
			resultMap.put("changeMrpGatheringStatus", mrpNoList);

			StringBuffer sb = new StringBuffer();

			// 사진 캡쳐할려고 코드 분리 시켜놓음, mrpGatheringNoList.values()로 돌리는 첫번째 for문이랑 합쳐야함
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
		HashMap<String, String> insertListMap = new HashMap<>(); // "itemCode : mrpGatheringNo" 의 맵
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();
		
		for (MrpGatheringTO bean : mrpGatheringTOList) {//소요량 취합결과 그리드에 뿌려진 데이터값
			String status = bean.getStatus();
			switch (status) {
				case "INSERT":
				   mrpGatheringDAO.insertMrpGathering(bean);
				   insertList.add(bean.getMrpGatheringNo());
				   //소요량취합번호 추가
				   insertListMap.put(bean.getItemCode(), bean.getMrpGatheringNo());
				   //map에 key(ItemCode) : value(getMrpGatheringNo)
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
			resultMap.put("INSERT", insertList); //소요량취합번호
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);
		}
		return resultMap;
   }
}