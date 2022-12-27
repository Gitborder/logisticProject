package com.estimulo.system.base.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.system.base.servicefacade.BaseServiceFacade;
import com.estimulo.system.base.to.ContractReportTO;
import com.estimulo.system.base.to.EstimateReportTO;
import com.estimulo.system.base.to.ExcelReadOptionTO;


@RestController
@RequestMapping("/base/*")
public class ExcelController{
	
	@Autowired
	private BaseServiceFacade baseSF;
	
	@RequestMapping(value="/downloadExcel.do",method=RequestMethod.GET)
	public void downloadEstimateExcel(HttpServletRequest request, HttpServletResponse response) {
		String target = request.getParameter("target");
		String estimateNo = request.getParameter("data");
		String path="";
		ArrayList<EstimateReportTO> estDataList = null;
		ArrayList<ContractReportTO> contDataList = null;
		
		if(target.equalsIgnoreCase("estimate")) {
			System.out.println("DATA******* "+estimateNo);
			estDataList= baseSF.getEstimateReport(estimateNo);
			System.out.println("********I'M HERE");
			path=request.getSession().getServletContext().getRealPath("\\resources\\excel\\Estimate.xlsx").toString();
			
		}else if(target.equalsIgnoreCase("contract")) {
			contDataList = baseSF.getContractReport(estimateNo);
			path=request.getSession().getServletContext().getRealPath("\\resources\\excel\\Contract.xlsx").toString();
			
		} //나중에 contract도 하면 downloadExcel로 메서드 변경해서 사용할 수 있게 하면 될 듯
		System.out.println("*****************HERE OUT*****************");
		ExcelReadOptionTO ero = new ExcelReadOptionTO();
		String[] outputColumns={"A", "B","C","D","E","F"};
		
		ero.setFilePath(path);
		ero.setOutputColumns(outputColumns);
	    ero.setStartRow(1);
	    
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet(target.toUpperCase());
		Row row = null;
        Cell cell = null;
        int rowNum = 0;
        int cellNum = 0;
        
	    List<Map<String, String>> result=read(ero);
	    String estimateDate = estDataList.get(0).getEstimateDate();
	    String customerTel=estDataList.get(0).getCustomerTelNumber();
	    String customerFax=estDataList.get(0).getCustomerFaxNumber();
	    String customer =estDataList.get(0).getCustomerName();
	    String total = estDataList.get(0).getTotalEstimatePriceWithTax();
	    if(target.equalsIgnoreCase("estimate")) {
		    for(Map<String, String> map : result) {
	    		//열 하나 생성
	    		row=sheet.createRow(rowNum++);
		    	for(int i=0;i<outputColumns.length;i++) {
		    		//해당 열의 셀 생성
		    		String cellValue = "";
		    		if(map.get(outputColumns[i])==null) {
		    			cellValue="";
		    		}else {
		    			cellValue=map.get(outputColumns[i]);
		    		}
	    			sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+(short)300);
	    			//상세정보 넣을 row가 아니면
	    			cell = row.createCell(cellNum++);
		    		if(cellValue.trim()!="") {
			    		//해당 셀의 값 삽입
			    		cell.setCellValue(cellValue);
		    		}else{
		    			String prevCellValue = "";
		    			if(i>0&&map.get(outputColumns[i-1]) != null) {
		    				prevCellValue=map.get(outputColumns[i-1]);
		    			}
		    			switch(prevCellValue) {
		    			case "견적일자" : cell.setCellValue(estimateDate); break;
		    			case "견적번호" : cell.setCellValue(estimateNo); break;
		    			case "발주회사" : cell.setCellValue(customer); break;
		    			case "발주회사TEL" : cell.setCellValue(customerTel); break;
		    			case "발주회사FAX" : cell.setCellValue(customerFax); break;
		    			case "소계(부가세 포함)" : cell.setCellValue(total); break;
		    			default: cell.setCellValue(cellValue); break;
		    			}
		    		}
		    		
		    		//엑셀의 헤더부분일 때
		    		if(rowNum==1&&i==outputColumns.length-1) {
		    			CellStyle basicStyle = wb.createCellStyle(); //style선언
		    			basicStyle.setAlignment(HorizontalAlignment.CENTER); // 가로 가운데 정렬
		    			basicStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 가운데 정렬
		    			basicStyle.setBorderTop(BorderStyle.THIN); // 셀 위 테두리 실선 적용
		    			basicStyle.setBorderBottom(BorderStyle.THIN); // 셀 아래 테두리 실선 적용
		    			basicStyle.setBorderLeft(BorderStyle.THIN); // 셀 왼쪽 테두리 실선 적용
		    			basicStyle.setBorderRight(BorderStyle.THIN); // 셀 오른쪽 테두리 실선 적용
		    			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, i));
		    		}
		    	}
		    	cellNum=0;
		    	if(rowNum==12) {
	    			for(EstimateReportTO est : estDataList) {
			    		row=sheet.createRow(rowNum++);
		    			for(int j=0;j<outputColumns.length;j++) {
			    			cell = row.createCell(cellNum++);
			    			switch(j) {
			    			case 0: cell.setCellValue(est.getItemCode()); break;
			    			case 1: cell.setCellValue(est.getItemName()); break;
			    			case 2: cell.setCellValue(est.getUnitOfEstimate()); break;
			    			case 3: cell.setCellValue(est.getEstimateAmount()); break;
			    			case 4: cell.setCellValue(est.getUnitPriceOfEstimate()); break;
			    			case 5: cell.setCellValue(est.getSumPriceOfEstimate()); break;
			    			}
		    			}
		    			cellNum=0;
	    			}
	    		}
		    }
	    }
	    response.setHeader("Content-Type","text/html; charset=utf-8");
	    response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    response.setHeader("Content-Disposition", String.format("attachment; filename="+target+".xlsx"));

	    OutputStream out = null;
		try {
			out = response.getOutputStream();
			wb.write(out);
			out.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			if(wb!=null) {
				try {
					wb.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(out!=null) {
		        try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<Map<String,String>> read(ExcelReadOptionTO excelReadOption) {
		//엑셀 파일 자체
        //엑셀파일을 읽어 들인다.
        //FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다.
		 Workbook wb = getWorkbook(excelReadOption.getFilePath()); 
      
        int sheetNum = wb.getNumberOfSheets(); //시트의 수를 가져오기 위한 변수 
        int numOfCells = 0;
        
        Row row = null;
        Cell cell = null;
        
        String cellName = "";
        
        /**
         * 각 row마다의 값을 저장할 맵 객체
         * 저장되는 형식은 다음과 같다.
         * put("A", "이름");
         * put("B", "게임명");
         */
        Map<String, String> map = null;

        /*
         * 각 Row를 리스트에 담는다.
         * 하나의 Row를 하나의 Map으로 표현되며
         * List에는 모든 Row가 포함될 것이다.
         */
        List<Map<String, String>> result = new ArrayList<Map<String, String>>(); 
        
        
        //이부분이 수정되었다.
	    for(int i =0; i<sheetNum; i++){
	            Sheet sheet = wb.getSheetAt(i);
	        
	            int numOfRows = sheet.getPhysicalNumberOfRows(); //유효한 데이터가 있는 행의 개수를 가져온다.
	            
	            /**
	             * 각 Row만큼 반복을 한다.
	             */
	            for(int rowIndex = excelReadOption.getStartRow() - 1; rowIndex < numOfRows; rowIndex++) {
	            
	                /*
	             * 워크북에서 가져온 시트에서 rowIndex에 해당하는 Row를 가져온다.
	             * 하나의 Row는 여러개의 Cell을 가진다.
	             */
	            row = sheet.getRow(rowIndex);
	            
	            if(row != null) {
	                /*
	                 * 가져온 Row의 Cell의 개수를 구한다. 
	                 */
	                numOfCells = row.getPhysicalNumberOfCells(); //한개의 행마다 몇개의 cell이 있는지 체크 
	                
	                /*
	                 * 데이터를 담을 맵 객체 초기화
	                 */
	                map = new HashMap<String, String>();
	                
	                /*
	                 * cell의 수 만큼 반복한다.
	                 */
	                for(int cellIndex = 0; cellIndex < numOfCells; cellIndex++) {
	                    /*
	                     * Row에서 CellIndex에 해당하는 Cell을 가져온다.
	                     */
	                    cell = row.getCell(cellIndex);
	                    /*
	                     * 현재 Cell의 이름을 가져온다
	                     * 이름의 예 : A,B,C,D,......
	                     */
	                    cellName = getName(cell, cellIndex);
	                    /*
	                     * 추출 대상 컬럼인지 확인한다
	                     * 추출 대상 컬럼이 아니라면, 
	                     * for로 다시 올라간다
	                     */
	                    if( !excelReadOption.getOutputColumns().contains(cellName) ) {
	                        continue;
	                    }
	                    /*
	                     * map객체의 Cell의 이름을 키(Key)로 데이터를 담는다.
	                     */
	                    map.put(cellName, getValue(cell));
	                }
	                /*
	                 * 만들어진 Map객체를 List로 넣는다.
	                 */
	                result.add(map);   
	            }
	            
	        }
	    }
        return result;
	}
	
	public Workbook getWorkbook(String path) {
		FileInputStream fis =null;
		try {
			fis = new FileInputStream(path);
		}catch(FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		Workbook wb = null;
		try {
			if(path.toUpperCase().endsWith(".XLS")) {
				wb = new HSSFWorkbook(fis);
			}else if(path.toUpperCase().endsWith(".XLSX")) {
				wb = new XSSFWorkbook(fis);
			}
		}catch(IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return wb;
	}
	
	public static String getName(Cell cell, int cellIndex) {
        int cellNum = 0;
        if(cell != null) {
            cellNum = cell.getColumnIndex();
        }
        else {
            cellNum = cellIndex;
        }
        
        return CellReference.convertNumToColString(cellNum);
    }
    
    public static String getValue(Cell cell) {
        String value = "";
        if(cell == null) {
            value = "";
        }
        else {
            if( cell.getCellType() == CellType.FORMULA ) {
                value = cell.getCellFormula();
            }
            else if( cell.getCellType() == CellType.NUMERIC ) {
                value = cell.getNumericCellValue() + "";
            }
            else if( cell.getCellType() == CellType.STRING ) {
                value = cell.getStringCellValue();
            }
            else if( cell.getCellType() == CellType.BOOLEAN ) {
                value = cell.getBooleanCellValue() + "";
            }
            else if( cell.getCellType() == CellType.ERROR ) {
                value = cell.getErrorCellValue() + "";
            }
            else if( cell.getCellType() == CellType.BLANK ) {
                value = "";
            }
            else {
                value = cell.getStringCellValue();
            }
        }
        return value;
    }
}