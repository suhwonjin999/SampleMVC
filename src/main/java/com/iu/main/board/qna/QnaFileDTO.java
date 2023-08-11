package com.iu.main.board.qna;

import com.iu.main.file.FileDTO;

public class QnaFileDTO extends FileDTO{
	private Long boardNum;

	public Long getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(Long boardNum) {
		this.boardNum = boardNum;
	}
	
	
}
