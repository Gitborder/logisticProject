package com.estimulo.system.base.utile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

//https://bin-repository.tistory.com/118?category=879445
//�̰� �Խ��� ���� fileUpload�Դϴ�. 
public class FileUploadUtil {

	public static Object doFileUpload(MultipartFile[] files) throws FileNotFoundException, IOException {

		List<BoardFile> list = new ArrayList<BoardFile>();
		// ���⼭ ���� ������ ���ε��� ������ �ִ��� Ȯ��!
		String path = "C:\\Apache2\\htdocs\\profile\\";
		File uploadPath = new File(path);
		// ������ false�� ��ȯ�ؼ� �Ʒ� ���� �� ������ ��������!
		if (!uploadPath.exists()) {

			uploadPath.mkdirs();
		}

		for (MultipartFile multipartFile : files) {

			// ���� ��������Ȯ��
			if (!multipartFile.getOriginalFilename().isEmpty()) {

				// �������ε��������̸�
				String filename = multipartFile.getOriginalFilename();

				// ���� �̸��� ������� �ߺ����� �ʰ��ϱ� ���� ���!
				UUID uuid = UUID.randomUUID();
				String newFileName = uuid.toString() + "_" + filename;

			
                //������ ������ ���ϰ�ο� UUID�̿��ؼ� ����<�ߺ�����>
				File savefile = new File(uploadPath,newFileName);
                 //���� ����
				
				multipartFile.transferTo(savefile);
                 //�������Ͽ� ���� �� ����
				String filesize=String.valueOf(multipartFile.getSize());
			   
				BoardFile boardFile = new BoardFile();
				boardFile.setFileName(filename);
				boardFile.setTempFileName(newFileName);
				boardFile.setFilesize(filesize);
				list.add(boardFile);

			}
		}
		return list;
	}
}
