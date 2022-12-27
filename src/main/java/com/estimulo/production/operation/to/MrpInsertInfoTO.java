package com.estimulo.production.operation.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MrpInsertInfoTO {
	private String firstMrpNo;
	private String lastMrpNo;
	private String length;
}
