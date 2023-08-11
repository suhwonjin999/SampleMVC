package com.iu.main.board.qna;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.iu.main.board.BoardDAO;
import com.iu.main.board.BoardDTO;
import com.iu.main.util.Pager;

@Repository
public class QnaDAO implements BoardDAO{

	@Autowired
	private SqlSession session;
	private final String NameSPACE="com.iu.main.board.qna.QnaDAO.";
	
	@Override
	public List<BoardDTO> getList(Pager pager) throws Exception {
		
		return session.selectList(NameSPACE+"getList", pager);
	}

	@Override
	public BoardDTO getDetail(BoardDTO boardDTO) throws Exception {
		
		return session.selectOne(NameSPACE+"getDetail", boardDTO);
	}

	@Override
	public int setAdd(BoardDTO boardDTO) throws Exception {
		System.out.println(boardDTO.getNum()+"asdasd");
		System.out.println(boardDTO.getName()+"asdasd");
		System.out.println(boardDTO.getContents()+"asdasd");
		
		return session.insert(NameSPACE+"setAdd", boardDTO);
	}

	@Override
	public int setUpdate(BoardDTO boardDTO) throws Exception {
		
		return session.update(NameSPACE+"setUpdate", boardDTO);
	}

	@Override
	public int setDelete(BoardDTO boardDTO) throws Exception {
		
		return session.delete(NameSPACE+"setDelete", boardDTO);
	}

	@Override
	public Long getTotal(Pager pager) throws Exception {
		
		return session.selectOne(NameSPACE+"getTotal", pager);
	}

	@Override
	public int setHitUpdate(BoardDTO boardDTO) throws Exception {
		
		return 0;
	}

	public int setFileAdd(QnaFileDTO qnaFileDTO)throws Exception{
		
		return session.insert(NameSPACE+"setFileAdd", qnaFileDTO);
	}
	
	public int setReplyAdd(QnaDTO qnaDTO) throws Exception{
		
		return session.insert(NameSPACE+"setReplyAdd", qnaDTO);
	}
	
	public int setStepUpdate(QnaDTO qnaDTO) throws Exception{
		
		return session.update(NameSPACE+"setStepUpdate", qnaDTO);
	}
}
