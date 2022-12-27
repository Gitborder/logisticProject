package com.estimulo.companymanaging.authoritymanager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShowErrorPageController {
	private static Logger logger = LoggerFactory.getLogger(Controller.class);
	private ModelMap modelMap = new ModelMap();
	private ModelAndView modelAndView;
	
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug("ShowErrorPageController :  handleRequestInternal");
		}
		String viewName = "redirect:" + request.getContextPath() + "/hello.html";

		if (request.getRequestURI().contains("accessDenied")) {
			modelMap.put("errorCode", -1);
			modelMap.put("errorTitle", "Access Denied");
			modelMap.put("errorMsg", "액세스 거부되었습니다");
			viewName = "errorPage";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("ShowErrorPageController :  handleRequestInternal");
		}
		modelAndView = new ModelAndView(viewName, modelMap);
		return modelAndView;
	}
}
