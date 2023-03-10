package com.estimulo.production.operation.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.business.sales.dao.ContractDetailDAO;
import com.estimulo.business.sales.dao.SalesPlanDAO;
import com.estimulo.production.operation.dao.MpsDAO;
import com.estimulo.production.operation.to.ContractDetailInMpsAvailableTO;
import com.estimulo.production.operation.to.MpsTO;
import com.estimulo.production.operation.to.SalesPlanInMpsAvailableTO;

@Component
public class MpsApplicationServiceImpl implements MpsApplicationService {
	
	@Autowired
	private MpsDAO mpsDAO;
	@Autowired
	private ContractDetailDAO contractDetailDAO;
	@Autowired
	private SalesPlanDAO salesPlanDAO;

	public ArrayList<MpsTO> getMpsList(String startDate, String endDate, String includeMrpApply) {
		ArrayList<MpsTO> mpsTOList = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("includeMrpApply", includeMrpApply);
		mpsTOList = mpsDAO.selectMpsList(map);
		
		return mpsTOList;
	}

	public ArrayList<ContractDetailInMpsAvailableTO> getContractDetailListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("searchCondition", searchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = null;
		contractDetailInMpsAvailableList = contractDetailDAO.selectContractDetailListInMpsAvailable(map);
		
		return contractDetailInMpsAvailableList;
	}

	public ArrayList<SalesPlanInMpsAvailableTO> getSalesPlanListInMpsAvailable(String searchCondition, String startDate,
			String endDate) {
		ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = null;
		salesPlanInMpsAvailableList = salesPlanDAO.selectSalesPlanListInMpsAvailable(searchCondition, startDate,
				endDate);

		return salesPlanInMpsAvailableList;
	}

	public String getNewMpsNo(String mpsPlanDate) {
		StringBuffer newEstimateNo = null;
		List<MpsTO> mpsTOlist = mpsDAO.selectMpsCount(mpsPlanDate);
		TreeSet<Integer> intSet = new TreeSet<>();
		for (MpsTO bean : mpsTOlist) {
			String mpsNo = bean.getMpsNo();
			// MPS ?????????????????? ????????? 2????????? ????????????
			int no = Integer.parseInt(mpsNo.substring(mpsNo.length() - 2, mpsNo.length()));
			intSet.add(no);	
		}
		int i=1;
		if (!intSet.isEmpty()) {
			i=intSet.pollLast() + 1;
		}
		newEstimateNo = new StringBuffer();
		newEstimateNo.append("PS");
		newEstimateNo.append(mpsPlanDate.replace("-", ""));
		newEstimateNo.append(String.format("%02d", i)); //PS2020042401

		return newEstimateNo.toString();
	}

	public HashMap<String, Object> convertContractDetailToMps(
			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList) {
		HashMap<String, Object> resultMap = null;
		ArrayList<MpsTO> mpsTOList = new ArrayList<>();
		MpsTO newMpsBean = null;

		// MPS ??? ????????? ???????????? Bean ??? ????????? ????????? MPS Bean ??? ??????, status : "INSERT"
		for (ContractDetailInMpsAvailableTO bean : contractDetailInMpsAvailableList) {

			newMpsBean = new MpsTO();
			newMpsBean.setStatus("INSERT");

			newMpsBean.setMpsPlanClassification(bean.getPlanClassification());
			newMpsBean.setContractDetailNo(bean.getContractDetailNo());
			newMpsBean.setItemCode(bean.getItemCode());
			newMpsBean.setItemName(bean.getItemName());
			newMpsBean.setUnitOfMps(bean.getUnitOfContract());
			newMpsBean.setMpsPlanDate(bean.getMpsPlanDate());
			newMpsBean.setMpsPlanAmount(bean.getProductionRequirement());
			newMpsBean.setDueDateOfMps(bean.getDueDateOfContract());
			newMpsBean.setScheduledEndDate(bean.getScheduledEndDate());
			newMpsBean.setDescription(bean.getDescription());

			mpsTOList.add(newMpsBean);
		}
		resultMap = batchMpsListProcess(mpsTOList); //batchMpsListProcess ????????? ??????
		return resultMap;
	}

