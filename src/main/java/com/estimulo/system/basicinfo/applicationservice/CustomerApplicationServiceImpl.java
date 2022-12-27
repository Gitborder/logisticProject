package com.estimulo.system.basicinfo.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.system.base.dao.CodeDetailDAO;
import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.basicinfo.dao.CustomerDAO;
import com.estimulo.system.basicinfo.to.CustomerTO;

@Component
public class CustomerApplicationServiceImpl implements CustomerApplicationService {
	
	// 참조변수 선언
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private CodeDetailDAO codeDetailDAO;

	public ArrayList<CustomerTO> getCustomerList(String searchCondition, String companyCode, String workplaceCode,String itemGroupCode) {
		ArrayList<CustomerTO> customerList = null;

		switch (searchCondition) {
			case "ALL":
				customerList = customerDAO.selectCustomerListByCompany();
				break;
	
			case "WORKPLACE":
				customerList = customerDAO.selectCustomerListByWorkplace(workplaceCode);
				break;
				
			case "ITEM":
				customerList = customerDAO.selectCustomerListByItem(itemGroupCode);
				break;
		}
		return customerList;
	}

	public String getNewCustomerCode(String companyCode) {
		ArrayList<CustomerTO> customerList = null;
		String newCustomerCode = null;
			
		customerList = customerDAO.selectCustomerListByCompany();

		TreeSet<Integer> customerCodeNoSet = new TreeSet<>();

		for (CustomerTO bean : customerList) {
			if (bean.getCustomerCode().startsWith("PTN-")) {
				try {
					Integer no = Integer.parseInt(bean.getCustomerCode().split("PTN-")[1]);
					customerCodeNoSet.add(no);
				} catch (NumberFormatException e) {
					// "PTN-" 다음 부분을 Integer 로 변환하지 못하는 경우 : 그냥 다음 반복문 실행
				}
			}
		}
		if (customerCodeNoSet.isEmpty()) {
			newCustomerCode = "PTN-" + String.format("%02d", 1);
		} else {
			newCustomerCode = "PTN-" + String.format("%02d", customerCodeNoSet.pollLast() + 1);
		}
		return newCustomerCode;
	}

	public HashMap<String, Object> batchCustomerListProcess(ArrayList<CustomerTO> customerList) {
		HashMap<String, Object> resultMap = new HashMap<>();
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		CodeDetailTO detailCodeBean = new CodeDetailTO();

		for (CustomerTO bean : customerList) {
			String status = bean.getStatus();
			switch (status) {
				case "INSERT":
					// 새로운 부서번호 생성 후 bean 에 set
					String newCustomerCode = getNewCustomerCode(bean.getWorkplaceCode());
					bean.setCustomerCode(newCustomerCode);
	
					// 부서 테이블에 insert
					customerDAO.insertCustomer(bean);
					insertList.add(bean.getCustomerCode());
	
					// CODE_DETAIL 테이블에 Insert
					detailCodeBean.setDivisionCodeNo("CL-01");
					detailCodeBean.setDetailCode(bean.getCustomerCode());
					detailCodeBean.setDetailCodeName(bean.getCustomerName());
	
					codeDetailDAO.insertDetailCode(detailCodeBean);
					break;
	
				case "UPDATE":
					customerDAO.updateCustomer(bean);
					updateList.add(bean.getCustomerCode());
	
					// CODE_DETAIL 테이블에 Update
					detailCodeBean.setDivisionCodeNo("CL-01");
					detailCodeBean.setDetailCode(bean.getCustomerCode());
					detailCodeBean.setDetailCodeName(bean.getCustomerName());
	
					codeDetailDAO.updateDetailCode(detailCodeBean);
					break;
	
				case "DELETE":
					customerDAO.deleteCustomer(bean);
					deleteList.add(bean.getCustomerCode());
	
					// CODE_DETAIL 테이블에 Delete
					detailCodeBean.setDivisionCodeNo("CL-01");
					detailCodeBean.setDetailCode(bean.getCustomerCode());
					detailCodeBean.setDetailCodeName(bean.getCustomerName());
	
					codeDetailDAO.deleteDetailCode(detailCodeBean);
					break;
			}
		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);

		return resultMap;
	}
}
