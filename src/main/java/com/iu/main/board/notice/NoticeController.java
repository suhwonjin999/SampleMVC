package com.iu.main.board.notice;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iu.main.board.BoardDTO;
import com.iu.main.util.Pager;

@Controller
@RequestMapping("/notice/*")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@ModelAttribute("board") //reuestmapping 실행되기전에 실행되서 모델에다가 이름은:board value:notice
	public String getBoardName() {
		return "NOTICE";
	}
	
	//imgdel
	@PostMapping("setContentsImgDelete")
	public String setContentsImgDelete(String path, HttpSession session,Model model)throws Exception{
		System.out.println("cont");
		boolean check = noticeService.setContentsImgDelete(path, session);
		model.addAttribute("result", check);
		
		return "commons/ajaxResult";
		
	}
	
	//imgupload
	@PostMapping("setContentsImg")
	public String setContentsImg(MultipartFile files, HttpSession session,Model model)throws Exception{
		
		String path = noticeService.setContentsImg(files, session);
		model.addAttribute("result",path);
		
		return "commons/ajaxResult";
	}

	//list
	@RequestMapping(value = "list", method = RequestMethod.GET )
	public ModelAndView getList(ModelAndView mv,Pager pager)throws Exception{		
		List<BoardDTO> ar = noticeService.getList(pager);
		mv.addObject("list", ar);
		mv.addObject("pager", pager);
		mv.setViewName("board/list");
		
		return mv;
	
	//add
	}
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String setAdd()throws Exception{
		
		return "board/add";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String setAdd(NoticeDTO noticeDTO,MultipartFile [] photos, HttpSession session,Model model )throws Exception{
		
		int result = noticeService.setAdd(noticeDTO,photos,session);
		
		String message="등록 실패";
		
		if(result >0) {
			message="등록 성공";
		}
		model.addAttribute("message", message);
		model.addAttribute("url","list");
		return "commons/result";

				
	}
	//detail
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String getDtail(NoticeDTO noticeDTO, Model model)throws Exception{
		
		BoardDTO boardDTO = noticeService.getDetail(noticeDTO);
		
		if(boardDTO != null) {
		model.addAttribute("dto",boardDTO);
		
		return "board/detail";
		
		}else {
			model.addAttribute("message", "글이 없다");
			model.addAttribute("url", "list");
			return "commons/result";
		}
		
		
	}
	
	//update
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String setUpdate(NoticeDTO noticeDTO, Model model)throws Exception{
		BoardDTO boardDTO = noticeService.getDetail(noticeDTO);
		model.addAttribute("dto",boardDTO);
		
		return "board/update";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String setUpdate(NoticeDTO noticeDTO, MultipartFile[] photos,HttpSession session )throws Exception{
		int result = noticeService.setUpdate(noticeDTO,photos, session);
		
		return "redirect:./list";
	}
	
	//delete
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String setdelete(NoticeDTO noticeDTO)throws Exception{
		int result = noticeService.setDelete(noticeDTO);
		
		return "redirect:./list";
	}
	
	//filedel
	@GetMapping("fileDelete")
	public String setFileDelete(NoticeFileDTO noticeFileDTO, Model model, HttpSession session)throws Exception{
		int result = noticeService.setFileDelete(noticeFileDTO,session);
		model.addAttribute("result",result);
		return "commons/ajaxResult";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
