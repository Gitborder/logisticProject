package com.estimulo.system.base.applicationservice;

import java.util.ArrayList;

import com.estimulo.system.base.to.BoardTO;

public interface boardApplicationService {

	
	public ArrayList<BoardTO> getBoardList();

	public void addBoard(BoardTO board);

	public BoardTO getBoard(int board_seq);

	public void changeHit(int board_seq);

	 public int getRowCount(); 

	public ArrayList<BoardTO> getBoardList(int sr, int er);

	public void removeBoard(int board_seq);
}
