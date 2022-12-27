package com.estimulo.production.outsourcing.applicationservice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.production.outsourcing.dao.OutSourcingDAO;
import com.estimulo.production.outsourcing.to.OutSourcingTO;

@Component
public class OutSourcingApplicationServiceImpl implements OutSourcingApplicationService{
	
	@Autowired
	private OutSourcingDAO OutSourcingDAO;
	
	@Override
	public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {
		ArrayList<OutSourcingTO> OutSourcingList = null;
		OutSourcingList = OutSourcingDAO.selectOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);

		return OutSourcingList;
	}
}
