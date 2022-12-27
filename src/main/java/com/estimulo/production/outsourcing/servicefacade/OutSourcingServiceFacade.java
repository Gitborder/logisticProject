package com.estimulo.production.outsourcing.servicefacade;

import java.util.ArrayList;

import com.estimulo.production.outsourcing.to.OutSourcingTO;

public interface OutSourcingServiceFacade {
	public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate,String toDate,String customerCode,String itemCode,String materialStatus);
}
