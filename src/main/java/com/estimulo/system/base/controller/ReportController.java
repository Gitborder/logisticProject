package com.estimulo.system.base.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.estimulo.system.base.servicefacade.BaseServiceFacade;
import com.estimulo.system.base.to.ContractReportTO;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Controller
@RequestMapping("/base/*")
public class ReportController  {

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();
	
	@Autowired
	private BaseServiceFacade baseSF;
	
	@Autowired
	private DataSource data;
	
	@RequestMapping(value="/report.pdf" ,method=RequestMethod.GET)
	public ModelAndView estimateReport(HttpServletRequest request, HttpServletResponse response) {
		String iReportFolderPath=request.getSession().getServletContext().getRealPath("\\resources").toString();
		HashMap<String, Object> parameters = new HashMap<>();
	      // 레포트 이름
	      String reportName = "\\iReportForm\\Estimate.jrxml";
	      InputStream inputStream=null;
	      ServletOutputStream out=null;
	      try {
	    	
	          response.setCharacterEncoding("UTF-8");
	          String orderDraftNo = request.getParameter("orderDraftNo");
	          parameters.put("orderDraftNo", orderDraftNo);
	          parameters.put("iReportFolderPath", iReportFolderPath.toString());
	        
	          Connection conn = data.getConnection();
	          
	          
	          inputStream = new FileInputStream(iReportFolderPath + reportName);
	          //jrxml 형식으로 읽어옴
	          JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
	          //jrxml 을 내가 원하는 모양을 가지고옴
	          JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	          //그 틀에 맞춰서 파라메터의 정보를 넣어줌
	          JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,conn);
	          
	          out = response.getOutputStream();
	          response.setContentType("application/pdf");
	          JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	          out.println();
	          out.flush();         

	       } catch (Exception e) {

	          e.printStackTrace();
	       } finally {
	     	  if(inputStream!=null) {
	     		  try {
	 				inputStream.close();
	 			} catch (IOException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	     	  }
	     	  if(out!=null) {
	     		  try {
	 				out.close();
	 			} catch (IOException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	     	  }
	       }
	       return null;
	}

	public ModelAndView contractReport(HttpServletRequest request, HttpServletResponse response) {

		String contractNo = request.getParameter("orderDraftNo");

		try {

			ArrayList<ContractReportTO> contractList = baseSF.getContractReport(contractNo);

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(contractList);
			modelMap.put("source", source);
			modelMap.put("format", "pdf");

		} catch (Exception e) {

			e.printStackTrace();
		}

		modelAndView = new ModelAndView("contractPdfView", modelMap);
		return modelAndView;
	}
}