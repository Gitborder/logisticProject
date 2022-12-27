package com.estimulo.system.base.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.system.base.dao.BoardDAO;
import com.estimulo.system.base.to.BoardTO;
import com.estimulo.system.base.utile.BoardFile;

import lombok.RequiredArgsConstructor;


@Component
public class boardApplicationServiceImpl implements boardApplicationService{
	
	@Autowired
	private  BoardDAO boardDAO;
	
	@Override
	public ArrayList<BoardTO> getBoardList() {

		ArrayList<BoardTO> list = boardDAO.selectBoardList();

		return list;
	}
	@Override
	public void addBoard(BoardTO board) {

		if (board.getBoard_seq() != 0) { // 새글이 아님
			BoardTO parentBoard = this.getBoard(board.getBoard_seq());
			board.setRef_seq(parentBoard.getRef_seq());
			board.setReply_seq(parentBoard.getBoard_seq());
			// 답변글일떄
			boardDAO.insertReplyBoard(board);
			for (BoardFile file : board.getBoardFiles())
				boardDAO.insertBoardFile(file);

		} else {
			// 새글일떄
			boardDAO.insertBoard(board);
			for (BoardFile file : board.getBoardFiles())
				boardDAO.insertBoardFile(file);

		}
	}
	
	@Override
	public BoardTO getBoard(int board_seq) {

		return boardDAO.selectBoard(board_seq);
	}

	@Override
	public void changeHit(int board_seq) {

		boardDAO.updateHit(board_seq);

	}
	
	
	  @Override public int getRowCount() {
	 
	  return boardDAO.selectRowCount(); }
	

	@Override
	public ArrayList<BoardTO> getBoardList(int sr, int er) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", sr);
		map.put("end", er);

		return boardDAO.selectBoardList(map);
	}

	public void removeBoard(int board_seq) {

		boardDAO.deleteBoard(board_seq);

	}
}
