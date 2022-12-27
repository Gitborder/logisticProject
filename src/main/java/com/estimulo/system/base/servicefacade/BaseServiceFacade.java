package com.estimulo.system.base.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.base.to.AddressTO;
import com.estimulo.system.base.to.BoardTO;
import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.base.to.CodeTO;
import com.estimulo.system.base.to.ContractReportTO;
import com.estimulo.system.base.to.EstimateReportTO;



public interface BaseServiceFacade {

	public ArrayList<CodeDetailTO> getDetailCodeList(String divisionCode);

	public ArrayList<CodeTO> getCodeList();

	public Boolean checkCodeDuplication(String divisionCode, String newDetailCode);

	public HashMap<String, Object> batchCodeListProcess(ArrayList<CodeTO> codeList);

	public HashMap<String, Object> batchDetailCodeListProcess(ArrayList<CodeDetailTO> detailCodeList);

	public HashMap<String, Object> changeCodeUseCheckProcess(ArrayList<CodeDetailTO> detailCodeList);

	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber);
	
	public void addCodeInFormation(ArrayList<CodeTO>  CodeTOList);	
	
	public ArrayList<CodeDetailTO> getCodeDetailList(String CodeDetail);
	
	ArrayList<EstimateReportTO> getEstimateReport(String estimateNo);

	ArrayList<ContractReportTO> getContractReport(String contractNo);
	
	//board
	public void addBoard(BoardTO board);

	public BoardTO findBoard(int board_seq);

	public BoardTO getBoard(String sessionId, int board_seq);

	public void changeHit(int board_seq);

	public int getRowCount();

	public ArrayList<BoardTO> findBoardList(int sr, int er);

	public void removeBoard(int board_seq);
	
}
