package com.iu.main.board.notice;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iu.main.board.BoardDTO;
import com.iu.main.board.BoardService;
import com.iu.main.file.FileDTO;
import com.iu.main.util.FileManger;
import com.iu.main.util.Pager;

@Service
public class NoticeService implements BoardService{
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@Autowired
	private FileManger fileManger;

	//imgdel
	public boolean setContentsImgDelete(String path, HttpSession session)throws Exception{
		//path : /resources/upload/notice/파일명
		System.out.println("service");
		FileDTO fileDTO = new FileDTO();
		
		fileDTO.setFileName(path.substring(path.lastIndexOf("/")+1));
//		path = path.substring(0,path.lastIndexOf("/")+1);
		path = "/resources/upload/notice/";
		System.out.println(path);
		return fileManger.fileDelete(fileDTO, path, session);
		
		
	}
	
	
	//imgup
	public String setContentsImg(MultipartFile file, HttpSession session)throws Exception {
		String path= "/resources/upload/notice/";
		String fileName = fileManger.fileSave(path, file, session);
		return path+fileName;
		
	}
	

	
	//list
	public List<BoardDTO> getList(Pager pager) throws Exception{
		
		pager.makeRowNum();
		Long total = noticeDAO.getTotal(pager);
		pager.makePageNum(total);
		return noticeDAO.getList(pager);
	}
	
	//add
	public int setAdd(BoardDTO boardDTO, MultipartFile[] files, HttpSession session) throws Exception{
		
		String path = "/resources/upload/board/";
		
		int result = noticeDAO.setAdd(boardDTO);
		
		for(MultipartFile multipartFile: files) {
			
			if(multipartFile.isEmpty()) {
				continue;
			}
			
			String fileName = fileManger.fileSave(path, multipartFile, session);
			NoticeFileDTO noticeFileDTO = new NoticeFileDTO();
			noticeFileDTO.setOriginalName(multipartFile.getOriginalFilename());
			noticeFileDTO.setFileName(fileName);
			noticeFileDTO.setNoticeNum(boardDTO.getNum());
			result = noticeDAO.setFileAdd(noticeFileDTO);
		}
		
		return result;
	}
	
	//detail
	public BoardDTO getDetail(BoardDTO boardDTO)throws Exception{
		return noticeDAO.getDetail(boardDTO);
				
	}
	
	//update
	public int setUpdate(BoardDTO boardDTO,MultipartFile[] files,HttpSession session )throws Exception{
		String path = "/resources/upload/board/";
		
		int result = noticeDAO.setUpdate(boardDTO);
		
		
		for(MultipartFile multipartFile: files) {
				
				if(multipartFile.isEmpty()) {
					continue;
				}
				
				String fileName = fileManger.fileSave(path, multipartFile, session);
				NoticeFileDTO noticeFileDTO = new NoticeFileDTO();
				noticeFileDTO.setOriginalName(multipartFile.getOriginalFilename());
				noticeFileDTO.setFileName(fileName);
				noticeFileDTO.setNoticeNum(boardDTO.getNum());
				result = noticeDAO.setFileAdd(noticeFileDTO);
			}
			
			
			return noticeDAO.setUpdate(boardDTO);
					
		}
	
	//delete
	public int setDelete(BoardDTO boardDTO)throws Exception{
		return noticeDAO.setDelete(boardDTO);
				
	}
	
	//filedel,
	public int setFileDelete(NoticeFileDTO noticeFileDTO, HttpSession session)throws Exception{
		//폴더 파일 삭제
		noticeFileDTO = noticeDAO.getFileDetail(noticeFileDTO);
		boolean flag = fileManger.fileDelete(noticeFileDTO, "/resources/upload/board",session );
		if(flag) {
			return noticeDAO.setFileDelete(noticeFileDTO); //de 삭제
		}
		return 0;
		
	}

}
