package com.estimulo.production.outsourcing.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.production.outsourcing.to.OutSourcingTO;

@Mapper
public interface OutSourcingDAO {

	ArrayList<OutSourcingTO> selectOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus);

}
