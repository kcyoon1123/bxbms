package com.bx.bxbms.cmmn.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bx.bxbms.member.vo.MemberVO;

public class EgovInterceptor extends HandlerInterceptorAdapter {
	
	
	@Override
	 public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object obj) throws Exception {
	  
	  HttpSession session = req.getSession();
	  MemberVO member = (MemberVO)session.getAttribute("session");
	  // 로그인 안됐을때
	  if(member == null) {
		  res.sendRedirect("/");
		  return false;
	  }
	  // 로그인O,USER
	  String roleId= member.getRoleId();
	  if(req.getRequestURI().contains("/admin/") && roleId.equals("USER")) {
		  res.sendRedirect("/");
		   return false;
	  }
	  
	  
	  return true;
	 }

}
