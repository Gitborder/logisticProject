package com.estimulo.system.base.utile;
import com.estimulo.system.base.to.BoardTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BoardFile {
	private int file_seq = 0;
	private BoardTO board = null;
	private String fileName = null;
	private String tempFileName = null;
	private String filesize=null;
	private int board_seq;
}

