package com.estimulo.business.sales.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.business.sales.to.EstimateDetailTO;

@Mapper
public interface EstimateDetailDAO {

	public ArrayList<EstimateDetailTO> selectEstimateDetailList(String estimateNo);

	public String selectEstimateDetailCount(String estimateNo);

	public void insertEstimateDetail(EstimateDetailTO TO);

	public void updateEstimateDetail(EstimateDetailTO TO);

	public void deleteEstimateDetail(EstimateDetailTO TO);

	public int selectEstimateDetailSeq(String estimateDate);
	
	public void initDetailSeq(String EST_DETAIL_SEQ);
	
	public void reArrangeEstimateDetail(EstimateDetailTO bean,String newSeq);
}