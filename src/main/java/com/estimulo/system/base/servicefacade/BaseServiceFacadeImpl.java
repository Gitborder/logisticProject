package com.estimulo.system.base.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.system.base.applicationservice.AddressApplicationService;
import com.estimulo.system.base.applicationservice.CodeApplicationService;
import com.estimulo.system.base.applicationservice.ReportApplicationService;
import com.estimulo.system.base.applicationservice.boardApplicationService;
import com.estimulo.system.base.to.AddressTO;
import com.estimulo.system.base.to.BoardTO;
import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.base.to.CodeTO;
import com.estimulo.system.base.to.ContractReportTO;
import com.estimulo.system.base.to.EstimateReportTO;

@Service
public class BaseServiceFacadeImpl implements BaseServiceFacade {
	// 참조변수 선언
	@Autowired
	private CodeApplicationService codeAS;
	@Autowired
	private AddressApplicationService addressAS;
	@Autowired
	private ReportApplicationService reportAS;
	@Autowired
	private boardApplicationService boardAS;

	@Override
	public ArrayList<CodeDetailTO> getDetailCodeList(String divisionCode) {

		ArrayList<CodeDetailTO> codeDetailList = codeAS.getDetailCodeList(divisionCode);
		
		return codeDetailList;
	}

	@Override
	public ArrayList<CodeTO> getCodeList() {

		ArrayList<CodeTO> codeList = codeAS.getCodeList();

		return codeList;
	}

	@Override
	public Boolean checkCodeDuplication(String divisionCode, String newDetailCode) {

		Boolean flag = codeAS.checkCodeDuplication(divisionCode, newDetailCode);
		
		return flag;
	}

	@Override
	public HashMap<String, Object> batchCodeListProcess(ArrayList<CodeTO> codeList) {
		
		HashMap<String, Object> resultMap = codeAS.batchCodeListProcess(codeList);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchDetailCodeListProcess(ArrayList<CodeDetailTO> detailCodeList) {

		HashMap<String, Object> resultMap = codeAS.batchDetailCodeListProcess(detailCodeList);
		
		return resultMap;
	}

	@Override
	public HashMap<String, Object> changeCodeUseCheckProcess(ArrayList<CodeDetailTO> detailCodeList) {

		HashMap<String, Object> resultMap = codeAS.changeCodeUseCheckProcess(detailCodeList);
		
		return resultMap;
	}

	@Override
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber) {

		ArrayList<AddressTO> addressList= addressAS.getAddressList(sidoName, searchAddressType, searchValue, mainNumber);
		
		return addressList;
	}

	   
	///////////////////////////일괄저장/////////////////////////////////   
   @Override
   public void addCodeInFormation(ArrayList<CodeTO>  CodeTOList) {
       codeAS.addCodeInFormation(CodeTOList);
   }

   @Override
   public ArrayList<CodeDetailTO> getCodeDetailList(String CodeDetail) {
      ArrayList<CodeDetailTO> codeLists = null;
      codeLists = codeAS.getCodeDetailList(CodeDetail);
      
      return codeLists;
   }

    @Override
	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo) {
    	System.out.println("SF IN");
		return reportAS.getEstimateReport(estimateNo);
	}
    
	@Override
	public ArrayList<ContractReportTO> getContractReport(String contractNo) {
		return reportAS.getContractReport(contractNo);
	}

//board
	@Override
	public void addBoard(BoardTO board) {
		// TODO Auto-generated method stub

		boardAS.addBoard(board);
	}

	@Override
	public BoardTO findBoard(int board_seq) {

		return boardAS.getBoard(board_seq);
	}
		
	@Override
	public BoardTO getBoard(String sessionId, int board_seq) {

	BoardTO board = boardAS.getBoard(board_seq);
		if (!sessionId.equals(board.getName())) { // 조회수 1증가
			changeHit(board_seq);
			}

			return board;
		}
	@Override
	public int getRowCount() {

		int dbCount = boardAS.getRowCount();
		return dbCount;
	}
	
	@Override
	public void changeHit(int board_seq) {
		// TODO Auto-generated method stub

		boardAS.changeHit(board_seq);

	}
	
	@Override
	public ArrayList<BoardTO> findBoardList(int sr, int er) {

		ArrayList<BoardTO> list = boardAS.getBoardList(sr, er);

		return list;
	}
	
	public void removeBoard(int board_seq) {
		// TODO Auto-generated method stub

		boardAS.removeBoard(board_seq);
	}
	
}
