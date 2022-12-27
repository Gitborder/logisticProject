package com.estimulo.system.base.applicationservice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.system.base.dao.AddressDAO;
import com.estimulo.system.base.to.AddressTO;

@Component
public class AddressApplicationServiceImpl implements AddressApplicationService {
	// DAO 참조변수
	@Autowired
	private AddressDAO addressDAO;

	@Override
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue,
			String mainNumber) {
		ArrayList<AddressTO> addressList = null;
		String sidoCode = addressDAO.selectSidoCode(sidoName);

		switch (searchAddressType) {
			case "roadNameAddress":
				String buildingMainNumber = mainNumber;
				addressList = addressDAO.selectRoadNameAddressList(sidoCode, searchValue, buildingMainNumber);
				break;
	
			case "jibunAddress":
				String jibunMainAddress = mainNumber;
				addressList = addressDAO.selectJibunAddressList(sidoCode, searchValue, jibunMainAddress);
				break;
		}
		return addressList;
	}
}
