package com.bx.bxbms.monthrep.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bx.bxbms.monthrep.service.MonthrepService;
import com.bx.bxbms.monthrep.vo.MonthrepVO;

@Controller
public class MonthrepController {
	
	@Resource(name="monthrepService")
	private MonthrepService monthrepService;


	// 월간보고 페이지
	@RequestMapping(value = "/monthrep/monthrepList.do", method = RequestMethod.GET)
	public String monthrepList(@ModelAttribute("monthrepVO") MonthrepVO vo, Model model) throws Exception {
		
		SimpleDateFormat formatterYM = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatterY = new SimpleDateFormat("yyyy");
		SimpleDateFormat formatterMM = new SimpleDateFormat("MM");
		Date now = new Date();
		String repDate = formatterYM.format(now);
		String year = "";
		String month = "";
		System.err.println("todaytodaytodaytoday"+repDate);
		
		// 검색시 해당년 월 repDate 다시 화면에 뿌릴때
		// 검색시 년도 월 선택안하면 이번달로 set
		if(vo.getRepDate() == null || vo.getRepDate().equals("")) {
			vo.setRepDate(repDate);
			 year = formatterY.format(now);
			 month = formatterMM.format(now);
			 model.addAttribute("dayFlag", "Y");
		}else {
			year = vo.getRepDate().substring(0, 4);
			month =  vo.getRepDate().substring(5);
			String selectDate=vo.getRepDate();
			// 이번달 월간보고일때 FLAG
			System.err.println("ASDFASDFSDFSDrepDate"+repDate);
			System.err.println("ASDFASDFSDFSDFselectDate"+selectDate);
			
			if(repDate.equalsIgnoreCase(selectDate)) {
				model.addAttribute("dayFlag", "Y");
			}else {
				model.addAttribute("dayFlag", "N");
			}
		}
		
		
		
		List<MonthrepVO> monthrepList = monthrepService.monthrepList(vo);
		
		model.addAttribute("monthrepList", monthrepList);
		model.addAttribute("searchType", vo.getSearchType());
		model.addAttribute("searchKeyword", vo.getSearchKeyword());
		model.addAttribute("comNm", vo.getComNm());
		model.addAttribute("repDate", repDate);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		
		
		
		return "monthrep/monthrepList";
	}
	
	
	
	// 월간보고 등록 수정
	@RequestMapping(value = "/monthrep/insertMonthrep.ajax", method = RequestMethod.POST)
	public ModelAndView insertMonthrep(@ModelAttribute("monthrepVO") MonthrepVO vo, HttpServletRequest request, MultipartHttpServletRequest mre)
			throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		// 파일가져오기
		List<MultipartFile> fileList = mre.getFiles("file");
		//System.err.println("fileListfileList"+fileList.get(1));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		Date now = new Date();
		String repDate = formatter.format(now);
		System.err.println("asdfsdfsdfsdf"+vo.getUniqueCont());
		// 등록시 MonthrepId
		String monthrepId = vo.getUserId()+repDate;
		System.err.println("vo.getDayrepId()vo.getDayrepId()vo.getDayrepId()"+vo.getMonthrepId());
		System.err.println("getUserIdgetUserIdgetUserIdgetUserId"+vo.getUserId());
		// vo.getMonthrepId()가 없으면 등록
		if(vo.getMonthrepId() == null || vo.getMonthrepId().equals("")) {
			vo.setRepDate(repDate);
			vo.setMonthrepId(monthrepId);
			monthrepService.insertMonthrep(vo);
			if(fileList != null) {
				monthrepService.insertMonthrepFile(vo.getMonthrepId(), fileList);
			}
			mv.addObject("formState", "successInsert");
		}else{
		// DayrepId가 있으면 수정 
			
			System.err.println("monthrepService.monthFileSt(vo.getMonthrepId()) "+fileList.size());
			if(fileList.size() == 0) {
				monthrepService.updateMonthrep(vo);
			}else {
				monthrepService.updateMonthrep(vo);
				if(monthrepService.monthFileSt(vo.getMonthrepId()) == 0) {
					monthrepService.insertMonthrepFile(vo.getMonthrepId(), fileList);
				}else if(monthrepService.monthFileSt(vo.getMonthrepId()) != 0) {
					// 기존 파일이 있을때 삭제하고 인서트
					monthrepService.deleteMonthFile(vo.getMonthrepId());
					monthrepService.insertMonthrepFile(vo.getMonthrepId(), fileList);
				}
				
			}
			
			mv.addObject("formState", "successUpate");
		}
		
		
		return mv;
	}
	
	// 월간보고 등록 수정
	@RequestMapping(value = "/monthrep/deleteMonthFile.ajax", method = RequestMethod.POST)
	public ModelAndView deleteMonthFile(@RequestParam("monthrepId") String monthrepId)
			throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		monthrepService.deleteMonthFile(monthrepId);
		mv.addObject("formState", "success");
		
		return mv;
	}
	
	// 월간보고 첨부파일 목록
	@RequestMapping(value = "/monthrep/monthFileList.ajax", method = RequestMethod.POST)
	public ModelAndView monthFileList(@RequestParam("monthrepId") String monthrepId)
			throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		List<MonthrepVO> fileList = monthrepService.monthFileList(monthrepId);
		System.err.println(""+fileList.get(0).getAtchFileId());
		mv.addObject("fileList", fileList);
		mv.addObject("formState", "success");
		
		return mv;
	}
	// 월간보고 파일 다운로드
		@RequestMapping(value = "/monthrep/downloadMonthrepFile.do", method = RequestMethod.GET)
		public ModelAndView downloadWeekrepFile(@RequestParam("atchFileId")int atchFileId, HttpServletResponse response, HttpServletRequest request) throws Exception {
			ModelAndView mv = new ModelAndView("jsonView");
			monthrepService.filedownload(atchFileId, response, request);
			mv.addObject("formState", "successInsert");
			return mv;
		}
	
	
}
