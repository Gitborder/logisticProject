package com.estimulo.system.base.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.estimulo.system.base.servicefacade.BaseServiceFacadeImpl;
import com.estimulo.system.base.to.BoardTO;
import com.estimulo.system.base.to.ListForm;
import com.estimulo.system.base.utile.BoardFile;
import com.estimulo.system.base.utile.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@RequestMapping("/base/*")
@RequiredArgsConstructor
@RestController
public class BoardController {
	
	@Autowired
	private final BaseServiceFacadeImpl BaseServiceFacade;
	private ModelMap map = new ModelMap();

	@RequestMapping("/findBoardList")
	public ModelMap findBoardList(@RequestParam String pn, @RequestParam String pselectValue) {

		ArrayList<BoardTO> list = null;
		ListForm boardList = null;
		int pagenum, sr, er, dbCount, selectValue;

		pagenum = 1;
		if (pn != null) {
			pagenum = Integer.parseInt(pn);
		}
		selectValue = Integer.parseInt(pselectValue);
		boardList = new ListForm();
		dbCount = BaseServiceFacade.getRowCount();
		boardList.setRowsize(selectValue);
		boardList.setDbcount(dbCount);
		boardList.setPagenum(pagenum);
		sr = boardList.getStartrow();
		er = boardList.getEndrow();

		list = BaseServiceFacade.findBoardList(sr, er);
		boardList.setList(list);

		map.put("errorMsg", "success");
		map.put("errorCode", 0);
		map.put("boardlist", list);
		map.put("board", boardList);

		return map;
	}

	@RequestMapping("/deleteBoard")
	public ModelMap deleteBoard(@RequestParam String pboard_seq) {

		int board_seq = Integer.parseInt(pboard_seq);
		BaseServiceFacade.removeBoard(board_seq);

		map.put("errorMsg", "????????????");
		map.put("errorCode", 0);

		return map;
	}

	@RequestMapping("/findDetailBoard")
	public ModelMap findDetailBoard(@RequestParam String pboard_seq, @RequestParam String id) {

		int board_seq = Integer.parseInt(pboard_seq);

		BoardTO board = BaseServiceFacade.getBoard(id, board_seq);

		map.put("errorMsg", "success");
		map.put("errorCode", 0);
		map.put("board", board);

		return map;
	}

	@PostMapping("/registBoard")
	public ModelMap registBoard(@RequestParam MultipartFile[] files, @RequestParam String content,
			@RequestParam String name, @RequestParam String title, @RequestParam String pboard_seq,
			@RequestParam String reg_date) {

		BoardTO board = new BoardTO();

		try {
			int board_seq = 0; // ??????

			if (!pboard_seq.equals("0")) { // ????????? ????????? ??? ?????????
				board_seq = Integer.parseInt(pboard_seq);

			}

			@SuppressWarnings("unchecked")
			List<BoardFile> boardfile = (List<BoardFile>) FileUploadUtil.doFileUpload(files);

			board.setContent(content);
			board.setName(name);
			board.setTitle(title);
			board.setBoard_seq(board_seq);
			board.setReg_date(reg_date);
			board.setBoardFiles(boardfile);
			BaseServiceFacade.addBoard(board);

			map.put("errorMsg", "????????? ??????");
			map.put("errorCode", 0);
		} catch (IOException io) {
			map.put("errorMsg", "????????????");
			map.put("errorCode", 1);
		}

		return map;
	}

	// ????????? ???????????? ?????? ??? ?????? ?????? ????????? mine ????????????
	@GetMapping(value = "/downloadBoardFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadBoardFile(@RequestParam String fileName,
			@RequestParam String tempFileName) {

		// ?????????????????? ????????? ??????!
		String filePath = "C:\\apache\\httpd-2.2.34-win64\\Apache2\\htdocs\\file";
		Resource resource = new FileSystemResource(filePath + tempFileName);
		// ????????? uuid?????? ?????? _????????? ?????? ???????????? ?????????????????? ????????????
		String resourceName = resource.getFilename().substring(resource.getFilename().lastIndexOf("_") + 1);
		HttpHeaders headers = new HttpHeaders();
		try {
			// httphead????????? ??????????????? ???????????? ????????? Content-disposition??? ??????. ????????? ?????? ??????????????? ????????? ?????????
			// ??????(????????? ??????????????? ???????????? ??????????????????)
			headers.add("Content-disposition",
					"attachment;filename=" + new String(resourceName.getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
}
