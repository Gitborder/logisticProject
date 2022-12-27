package com.estimulo.system.base.applicationservice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.system.base.dao.ReportDAO;
import com.estimulo.system.base.to.ContractReportTO;
import com.estimulo.system.base.to.EstimateReportTO;

@Component
public class ReportApplicationServiceImpl implements ReportApplicationService {

	@Autowired
	private ReportDAO reportDAO;

	@Override
	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo) {
		System.out.println("AS IN");
		return reportDAO.selectEstimateReport(estimateNo);
	}

	@Override
	public ArrayList<ContractReportTO> getContractReport(String contractNo) {
		return reportDAO.selectContractReport(contractNo);
	}
}
