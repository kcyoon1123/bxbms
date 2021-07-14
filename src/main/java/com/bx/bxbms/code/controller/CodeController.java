package com.bx.bxbms.code.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bx.bxbms.code.service.CodeService;
import com.bx.bxbms.code.vo.CodeVO;
import com.bx.bxbms.dayrep.vo.DayrepVO;
import com.bx.bxbms.member.vo.MemberVO;

@Controller
public class CodeController {

	@Resource(name="codeService")
	private CodeService codeService;
	
	// 코드관리 코드목록페이지
	@RequestMapping(value = "/admin/code/codeList.do", method = RequestMethod.GET)
	public String codeGrpList(@ModelAttribute("codeVO") CodeVO vo, Model model) throws Exception {
		List<CodeVO> codeGrpList = codeService.codeGrpList(vo);
		model.addAttribute("codeGrpList", codeGrpList);
		return "code/codeList";
	}
	
	// 코드관리 코드그룹 등록페이지
	@RequestMapping(value = "/admin/code/codeView.do", method = RequestMethod.GET)
	public String codeView(@RequestParam("codeGrpId")String codeGrpId) throws Exception {
		CodeVO codeGrpDetail = codeService.codeGrpDetail(codeGrpId);
		ModelAndView mv = new ModelAndView("jsonView");
		mv.addObject("codeGrpDetail", codeGrpDetail);
		return "code/codeView";
	}
	
	// 코드관리 코드 등록/상세 페이지
	@RequestMapping(value = "/admin/code/insertCodeView.do", method = RequestMethod.GET)
	public String insertCodeView() throws Exception {
		return "code/insertCodeView";
	}
	
	// 코드관리 코드그룹 등록
	@RequestMapping(value = "/admin/code/insertCodeGrp.ajax", method = RequestMethod.POST)
	public ModelAndView insertCodeGrp(@ModelAttribute("codeVO") CodeVO vo, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		// 등록아이디 세션에서 가져오기
		HttpSession session = request.getSession();
        MemberVO login = (MemberVO) session.getAttribute("session");
        System.err.println("getUserIdgetUserIdgetUserId"+login.getUserId());
        System.err.println("getUserIdgetUserIdgetUserId"+login.getMemId());
        vo.setRegId(login.getUserId());
		
		codeService.insertCodeGrp(vo);
		mv.addObject("formState","success");
		return mv;
	}
}
