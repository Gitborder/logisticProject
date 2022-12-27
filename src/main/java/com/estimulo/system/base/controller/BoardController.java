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

		map.put("errorMsg", "삭제성공");
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
			int board_seq = 0; // 새글

			if (!pboard_seq.equals("0")) { // 새글이 아닐떄 즉 답변글
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

			map.put("errorMsg", "등록에 성공");
			map.put("errorCode", 0);
		} catch (IOException io) {
			map.put("errorMsg", "저장실패");
			map.put("errorCode", 1);
		}

		return map;
	}

	// 파일을 다운로드 받을 수 있게 하기 위해서 mine 타입설정
	@GetMapping(value = "/downloadBoardFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadBoardFile(@RequestParam String fileName,
			@RequestParam String tempFileName) {

		// 파일업로드시 저장한 위치!
		String filePath = "C:\\apache\\httpd-2.2.34-win64\\Apache2\\htdocs\\file";
		Resource resource = new FileSystemResource(filePath + tempFileName);
		// 저장시 uuid이용 해서 _이후로 실제 파일명만 나올수있도록 설정해줌
		String resourceName = resource.getFilename().substring(resource.getFilename().lastIndexOf("_") + 1);
		HttpHeaders headers = new HttpHeaders();
		try {
			// httphead객체로 다운로드시 저장되는 이름은 Content-disposition을 이용. 그리고 파일 이름에대한 문자열 처리를
			// 해줌(아니면 파일이름이 한글일때 깨질수가있음)
			headers.add("Content-disposition",
					"attachment;filename=" + new String(resourceName.getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
}
