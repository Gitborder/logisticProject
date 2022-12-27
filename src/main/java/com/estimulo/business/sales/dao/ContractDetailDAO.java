package com.estimulo.business.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.ui.ModelMap;

import com.estimulo.business.sales.to.ContractDetailTO;
import com.estimulo.production.operation.to.ContractDetailInMpsAvailableTO;

@Mapper
public interface ContractDetailDAO {

	public ArrayList<ContractDetailTO> selectContractDetailList(String contractNo);

	public ArrayList<ContractDetailTO> selectDeliverableContractDetailList(String contractNo);
	
	public int selectContractDetailCount(String contractNo);

	public ArrayList<ContractDetailInMpsAvailableTO> selectContractDetailListInMpsAvailable(HashMap<String,String> map);
/*
	public void insertContractDetail(ContractDetailTO TO);

	public void updateContractDetail(ContractDetailTO TO);*/

	public void changeMpsStatusOfContractDetail(HashMap<String,String> map);

	public void deleteContractDetail(ContractDetailTO TO);
	
	public ModelMap insertContractDetail(HashMap<String,String>  workingContractList);

}
