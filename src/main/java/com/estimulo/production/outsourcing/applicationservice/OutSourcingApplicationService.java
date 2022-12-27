package com.estimulo.production.outsourcing.applicationservice;

import java.util.ArrayList;

import com.estimulo.production.outsourcing.to.OutSourcingTO;

public interface OutSourcingApplicationService {

	ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus);

}
