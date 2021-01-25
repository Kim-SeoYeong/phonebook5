package com.javaex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@Controller
@RequestMapping(value="/phone")
public class PhoneController {

	//필드
	//시작할때 자동으로 주소를 주입시켜줄때
	@Autowired
	private PhoneDao phoneDao;	//new phoneDao();가 생긴다고 생각하면됨. 
	//생성자
	//메소드 g/s
	
	/***** *메소드 일반* 메소드마다  기능 1개씩  --> 기능마다 url 부여 *****/
	
	//리스트
	@RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("list");
		
		//dao를 통해 리스트를 가져온다.
		List<PersonVo> personList = phoneDao.getPersonList();
		//System.out.println(personList.toString());
		//model --> data를 보내는 방법 --> 담아 놓으면 된다.
		//이전 request.setAttribure("pList", personList)를 표현
		model.addAttribute("pList", personList);

		//return "/WEB-INF/views/list.jsp";
		return "list";
	}
	
	//등록폼
	@RequestMapping(value="/writeForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String writeForm() {
		System.out.println("writeForm");
		return "writeForm";	//포워드
	}
	
	//http://localhost:8088/phonebook5/phone/write?name=김서영&hp=010-1111-1111&company=02-1111-1111
	//등록
	@RequestMapping(value="/write", method = {RequestMethod.GET, RequestMethod.POST})
	public String write(@RequestParam("name") String name, @RequestParam("hp") String hp, @RequestParam("company") String company) {
		//request.getParameter("name")을 @RequestParam("name")으로 표현하고 담을 변수를 옆에 적어줌.
		System.out.println("write");
		
		PersonVo personVo = new PersonVo(name, hp, company);

		phoneDao.personInsert(personVo);
		
		return "redirect:/phone/list";	//인터넷 주소를 다시 숨어서 보내는 것  redirect 해주는부분.
	}
	
	//삭제	--> delete --> @RequestMapping 약식 표현
	@RequestMapping(value="/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("personId") int personId) {
		System.out.println("delete");
		
		//PhoneDao --> personDelete();
		phoneDao.personDelete(personId);
		
		return "redirect:/phone/list";
	}
	
	//삭제	--> delete	--> 
	@RequestMapping(value="/delete2/{personId}", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete2(@PathVariable("personId") int personId) {	//주소에서 어떤 값(personId)을 꺼내서 personId에 담아줘.
		System.out.println("delete");
		
		//PhoneDao --> personDelete();
		phoneDao.personDelete(personId);
		
		return "redirect:/phone/list";
	}
	
	//수정폼	--> modifyForm
	@RequestMapping(value="/modifyForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(Model model, @RequestParam("id") int personId) {
		System.out.println("modifyForm");

		PersonVo personVo = phoneDao.getPerson(personId);
		
		//personVo를 보내기 위해 model에 담아줌.
		model.addAttribute("pvo", personVo);
		
		return "modifyForm";	//포워드
	}
	
	//수정폼2 Map을 이용한 방식
	@RequestMapping(value="/modifyForm2", method = {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(@RequestParam("id") int personId, Model model) {
		System.out.println("modifyForm2");
		System.out.println(personId);
		
		Map<String, Object> personMap = phoneDao.getPerson2(personId);
		
		//personMap을 보내기 위해
		model.addAttribute("pMap", personMap);
		
		return "modifyForm2";
	}
	
	//수정	--> modify	--> @ModelAttribute를 이용한것.
	//modify--> @RequestParam을 사용했을 경우
	@RequestMapping(value="/modify", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute PersonVo personVo) {	//@ModelAttribure 자료형 모델명
		System.out.println("modify");
		
		//이게 필요가 없어짐.
		//PersonVo personVo = new PersonVo(personId, name, hp, company);
		//modifyForm의 코드id 부분 name을 personVo 변수이름과 맞추어야함..name=어쩌고 hp=010 company=02222 personId=3 이런식으로 넘거야함.
		System.out.println(personVo.toString());
		
		//phoneDao --> personUpdate();
		phoneDao.personUpdate(personVo);
		
		return "redirect:/phone/list";	//redirect 해줌.
	}
	
	//수정2 --> modify2
	@RequestMapping(value="/modify2", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify2(@RequestParam("personId") int personId,
						  @RequestParam("name") String name,
						  @RequestParam("hp") String hp,
						  @RequestParam("company") String company) {
		System.out.println("modify2");
		System.out.println(personId + "," + name + "," + hp + "," + company);
		
		//phoneDao --> personUpdate2()
		phoneDao.personUpdate2(personId, name, hp, company);
		
		return "redirect:/phone/list";
	}
	/*
	//수정	--> modify2
	//modify2--> @RequestParam을 사용했을 경우
	@RequestMapping(value="/modify2", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify2(@RequestParam("name") String name, @RequestParam("hp") String hp, 
						@RequestParam("company") String company, @RequestParam("personId") int personId) {
		System.out.println("modify");
		
		PersonVo personVo = new PersonVo(personId, name, hp, company);
		
		//phoneDao --> personUpdate();
		phoneDao.personUpdate(personVo);
		
		return "redirect:/phone/list";	//redirect 해줌.
	}
	*/

}