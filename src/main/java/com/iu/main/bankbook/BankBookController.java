package com.iu.main.bankbook;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iu.main.util.Pager;

@Controller
@RequestMapping("/bankbook/*")
public class BankBookController {
	@Autowired
	private BankBookService bankBookService;
	
	@GetMapping("fileDelete")
	public String setFileDelete(BankBookFileDTO bankBookFileDTO, Model model, HttpSession session) throws Exception{
		
		// db
		int result = bankBookService.setFileDelete(bankBookFileDTO, session);
		model.addAttribute("result", result);
		return "commons/ajaxResult";
	}
	
	@RequestMapping(value="list", method = RequestMethod.GET)
	public String getList(Pager pager, Model model) throws Exception{
		List<BankBookDTO> ar =bankBookService.getList(pager);
		model.addAttribute("list", ar);
		model.addAttribute("pager", pager);
	
		return "bankbook/list";
	}
	
	@RequestMapping(value="detail", method = RequestMethod.GET) //method 생략하면 기본값은 GET
	public ModelAndView getDetail(BankBookDTO bankBookDTO, ModelAndView mv) throws Exception{

		bankBookDTO=bankBookService.getDetail(bankBookDTO);
		System.out.println(bankBookDTO.getBookName());
		mv.addObject("dto", bankBookDTO);
		mv.setViewName("bankbook/detail");
		System.out.println("detail");
		System.out.println(bankBookDTO.getFileDTOs());
		System.out.println();

		return mv;
	}
	
	@RequestMapping(value="add", method = RequestMethod.GET)
	public void setAdd() throws Exception{
	//add 폼 호출	
	}
	
	//db insert
	@RequestMapping(value="add", method = RequestMethod.POST)
	public String setAdd(BankBookDTO bankBookDTO, MultipartFile [] photos, HttpSession session) throws Exception{
		// DTO를 매개변수로 넘기기 위해서는 DTO의 setter의 이름과 파라미터 이름이 동일해야 한다.
		int result = bankBookService.setAdd(bankBookDTO, photos, session);
		
		return "redirect:./list"; //리다이렉트 할 때는 redirect 하는 것을 써주어야 한다.
				                 //모델앤드뷰는 url 위치로 가면서 삭제됨 
	}
	
	@RequestMapping(value="delete", method = RequestMethod.GET)
	public String setDelete(Long bookNum)throws Exception{
		//파라미터이름이라 매개변수이름을 일치시켜줘야함
		int result = bankBookService.setDelete(bookNum);
		
		return "redirect:./list";
		
	}
	
	//@수정form
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public void setUpdate(BankBookDTO bankBookDTO, Model model)throws Exception{
		bankBookDTO = bankBookService.getDetail(bankBookDTO);
		model.addAttribute("dto", bankBookDTO);
		
	}
	
	//update
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String setUpdate(BankBookDTO bankBookDTO, MultipartFile[] photos, HttpSession session)throws Exception{
		int result = bankBookService.setUpdate(bankBookDTO, photos, session);
		return "redirect:./detail?bookNum="+bankBookDTO.getBookNum();
		
	}
	
}
