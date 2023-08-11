package com.iu.main.member;

import java.util.List;

import javax.swing.plaf.PanelUI;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
		
	@Autowired
	private SqlSession sqlSession;
	
	public MemberDTO getIdCheck(MemberDTO memberDTO) {
	return sqlSession.selectOne(NAMESPACE+"getIdCheck",memberDTO);
			
	}
	
	public int setFileJoin(MemberFileDTO memberFileDTO)throws Exception{
		return sqlSession.insert(NAMESPACE+"setFileJoin", memberFileDTO);
	}
	private final String NAMESPACE="com.iu.main.member.MemberDAO.";

	public int setJoin(MemberDTO memberDTO) throws Exception{
		return sqlSession.insert(NAMESPACE+"setJoin",memberDTO);
	}
	public MemberDTO getLogin(MemberDTO memberDTO) throws Exception{
		return sqlSession.selectOne(NAMESPACE+"getLogin", memberDTO);
	}
	public int setMemberUpdate(MemberDTO memberDTO) throws Exception{
		return sqlSession.update(NAMESPACE+"setMemberUpdate", memberDTO);
	}
}
