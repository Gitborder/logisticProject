package com.estimulo.business.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.business.sales.to.EstimateTO;

@Mapper
public interface EstimateDAO {
	public ArrayList<EstimateTO> selectEstimateList(HashMap<String, String> map);

	public EstimateTO selectEstimate(String estimateNo);

	public int selectEstimateCount(String estimateDate);

	public void insertEstimate(EstimateTO TO);

	public void updateEstimate(EstimateTO TO);

	public void changeContractStatusOfEstimate(String estimateNo, String contractStatus);

}
