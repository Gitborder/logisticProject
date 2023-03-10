package com.estimulo.business.sales.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.estimulo.business.sales.dao.EstimateDAO;
import com.estimulo.business.sales.dao.EstimateDetailDAO;
import com.estimulo.business.sales.to.EstimateDetailTO;
import com.estimulo.business.sales.to.EstimateTO;

@Component
public class EstimateApplicationServiceImpl implements EstimateApplicationService {
	
	@Autowired
	private EstimateDAO estimateDAO;
	@Autowired
	private EstimateDetailDAO estimateDetailDAO;

	@Override
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate) {
		ArrayList<EstimateTO> estimateTOList = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dateSearchCondition", dateSearchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		estimateTOList = estimateDAO.selectEstimateList(map);
		
		for (EstimateTO bean : estimateTOList) {
			bean.setEstimateDetailTOList(estimateDetailDAO.selectEstimateDetailList(bean.getEstimateNo()));
		}
		
		return estimateTOList;
	}

	@Override
	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo) {

		ArrayList<EstimateDetailTO> estimateDetailTOList = null;

		estimateDetailTOList = estimateDetailDAO.selectEstimateDetailList(estimateNo);

		return estimateDetailTOList;
	}

	@Override
	public String getNewEstimateDetailNo(String estimateNo) {
		StringBuffer newEstimateDetailNo = null;

		int i = estimateDetailDAO.selectEstimateDetailSeq(estimateNo); //1

		newEstimateDetailNo = new StringBuffer();
		newEstimateDetailNo.append("ES");
		newEstimateDetailNo.append(estimateNo.replace("-", ""));
		newEstimateDetailNo.append("-"); 
		newEstimateDetailNo.append(String.format("%02d", i));		   
		System.out.println("newEstimateNo = " + newEstimateDetailNo);

		return newEstimateDetailNo.toString();
	}
	
	@Override
	public String getNewEstimateNo(String estimateDate) {
		StringBuffer newEstimateNo = null;

		int i = estimateDAO.selectEstimateCount(estimateDate); //137  ????????? ????????? ????????????.

		newEstimateNo = new StringBuffer();
		newEstimateNo.append("ES");
		newEstimateNo.append(estimateDate.replace("-", "")); //ES20200422 - ??? ???????????? ?????????.
		newEstimateNo.append(String.format("%02d", i));		   //ES2020042201<-01??? ?????????  format("%02d", i) ????????? ???????????? 0??? ????????? ??????????????? 2??? (MAX 99) ?????? ?????? ????????? ???????????? 0 ??? ?????? i = 1 ????????? 01??? ????????? ?????? 
		System.out.println("newEstimateNo = " + newEstimateNo);// ES + estimateDate(-??????) + ??????????????? ???????????????

		return newEstimateNo.toString();
	}

	@Override
	public ModelMap addNewEstimate(String estimateDate, EstimateTO newEstimateBean) {
		ModelMap resultMap = null;
		
		String newEstimateNo = getNewEstimateNo(estimateDate);		//ES20220113138
		System.out.println("newEstimateNo = " + newEstimateNo);
		// ????????? ?????????????????? ??????

		newEstimateBean.setEstimateNo(newEstimateNo);
		// ???????????? ????????? ?????? Bean ??? ????????? ?????????????????? set

		estimateDAO.insertEstimate(newEstimateBean);
		// ?????? Bean ??? Insert
		ArrayList<EstimateDetailTO> estimateDetailTOList = newEstimateBean.getEstimateDetailTOList(); //bean??????
		// ???????????? List
		//???????????? bean
		for (EstimateDetailTO bean : estimateDetailTOList) {
			String newEstimateDetailNo = getNewEstimateDetailNo(newEstimateNo);
			// ?????? ????????? ?????? ???????????? set
			bean.setEstimateNo(newEstimateNo);//ES20220113138
			// ????????? ???????????? ???????????? set
			bean.setEstimateDetailNo(newEstimateDetailNo);//ES20220113138
		}

		// ????????????List ??? batchListProcess ??? Insert, ?????? ??? ??????
		resultMap = batchEstimateDetailListProcess(estimateDetailTOList,newEstimateNo);

		// ?????? ?????? "estimateNo" ???????????? ?????? ????????? ?????????????????? ??????
		resultMap.put("newEstimateNo", newEstimateNo);

		return resultMap;
		// ?????? ????????? ??????????????????,??????????????????????????? ??????
	}

	@Override
	public ModelMap batchEstimateDetailListProcess(ArrayList<EstimateDetailTO> estimateDetailTOList,String estimateNo) {
		ModelMap resultMap = new ModelMap();

		// ??????/??????/????????? ??????????????????????????? ?????? ArrayList
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		// ??? ?????? ????????? : INSERT ??? ?????? ?????? => DELETE ??? ?????? ?????? ????????? ????????? ????????? ???????????? ????????? ????????? ??? ??????,
		// UPDATE ??? ????????????
		estimateDetailDAO.initDetailSeq("EST_DETAIL_SEQ");
		String count = estimateDetailDAO.selectEstimateDetailCount(estimateNo);
		int cnt = 1;
		boolean isDelete=false;
		for (EstimateDetailTO bean : estimateDetailTOList) {
			String status = bean.getStatus();
		if(count!=null) cnt=Integer.parseInt(count);
			switch (status) {
				case "INSERT":
					//?????????????????? ??????????????? ????????? ????????? ??????????????? ??????
					// ???????????? ???????????? ?????? : "ES20180101-01"
					if(cnt==1) {
						estimateDetailDAO.insertEstimateDetail(bean);
					}else {
						String newCnt= estimateDetailDAO.selectEstimateDetailCount(estimateNo); //????????? ????????? ??????????????????????????? ??????
						StringBuffer newEstimateDetailNo = new StringBuffer();
						newEstimateDetailNo.append("ES");
						newEstimateDetailNo.append(estimateNo.replace("-", ""));
						newEstimateDetailNo.append("-"); 
						newEstimateDetailNo.append(String.format("%02d", newCnt));	
						bean.setEstimateDetailNo(newEstimateDetailNo.toString());
						estimateDetailDAO.insertEstimateDetail(bean);
					}
					insertList.add(bean.getEstimateDetailNo());		//ES2020010301-01 <= ?????? -01 ??? ????????? ??????????????? -01 ??????.
					break;
					//insertList = arrayList
					
				/*
				 * ???????????? ??????/????????? ?????? ?????????????????? ?????? insert??? ??? ???????????? ????????? ?????? ???????????????????????? ????????? ?????? ????????? ??? ??? ????????????
				 * ????????? ????????? ??????????????? ?????????????????? loop????????? ????????????
				 */
				case "UPDATE":
					estimateDetailDAO.updateEstimateDetail(bean);
					updateList.add(bean.getEstimateDetailNo());
					break;
					//????????? ?????? ???????????? ??????
				case "DELETE":
					estimateDetailDAO.deleteEstimateDetail(bean);
					deleteList.add(bean.getEstimateDetailNo());
					isDelete=true;
					//????????? ?????? ???????????? ??????
					break;
			}

		}
		if(isDelete==true) {
			for (EstimateDetailTO bean : estimateDetailTOList) {
				int i = estimateDetailDAO.selectEstimateDetailSeq(estimateNo);
				String newSeq = String.format("%02d", i);
				estimateDetailDAO.reArrangeEstimateDetail(bean,newSeq);
			}
		}
		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);
			
		return resultMap;
	}
}
