package com.estimulo.business.sales.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.estimulo.business.sales.dao.ContractDAO;
import com.estimulo.business.sales.dao.ContractDetailDAO;
import com.estimulo.business.sales.dao.EstimateDAO;
import com.estimulo.business.sales.dao.EstimateDetailDAO;
import com.estimulo.business.sales.to.ContractDetailTO;
import com.estimulo.business.sales.to.ContractInfoTO;
import com.estimulo.business.sales.to.EstimateTO;

@Component
public class ContractApplicationServiceImpl implements ContractApplicationService {
	
	@Autowired
	private ContractDAO contractDAO;
	@Autowired
	private ContractDetailDAO contractDetailDAO;
	@Autowired
	private EstimateDAO estimateDAO;
	@Autowired
	private EstimateDetailDAO estimateDetailDAO;
	
	@Override
	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String startDate, String endDate, String customerCode) {
		ArrayList<ContractInfoTO> contractInfoTOList = null;
		HashMap<String,String> map = new HashMap<>();
		map.put("searchCondition",searchCondition);
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("customerCode",customerCode);
		
		contractInfoTOList = contractDAO.selectContractList(map);
		for (ContractInfoTO bean : contractInfoTOList) {
			bean.setContractDetailTOList(contractDetailDAO.selectContractDetailList(bean.getContractNo()));
		}
		return contractInfoTOList;
	}
	
	@Override
	public ArrayList<ContractInfoTO> getDeliverableContractList(HashMap<String, String> ableSearchConditionInfo) {
		ArrayList<ContractInfoTO> contractInfoTOList = null;
		contractInfoTOList = contractDAO.selectDeliverableContractList(ableSearchConditionInfo);

		for (ContractInfoTO bean : contractInfoTOList) { 
			bean.setContractDetailTOList(contractDetailDAO.selectDeliverableContractDetailList(bean.getContractNo()));
		}
		return contractInfoTOList;
	}
	
	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String contractNo) {
		ArrayList<ContractDetailTO> contractDetailTOList = null;

		contractDetailTOList = contractDetailDAO.selectContractDetailList(contractNo);

		return contractDetailTOList;
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {
		ArrayList<EstimateTO> estimateListInContractAvailable = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		estimateListInContractAvailable = contractDAO.selectEstimateListInContractAvailable(map);

		for (EstimateTO bean : estimateListInContractAvailable) {
			bean.setEstimateDetailTOList(estimateDetailDAO.selectEstimateDetailList(bean.getEstimateNo()));
		}
			
		return estimateListInContractAvailable;
	}

	@Override
	public String getNewContractNo(String contractDate) {
		StringBuffer newContractNo = null;

		int i = contractDAO.selectContractCount(contractDate);
		System.out.println(i);
		newContractNo = new StringBuffer();
		newContractNo.append("CO"); //CO
		newContractNo.append(contractDate.replace("-", "")); //CO2020-04-27 -> CO20200427 -> CO2020042701
		newContractNo.append(String.format("%02d", i));	//CO + contractDate + 01 

		return newContractNo.toString();
	}

	@Override
	public ModelMap addNewContract(HashMap<String,String[]>  workingContractList) {
		ModelMap resultMap = new ModelMap();
		HashMap<String,String> setValue = new HashMap<String, String>();
		StringBuffer str = null;
		
		setValue=new HashMap<String,String>();
		for(String key:workingContractList.keySet()) {
			str=new StringBuffer();
			
			for(String value:workingContractList.get(key)) {
				if(key.equals("contractDate")) {
					String newContractNo=getNewContractNo(value);	//co2022011667
					str.append(newContractNo+",");//시퀀스로 변경된 날짜를 집어넣음
				}
				else str.append(value+",");
			}
			
			//"estimateNo":["ES2022011653"],"contractType":["CT-01"],"contractRequester":["null"],
			//"personCodeInCharge":["EMP-07"],"discription":["null"],"contractDate":["2022-1-16"],"customerCode":["PTN-01"]}
			
			str.substring(0, str.length()-1);
			if(key.equals("contractDate")) 
				setValue.put("newContractNo", str.toString()); 
				
			else 
			setValue.put(key, str.toString()); //hashMap<string,string[]> 을 <string,string> 으로 바꿔준다.		
		}
		contractDetailDAO.insertContractDetail(setValue); //프로시저 호출
		
		resultMap.put("gridRowJson", setValue.get("RESULT"));
		resultMap.put("errorCode", setValue.get("ERROR_CODE"));
		resultMap.put("errorMsg", setValue.get("ERROR_MSG"));
			
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {
		HashMap<String, Object> resultMap = new HashMap<>();

		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		for (ContractDetailTO bean : contractDetailTOList) {

			String status = bean.getStatus();

			switch (status) {

			case "INSERT":
				/*contractDetailDAO.insertContractDetail(bean);*/
				insertList.add(bean.getContractDetailNo());
				break;

			case "UPDATE":
				/*contractDetailDAO.updateContractDetail(bean);*/
				updateList.add(bean.getContractDetailNo());
				break;
				
			case "DELETE":
				contractDetailDAO.deleteContractDetail(bean);
				deleteList.add(bean.getContractDetailNo());
				break;
			}
		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);

		return resultMap;
	}

	@Override
	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {
												//estimateNO , "Y"
		estimateDAO.changeContractStatusOfEstimate(estimateNo, contractStatus);
	}

}
