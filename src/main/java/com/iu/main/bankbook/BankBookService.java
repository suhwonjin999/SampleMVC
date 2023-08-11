package com.iu.main.bankbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iu.main.member.MemberFileDTO;
import com.iu.main.util.FileManager;
import com.iu.main.util.Pager;

@Service
public class BankBookService {
	@Autowired
	private BankBookDAO bankBookDAO;
	
	@Autowired
	private FileManager fileManager;
	
	
	public int setFileDelete(BankBookFileDTO bankBookFileDTO, HttpSession session) throws Exception{
		bankBookFileDTO=bankBookDAO.getFileDetail(bankBookFileDTO, session);
		boolean flag = fileManager.fileDelete(bankBookFileDTO, "/resources/upload/bankbook/", session);
		
		if(flag) {
			
			return bankBookDAO.setFileDelete(bankBookFileDTO);
		}
		
		return 0;
	}
	
	public List<BankBookDTO> getList(Pager pager) throws Exception{
//		Map<String, Integer> map = new HashMap<String, Integer>();
		//page     startRow      lastRow
		//1           1             10
		//2           11            20
		//.....
//		int count=10;
//		int startRow=(page-1)*count+1;//count가 바뀌더라도 계속 이용가능
//		int lastRow=page*count;
		
		pager.makeRowNum();
		Long total =bankBookDAO.getTotal(pager);
		pager.makePageNum(total);
//		map.put("startRow", startRow);
//		map.put("lastRow", lastRow);

		return bankBookDAO.getList(pager);
	}
	
	public BankBookDTO getDetail(BankBookDTO bankBookDTO) throws Exception{
		return bankBookDAO.getDetail(bankBookDTO);
	}
	
	public int setAdd(BankBookDTO bankBookDTO, MultipartFile [] files, HttpSession session) throws Exception{
		//long num = bankBookDAO.getSequence();
		//bankBookDTO.setBookNum(num);
		int result = bankBookDAO.setAdd(bankBookDTO);
		String path="/resources/upload/bankbook/";
		for(MultipartFile multipartFile: files) {// 배열이라서 for 문을 사용한다.
			
			if(multipartFile.isEmpty()) {
				continue;
			}
			
			String fileName = fileManager.fileSave(multipartFile, session, path);
			BankBookFileDTO bankBookFileDTO = new BankBookFileDTO();
			bankBookFileDTO.setBookNum(bankBookDTO.getBookNum());
			bankBookFileDTO.setOriginalName(multipartFile.getOriginalFilename());
			bankBookFileDTO.setFileName(fileName);
			
			result = bankBookDAO.setFileAdd(bankBookFileDTO);
			
		}

		return result;
	}
	
	public int setDelete(Long num)throws Exception{
		return bankBookDAO.setDelete(num);
	}
	
	public int setUpdate(BankBookDTO bankBookDTO, MultipartFile[]files, HttpSession session)throws Exception{
		int result = bankBookDAO.setUpdate(bankBookDTO);
		String path="/resources/upload/bankbook/";
		for(MultipartFile multipartFile: files) {// 배열이라서 for 문을 사용한다.
			
			if(multipartFile.isEmpty()) {
				continue;
			}
			
			String fileName = fileManager.fileSave(multipartFile, session, path);
			BankBookFileDTO bankBookFileDTO = new BankBookFileDTO();
			bankBookFileDTO.setBookNum(bankBookDTO.getBookNum());
			bankBookFileDTO.setOriginalName(multipartFile.getOriginalFilename());
			bankBookFileDTO.setFileName(fileName);
			
			result = bankBookDAO.setFileAdd(bankBookFileDTO);
			
		}

		return result;
	}
	
}
