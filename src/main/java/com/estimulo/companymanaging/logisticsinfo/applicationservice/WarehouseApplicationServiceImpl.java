package com.estimulo.companymanaging.logisticsinfo.applicationservice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.companymanaging.logisticsinfo.dao.WarehouseDAO;
import com.estimulo.companymanaging.logisticsinfo.to.WarehouseTO;

@Component
public class WarehouseApplicationServiceImpl implements WarehouseApplicationService{
	
	@Autowired
	private WarehouseDAO warehouseDAO;
		
	@Override
	public ArrayList<WarehouseTO> getWarehouseInfoList(){
		ArrayList<WarehouseTO> warehouseList = null;
		warehouseList = warehouseDAO.selectWarehouseList();

		return warehouseList;
	}
	
	@Override
	public void batchWarehouseProcess(ArrayList<WarehouseTO> warehouseTOList) {
		for (WarehouseTO bean : warehouseTOList) {
			String status = bean.getStatus();
			switch (status) {
				case "DELETE":
					System.out.println("*******************DELETE******************");
					warehouseDAO.deleteWarehouse(bean);
					break;
				case "INSERT":
					System.out.println("*******************INSERT******************");
					warehouseDAO.insertWarehouse(bean);
					break;
				case "UPDATE":
					System.out.println("*******************UPDATE******************");
					warehouseDAO.updateWarehouse(bean);
			}
		}
	}

	@Override
	public String findLastWarehouseCode() {
		// TODO Auto-generated method stub
		return null;
	}
	
    @Override
    public void modifyWarehouseInfo(WarehouseTO warehouseTO) {
       // TODO Auto-generated method stub   
    }
}
