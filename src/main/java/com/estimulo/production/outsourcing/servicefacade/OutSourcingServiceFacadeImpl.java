package com.estimulo.production.outsourcing.servicefacade;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.production.outsourcing.applicationservice.OutSourcingApplicationService;
import com.estimulo.production.outsourcing.to.OutSourcingTO;

@Service
public class OutSourcingServiceFacadeImpl implements OutSourcingServiceFacade{
	
	@Autowired
	private OutSourcingApplicationService OutSourcingApplicationService;
	
	@Override
	public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {
		ArrayList<OutSourcingTO> OutSourcingList = null;
		OutSourcingList = OutSourcingApplicationService.searchOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);
		
		return OutSourcingList;
	}
}
