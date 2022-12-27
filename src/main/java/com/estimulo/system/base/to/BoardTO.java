package com.estimulo.system.base.to;

import java.util.ArrayList;
import java.util.List;

import com.estimulo.system.base.utile.BoardFile;

import lombok.Data;

@Data
public class BoardTO {
	private int board_seq;		
	private int ref_seq;		
	private int reply_seq;		
	private int reply_level;	
	private String reg_date;    
	private String name;		
	private String title;		
	private String content;		
	private int hit;			
	
	private List<BoardFile> boardFiles = new ArrayList<BoardFile>();
	
	
	

}
