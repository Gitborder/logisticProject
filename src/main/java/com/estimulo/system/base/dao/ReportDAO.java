package com.estimulo.system.base.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.system.base.to.ContractReportTO;
import com.estimulo.system.base.to.EstimateReportTO;

@Mapper
public interface ReportDAO {

	public ArrayList<EstimateReportTO> selectEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> selectContractReport(String contractNo);
	
}
