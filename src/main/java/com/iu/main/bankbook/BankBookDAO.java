package com.iu.main.bankbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iu.main.util.Pager;

@Repository
public class BankBookDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	//BankBookMapper와 같은것을 씀
	private final String NAMESPACE="com.iu.main.bankbook.BankBookDAO.";
	
	public BankBookFileDTO getFileDetail(BankBookFileDTO bankBookFileDTO, HttpSession session) throws Exception{
		return sqlSession.selectOne(NAMESPACE+"getFileDetail", bankBookFileDTO);
	}
	
	public int setFileDelete(BankBookFileDTO bankBookFileDTO) throws Exception{
		return sqlSession.delete(NAMESPACE+"setFileDelete", bankBookFileDTO);
	}
	
	//total
	public Long getTotal(Pager pager)throws Exception{
		return sqlSession.selectOne(NAMESPACE+"getTotal", pager);
	}
	
	//List
	public List<BankBookDTO> getList(Pager pager) throws Exception{
		return sqlSession.selectList(NAMESPACE+"getList", pager);
	}  //selectList를 써서 여러개의 select가 나올 것을 의미함.
	   //어떤 맴퍼 + 아이디를 통해 어떤 쿼리문을 쓸것인지!
	
	//detail
	public BankBookDTO getDetail(BankBookDTO bankBookDTO) throws Exception{
		
		// 연결을 mybatis에서 함 연결 끊는 것도 mybatis에서 해줌
		
		return sqlSession.selectOne(NAMESPACE+"getDetail", bankBookDTO);
		// getDetail은 위에 NAMESPACE의 아이디를 의미한다. 예전 ?에 해당하는 것으로 마지막은 DB에 보내줄 것(DTO) 넘겨줌
	}	// 익스큐트 쿼리로 보낼지 익스큐트 업데이트로 보낼지는 메서드에서 정함 	
	
	//시퀀스
	public long getSequence() throws Exception{
		return sqlSession.selectOne(NAMESPACE+"getSequence");
	}
	//add
	public int setAdd(BankBookDTO bankBookDTO) throws Exception{
		return sqlSession.insert(NAMESPACE+"setAdd", bankBookDTO);
	}
	
	//fileadd
	public int setFileAdd(BankBookFileDTO bankBookFileDTO)throws Exception{
		return sqlSession.insert(NAMESPACE+"setFileAdd", bankBookFileDTO);
	}
	
	
	//delete
	public int setDelete(Long num)throws Exception{
		return sqlSession.delete(NAMESPACE+"setDelete", num);
		//하나의 값을 보낼때는 변수를 보낼 수도 있다. 덜 쓰는게 더 안전하다.
		//Mapper에서도 같은 변수명을 써주어야한다.
	}
	
	//update
	public int setUpdate(BankBookDTO bankBookDTO)throws Exception{
		return sqlSession.update(NAMESPACE+"setUpdate", bankBookDTO);
	}

}



