package com.estimulo.system.base.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseTO {
	
	protected String status = "NORMAL";
}