	public HashMap<String, Object> convertSalesPlanToMps(
			ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList) {
		HashMap<String, Object> resultMap = null;
		ArrayList<MpsTO> mpsTOList = new ArrayList<>();
		MpsTO newMpsBean = null;

		// MPS ??? ????????? ???????????? TO ??? ????????? ????????? MPS TO ??? ??????, status : "INSERT"
		for (SalesPlanInMpsAvailableTO bean : salesPlanInMpsAvailableList) {
			newMpsBean = new MpsTO();
			newMpsBean.setStatus("INSERT");

			newMpsBean.setMpsPlanClassification(bean.getPlanClassification());
			newMpsBean.setSalesPlanNo(bean.getSalesPlanNo());
			newMpsBean.setItemCode(bean.getItemCode());
			newMpsBean.setItemName(bean.getItemName());
			newMpsBean.setUnitOfMps(bean.getUnitOfSales());
			newMpsBean.setMpsPlanDate(bean.getMpsPlanDate());
			newMpsBean.setMpsPlanAmount(bean.getSalesAmount());
			newMpsBean.setDueDateOfMps(bean.getDueDateOfSales());
			newMpsBean.setScheduledEndDate(bean.getScheduledEndDate());
			newMpsBean.setDescription(bean.getDescription());

			mpsTOList.add(newMpsBean);
		}
		resultMap = batchMpsListProcess(mpsTOList);
		return resultMap;
	}

	public HashMap<String, Object> batchMpsListProcess(ArrayList<MpsTO> mpsTOList) {
		HashMap<String, Object> resultMap = null;
		resultMap = new HashMap<>();

		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		for (MpsTO bean : mpsTOList) {
			String status = bean.getStatus();
			switch (status) {
				case "INSERT":
					// ????????? MPS?????? ??????
					String newMpsNo = getNewMpsNo(bean.getMpsPlanDate());  //mpsPlanDate":"2022-01-17
						System.out.println("newMpsNo = "+newMpsNo); //PS2022011702
						
					// MPS TO ??? ????????? MPS?????? ??????
					bean.setMpsNo(newMpsNo);
	
					// MPS TO Insert
					mpsDAO.insertMps(bean);
	
					// ????????? ????????? MPS ????????? ArrayList ??? ??????
					insertList.add(newMpsNo);
	
					// MPS TO ??? ????????????????????? ????????????, ???????????? ??????????????? ?????? ????????? MPS ??????????????? 'Y' ??? ??????
					if (bean.getContractDetailNo() != null) {
						changeMpsStatusInContractDetail(bean.getContractDetailNo(), "Y");
						// MPS TO ??? ????????????????????? ????????????, ???????????? ??????????????? ?????? ????????? MPS ??????????????? 'Y' ??? ??????
					} else if (bean.getSalesPlanNo() != null) {
						changeMpsStatusInSalesPlan(bean.getSalesPlanNo(), "Y");
					}
					break;
	
				case "UPDATE":
					mpsDAO.updateMps(bean);
					updateList.add(bean.getMpsNo());
					break;
	
				case "DELETE":
					mpsDAO.deleteMps(bean);
					deleteList.add(bean.getMpsNo());
					break;
			}
		}
		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);
		return resultMap;
	}

	public void changeMpsStatusInContractDetail(String contractDetailNo, String mpsStatus) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("contractDetailNo", contractDetailNo);
		map.put("mpsStatus", mpsStatus);
		contractDetailDAO.changeMpsStatusOfContractDetail(map);
	}

	public void changeMpsStatusInSalesPlan(String salesPlanNo, String mpsStatus) {
		salesPlanDAO.changeMpsStatusOfSalesPlan(salesPlanNo, mpsStatus);
	}

}
