package com.estimulo.business.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.business.sales.to.ContractInfoTO;
import com.estimulo.business.sales.to.ContractTO;
import com.estimulo.business.sales.to.EstimateTO;

@Mapper
public interface ContractDAO {

	public ArrayList<EstimateTO> selectEstimateListInContractAvailable(HashMap<String, String> map);
	
	public ArrayList<ContractInfoTO> selectContractList(HashMap<String,String> map);

//	public ArrayList<ContractInfoTO> selectContractListByDate(String startDate, String endDate);

//	public ArrayList<ContractInfoTO> selectContractListByCustomer(String customerCode);

	public ArrayList<ContractInfoTO> selectDeliverableContractList(HashMap<String, String> ableSearchConditionInfo);
	
	public int selectContractCount(String contractDate);

	public void insertContract(ContractTO TO);

	public void updateContract(ContractTO TO);

	public void deleteContract(ContractTO TO);

}
