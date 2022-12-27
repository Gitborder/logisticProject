package com.estimulo.companymanaging.authoritymanager.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.companymanaging.authoritymanager.servicefacade.AuthorityManagerServiceFacade;
import com.estimulo.companymanaging.authoritymanager.to.MenuAuthorityTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/authoritymanager/*")
public class MenuController {
	ModelMap map = new ModelMap();
	
	// gson
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	
	@Autowired
	private AuthorityManagerServiceFacade authorityManagerServiceFacade;
	
	@RequestMapping(value="/insertMenuAuthority.do", method=RequestMethod.GET)
	public ModelMap insertMenuAuthority(HttpServletRequest request, HttpServletResponse response) {
		String authorityGroupCode = request.getParameter("authorityGroupCode");
		String insertDataList = request.getParameter("insertData");
		System.out.println(insertDataList); 
		ArrayList<MenuAuthorityTO> menuAuthorityTOList = gson.fromJson(insertDataList,
				new TypeToken<ArrayList<MenuAuthorityTO>>() {}.getType());
		try {
			authorityManagerServiceFacade.insertMenuAuthority(authorityGroupCode, menuAuthorityTOList);
			
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/getMenuAuthority.do", method=RequestMethod.GET)
	public ModelMap getMenuAuthority(HttpServletRequest request, HttpServletResponse response) {
		String authorityGroupCode = request.getParameter("authorityGroupCode");
		try {
			ArrayList<MenuAuthorityTO> menuAuthorityTOList = authorityManagerServiceFacade.getMenuAuthority(authorityGroupCode);
			
			map.put("gridRowJson", menuAuthorityTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
}
