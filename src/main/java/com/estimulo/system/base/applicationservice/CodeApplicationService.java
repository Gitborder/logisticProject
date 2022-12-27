package com.estimulo.system.base.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.base.to.CodeTO;

public interface CodeApplicationService {

	public ArrayList<CodeDetailTO> getDetailCodeList(String divisionCode);

	public ArrayList<CodeTO> getCodeList();

	public Boolean checkCodeDuplication(String divisionCode, String newDetailCode);
	
	public HashMap<String, Object> batchCodeListProcess(ArrayList<CodeTO> codeList);
	
	public HashMap<String, Object> batchDetailCodeListProcess(ArrayList<CodeDetailTO> detailCodeList);
	
	public HashMap<String, Object> changeCodeUseCheckProcess(ArrayList<CodeDetailTO> detailCodeList);

	public void addCodeInFormation( ArrayList<CodeTO> CodeTOList);
	
	public ArrayList<CodeDetailTO> getCodeDetailList(String CodeDetail);
}
