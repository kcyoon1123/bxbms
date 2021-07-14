package com.bx.bxbms.dayrep.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bx.bxbms.dayrep.service.DayrepService;
import com.bx.bxbms.dayrep.vo.DayrepVO;

@Controller
public class DayrepController {
	
	@Resource(name="dayrepService")
	private DayrepService dayrepService;

	// 일간보고 페이지
	@RequestMapping(value = "/dayrep/dayrepList.do", method = RequestMethod.GET)
	public String dayrepList(@ModelAttribute("dayrepVO") DayrepVO vo, Model model) throws Exception {
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String today = formatter.format(now);
		// repDate없을때 오늘 날짜로
		if(vo.getRepDate() == null) {
			vo.setRepDate(today);
		}
		// dayFlag
		String dayFlag ="";
		System.err.println("todaytodaytodaytoday"+today);
		System.err.println("vo.getRepDate()vo.getRepDate()"+vo.getRepDate());
		if(vo.getRepDate() == today) {
			dayFlag ="Y";
		}
		List<DayrepVO> dayrepList = dayrepService.dayrepList(vo);
		model.addAttribute("dayrepList", dayrepList);
		model.addAttribute("dayFlag", dayFlag);
		model.addAttribute("comNm", vo.getComNm());
		model.addAttribute("repDate", vo.getRepDate());
		model.addAttribute("searchType", vo.getSearchType());
		model.addAttribute("searchKeyword", vo.getSearchKeyword());
		return "dayrep/dayrepList";
	}
	
	// 주간보고 등록 수정
	@RequestMapping(value = "/dayrep/insertDayrep.ajax", method = RequestMethod.POST)
	public ModelAndView insertdayrep(@ModelAttribute("dayrepVO") DayrepVO vo)
			throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String today = formatter.format(now);
		System.err.println("vo.getDayrepId()vo.getDayrepId()vo.getDayrepId()"+vo.getDayrepId());
		// DayrepId가 없으면 등록
		if(vo.getDayrepId() == null || vo.getDayrepId().equals("")) {
			vo.setRepDate(today);
			dayrepService.insertDayrep(vo);
			mv.addObject("formState", "successInsert");
		}else{
		// DayrepId가 있으면 수정 
			dayrepService.updateDayrep(vo);
			mv.addObject("formState", "successUpate");
		}
		
		
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
}
