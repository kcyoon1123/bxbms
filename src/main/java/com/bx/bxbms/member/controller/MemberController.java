package com.bx.bxbms.member.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bx.bxbms.member.service.MemberService;
import com.bx.bxbms.member.vo.MemberVO;

@Controller
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Resource(name = "memberService")
	private MemberService memberService;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	// 회원가입 페이지
		@RequestMapping(value = "/member/memberSignup.do", method = RequestMethod.GET)
		public String memberSignup( HttpSession session) throws Exception {
			logger.info("get register");
			//로그인된 상태에서 회원가입 페이지 접속 시 자동 로그아웃
			if(session.getAttribute("session") != null) {
				session.setAttribute("session", null);
				session.invalidate();
			}
			return "/member/memberSignup";
		}

		// 회원가입
		@RequestMapping(value = "/member/memberSignup.ajax", method = RequestMethod.POST)
		public ModelAndView postRegister(@ModelAttribute("memberVO") MemberVO vo) throws Exception {
			logger.info("post resister");
			
			ModelAndView mv = new ModelAndView("jsonView");
			String inputPass = vo.getUserPass();
			String pass = encoder.encode(inputPass);
			vo.setUserPass(pass);
			memberService.memberSignUp(vo);

			mv.addObject("formState", "success");
			return mv;

		}
		
		// 회원가입 아이디 중복체크
		@RequestMapping(value = "/member/idCheck.ajax", method = RequestMethod.POST)
		public ModelAndView idCheck(@ModelAttribute("memberVO") MemberVO vo) throws Exception {
			logger.info("post idCheck");
			
			ModelAndView mv = new ModelAndView("jsonView");
			int chk = memberService.idCheck(vo);
			if(chk == 0) {
				// 중복된 아이디 없음
				mv.addObject("idChk", "pass");
			}else if(chk != 0) {
				// 중복된 아이디 있음
				mv.addObject("idChk", "fail");
			}
			
			return mv;
			
		}

		// 로그인 
		@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
		public ModelAndView login(@ModelAttribute("memberVO") MemberVO vo, HttpServletRequest request, RedirectAttributes attr, Model model) throws NullPointerException {
			logger.info("post login");
			HttpSession session = request.getSession();
			ModelAndView mv = new ModelAndView("jsonView");
			try {
				MemberVO login = memberService.login(vo);
				boolean passMatch = encoder.matches(vo.getUserPass(), login.getUserPass());
				System.out.println("passMatchpassMatchpassMatchpassM"+passMatch);
				 if(login != null && passMatch) {
					 // 로그인 성공
					 session.setAttribute("session", login);
					 mv.setViewName("redirect:/weekrep/weekrepList.do");
				 } else{
					 // 로그인 실패
				  session.setAttribute("session", null);  
				  mv.setViewName("redirect:/");
				  mv.addObject("msg", "fali");
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			return mv;

		}

		// 로그아웃
		@RequestMapping(value = "/member/logout.do", method = RequestMethod.GET)
		public String logout(HttpSession session) throws Exception {
			logger.info("get logout");

			session.invalidate();

			return "redirect:/";
		}

		// 비밀번호 확인 페이지
		@RequestMapping(value = "/myPage/myPage.do", method = RequestMethod.GET)
		public String passwdChk() throws Exception {
			logger.info("get passwdChk");
			return "/member/passwdChk";
			
		}

		// 비밀번호 확인
		@RequestMapping(value = "/myPage/passwdChk.ajax", method = RequestMethod.POST)
		public ModelAndView passwdChk(@ModelAttribute("memberVO")MemberVO vo, HttpSession session) throws Exception {
			logger.info("post passwdChk");
			ModelAndView mv = new ModelAndView("jsonView");
			MemberVO login = memberService.login(vo);
			boolean passMatch = encoder.matches(vo.getUserPass(), login.getUserPass());
			if(passMatch) {
				mv.addObject("formState", "success");
			}else {
				mv.addObject("formState", "fail");
			}
			return mv;
		}
		
		// 회원정보 수정 페이지
		@RequestMapping(value = "/myPage/memberModify.do", method = RequestMethod.GET)
		public String memberModifyGet(@RequestParam("memId")String memId,  Model model) throws Exception {
			logger.info("get memberModify");
			MemberVO mem = memberService.memberInfo(memId);
			model.addAttribute("member",mem);
			return "/member/memberModify";
		}
		
		// 회원정보 수정
		@RequestMapping(value = "/myPage/memberModify.ajax", method = RequestMethod.POST)
		public ModelAndView memberModifyPost(@ModelAttribute("memberVO")MemberVO vo, HttpSession session) throws Exception {
			logger.info("post memberModify");
			System.err.println("vo.getUserNm()vo.getUserNm()vo.getUserNm()"+vo.getUserNm());
			ModelAndView mv = new ModelAndView("jsonView");
			// 정보수정
			memberService.memberModify(vo);
			// 로그아웃
			mv.addObject("formState", "success");
			session.invalidate();
			return mv;
		}
		
		// 회원정보 비밀번호 수정
		@RequestMapping(value = "/myPage/memberPassModify.ajax", method = RequestMethod.POST)
		public ModelAndView memberPassModify(@ModelAttribute("memberVO")MemberVO vo, HttpSession session) throws Exception {
			logger.info("post memberPassModify");
			ModelAndView mv = new ModelAndView("jsonView");
			String changePass = encoder.encode(vo.getUserPass());
			vo.setUserPass(changePass);
			// 정보수정
			memberService.memberPassModify(vo);
			// 로그아웃
			//session.invalidate();
			mv.addObject("formState", "success");
			return mv;
		}
		
		// 관리자 회원 목록
		@RequestMapping(value = "/admin/member/memberList.do", method=RequestMethod.GET)
		public String memberList(Model model) throws Exception {
			List<MemberVO> memberList = null;
			memberList = memberService.memberList();
			model.addAttribute("memberList", memberList);
			return "/member/memberList";
		}
		
		// 관리자 페이지 회원정보 수정
		@RequestMapping(value = "/admin/member/adminmemberModify.ajax", method = RequestMethod.POST)
		public ModelAndView adminmemberModify(@ModelAttribute("memberVO")MemberVO vo) throws Exception {
			logger.info("post adminmemberModify");
			ModelAndView mv = new ModelAndView("jsonView");
			// 정보수정
			memberService.adminmemberModify(vo);
			// 로그아웃
			mv.addObject("formState", "success");
			return mv;
		}
		
		// 관리자 페이지 회원 삭제
		@RequestMapping(value = "/admin/member/deleteMember.ajax", method = RequestMethod.POST)
		public ModelAndView deleteMember(@ModelAttribute("memberVO")MemberVO vo) throws Exception {
			logger.info("post adminmemberModify");
			ModelAndView mv = new ModelAndView("jsonView");
			// 정보수정
			memberService.deleteMember(vo);
			// 로그아웃
			mv.addObject("formState", "success");
			return mv;
		}
	
}
