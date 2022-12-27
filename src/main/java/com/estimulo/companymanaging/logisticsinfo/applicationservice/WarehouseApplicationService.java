package com.estimulo.companymanaging.logisticsinfo.applicationservice;

import java.util.ArrayList;

import com.estimulo.companymanaging.logisticsinfo.to.WarehouseTO;

public interface WarehouseApplicationService {
	
	public ArrayList<WarehouseTO> getWarehouseInfoList();

	public void modifyWarehouseInfo(WarehouseTO warehouseTO);
	
	public void batchWarehouseProcess(ArrayList<WarehouseTO> warehouseTOList);

	public String findLastWarehouseCode();
}
