package com.estimulo.system.base.to;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CodeTO extends BaseTO {
	
	private String divisionCodeNo;
	private String codeType;
	private String divisionCodeName;
	private String codeChangeAvailable;
	private String description;
	private ArrayList<CodeDetailTO> codeDetailTOList;

	public ArrayList<CodeDetailTO> getCodeDetailTOList() {
		return codeDetailTOList;
	}
	public void setCodeDetailTOList(ArrayList<CodeDetailTO> codeDetailTOList) {
		this.codeDetailTOList = codeDetailTOList;
	}
	
}