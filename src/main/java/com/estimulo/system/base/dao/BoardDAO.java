package com.estimulo.system.base.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.system.base.to.BoardTO;
import com.estimulo.system.base.utile.BoardFile;


@Mapper
public interface BoardDAO {
	public ArrayList<BoardTO> selectBoardList();
	public BoardTO selectBoard(int board_seq);
	public void insertBoard(BoardTO board);
	public void insertReplyBoard(BoardTO board);
	public void updateHit(int board_seq);
	public int selectRowCount();
	public ArrayList<BoardTO> selectBoardList(HashMap<String,Object> map);
	public void deleteBoard(int board_seq);
	public void insertBoardFile(BoardFile file);
}
