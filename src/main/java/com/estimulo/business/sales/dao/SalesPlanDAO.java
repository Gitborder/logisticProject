package com.estimulo.business.sales.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.business.sales.to.SalesPlanTO;
import com.estimulo.production.operation.to.SalesPlanInMpsAvailableTO;

@Mapper
public interface SalesPlanDAO {
	public ArrayList<SalesPlanTO> selectSalesPlanList(String dateSearchCondition, String startDate, String endDate);
			
	public int selectSalesPlanCount(String salesPlanDate);
	
	public ArrayList<SalesPlanInMpsAvailableTO>
		selectSalesPlanListInMpsAvailable(String searchCondition, String startDate, String endDate);
	
	public void insertSalesPlan(SalesPlanTO TO);

	public void updateSalesPlan(SalesPlanTO TO);
	
	public void changeMpsStatusOfSalesPlan(String salesPlanNo, String mpsStatus);	
	
	public void deleteSalesPlan(SalesPlanTO TO);
	
}
