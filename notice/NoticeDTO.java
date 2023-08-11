package com.iu.main.board.notice;

import java.sql.Date;
import java.util.List;

import com.iu.main.bankBook.BankBookFileDTO;
import com.iu.main.board.BoardDTO;

public class NoticeDTO extends BoardDTO{
		
	
	
	private List<NoticeDTO> fileDTOs;

	public List<NoticeDTO> getFileDTOs() {
		return fileDTOs;
	}

	public void setFileDTOs(List<NoticeDTO> fileDTOs) {
		this.fileDTOs = fileDTOs;
	}
	
	



}
