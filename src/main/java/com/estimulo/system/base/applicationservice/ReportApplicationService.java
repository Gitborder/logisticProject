package com.estimulo.system.base.applicationservice;

import java.util.ArrayList;

import com.estimulo.system.base.to.ContractReportTO;
import com.estimulo.system.base.to.EstimateReportTO;



public interface ReportApplicationService {

	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> getContractReport(String contractNo);
	
}
