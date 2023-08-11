package com.iu.main.board.qna;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iu.main.board.BoardDTO;
import com.iu.main.board.BoardService;
import com.iu.main.board.notice.NoticeFileDTO;
import com.iu.main.util.FileManager;
import com.iu.main.util.Pager;

@Service
public class QnaService implements BoardService{

	@Autowired
	private QnaDAO qnaDAO;
	
	@Autowired
	private FileManager fileManager;

	@Override
	public List<BoardDTO> getList(Pager pager) throws Exception {
		
		pager.makeRowNum();
		pager.makePageNum(qnaDAO.getTotal(pager));
		
		return qnaDAO.getList(pager);
	}

	@Override
	public BoardDTO getDetail(BoardDTO boardDTO) throws Exception {
		
		return qnaDAO.getDetail(boardDTO);
	}

	@Override
	public int setAdd(BoardDTO boardDTO, MultipartFile[] files, HttpSession session) throws Exception {
		String path="/resources/upload/qna/";
				
				int result = qnaDAO.setAdd(boardDTO);
				
				for(MultipartFile file:files) {
					if(!file.isEmpty()) {
						String fileName=fileManager.fileSave(path, session, file);
						QnaFileDTO qnaFileDTO = new QnaFileDTO();
						qnaFileDTO.setBoardNum(boardDTO.getNum());
						qnaFileDTO.setFileName(fileName);
						qnaFileDTO.setOriginalName(file.getOriginalFilename());
						result=qnaDAO.setFileAdd(qnaFileDTO);
					}
				}
				
				return result;
				
				
	}

	
	
	@Override
	public int setUpdate(BoardDTO boardDTO, MultipartFile [] files, HttpSession session) throws Exception {
		
		return qnaDAO.setUpdate(boardDTO);
	}

	@Override
	public int setDelete(BoardDTO boardDTO) throws Exception {
		
		return qnaDAO.setDelete(boardDTO);
	}
	
	public int setReplyAdd(QnaDTO qnaDTO, MultipartFile[] files, HttpSession session) throws Exception{
		BoardDTO parentDTO = new BoardDTO();
		parentDTO.setNum(qnaDTO.getNum());
		
		parentDTO = qnaDAO.getDetail(parentDTO);
		QnaDTO p = (QnaDTO)parentDTO;
		qnaDTO.setRef(p.getRef());
		qnaDTO.setStep(p.getStep()+1);
		qnaDTO.setDepth(p.getDepth()+1);
		
		int result = qnaDAO.setStepUpdate(p);
		
		result = qnaDAO.setReplyAdd(qnaDTO);
		
		return result;
		
		
	}
	
}
