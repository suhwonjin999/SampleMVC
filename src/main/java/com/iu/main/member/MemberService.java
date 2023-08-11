package com.iu.main.member;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.iu.main.util.FileManager;

@Service
public class MemberService {
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private FileManager fileManager;
	
	public MemberDTO getIdCheck(MemberDTO memberDTO) throws Exception{
		return memberDAO.getIdCheck(memberDTO); 
	}
		
	public int setJoin(MemberDTO memberDTO, MultipartFile multipartFile, HttpSession session)throws Exception{
		String path="/resources/upload/member/";
		
		int result = memberDAO.setJoin(memberDTO);
		
		if(!multipartFile.isEmpty()) {
		
			String fileName = fileManager.fileSave(path, session, multipartFile);
			
			MemberFileDTO memberFileDTO = new MemberFileDTO();
			memberFileDTO.setId(memberDTO.getId());
			memberFileDTO.setOriginalName(multipartFile.getOriginalFilename());
			memberFileDTO.setFileName(fileName);
			result = memberDAO.setFileJoin(memberFileDTO);
		}
		
		
		
		return result;//memberDAO.setJoin(memberDTO);
	}
	
		
	public MemberDTO getLogin(MemberDTO memberDTO) throws Exception{
		
		return memberDAO.getLogin(memberDTO);
	}
	public int setMemberUpdate(MemberDTO memberDTO) throws Exception{
		return memberDAO.setMemberUpdate(memberDTO);
	}
	
}
