package com.bx.bxbms.weekrep.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bx.bxbms.monthrep.vo.MonthrepVO;
import com.bx.bxbms.util.WeekCal;
import com.bx.bxbms.weekrep.service.WeekrepService;
import com.bx.bxbms.weekrep.vo.WeekrepVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class WeekrepController {
	
	private static final Logger logger = LoggerFactory.getLogger(WeekrepController.class);

	@Resource(name = "weekrepService")
	private WeekrepService weekrepService;
	
	@Resource(name="weekCal")
	private WeekCal weekCal;
	
		// 주간보고 페이지
		@RequestMapping(value = "/weekrep/weekrepList.do", method = RequestMethod.GET)
		public String weekrepList(@ModelAttribute("weekrepVO") WeekrepVO vo, Model model) throws Exception {
			logger.info("get weekrepList");
			ModelAndView mv = new ModelAndView("jsonView");
			
			SimpleDateFormat formatMonth = new SimpleDateFormat("yyyyMM");
			Calendar now = Calendar.getInstance();
			String today = formatMonth.format(now.getTime());
			
			String todayMon = weekCal.getCurMonday();
			String todaySun = weekCal.getCurSunday();
			String todayWeekNum = weekCal.getWeek();
			model.addAttribute("today", today+todayWeekNum);
			String year="";
			String month="";
			String weekNum="";
			String pstgBgngDt="";
			String pstgEndDt="";
			// 입력받은 날짜가 없을때 오늘 기준으로
			// repdate => 2021071 >> 2021년 7월 첫째주
			if (vo.getRepdate() == null) {
				// 오늘 기준 이번주 월요일
				vo.setPstgBgngDt(todayMon);
				vo.setPstgEndDt(todaySun);
				
			    year = today.substring(0,4);
				month = today.substring(4,6);
				weekNum = weekCal.getWeek();
				
				model.addAttribute("pstgBgngDt", todayMon);
				model.addAttribute("pstgEndDt", todaySun);
				model.addAttribute("dayFlag", "Y");
			} else {
				
				// 입력받은 날짜로 시작 날과 마지막날 생성
				System.err.println("getRepdategetRepdategetRepdategetRepdate"+vo.getRepdate());
				pstgBgngDt = weekCal.getMonday(vo.getRepdate());
				pstgEndDt = weekCal.getSunday(vo.getRepdate());
				
				year = vo.getRepdate().substring(0,4);
				month = vo.getRepdate().substring(4,6);
				weekNum = vo.getRepdate().substring(6);
				
				System.err.println("pstgBgngDtpstgBgngDtpstgBgngDt"+pstgBgngDt);
				System.err.println("todayMontodayMontodayMontodayMon"+todayMon);
				System.err.println("pstgEndDtpstgEndDtpstgEndDt"+pstgEndDt);
				System.err.println("todaySuntodaySuntodaySuntodaySun"+todaySun);
				
				vo.setPstgBgngDt(pstgBgngDt);
				vo.setPstgEndDt(pstgEndDt);
				
				model.addAttribute("comNm", vo.getComNm());
				model.addAttribute("searchType", vo.getSearchType());
				model.addAttribute("searchKeyword", vo.getSearchKeyword());
				model.addAttribute("pstgBgngDt", pstgBgngDt); 
				model.addAttribute("pstgEndDt", pstgEndDt);
				// 이번주 주간보고일때 FLAG
				if(todayMon.equalsIgnoreCase(pstgBgngDt) && todaySun.equalsIgnoreCase(pstgEndDt)) {
					model.addAttribute("dayFlag", "Y");
				}else {
					model.addAttribute("dayFlag", "N");
				}
			}
			
			model.addAttribute("year", year);
			model.addAttribute("month", month);
			model.addAttribute("weekNum", weekNum);
			
			List<WeekrepVO> weekrepList = null;
			weekrepList = weekrepService.weekrepList(vo);
			model.addAttribute("weekrepList", weekrepList);
			// 개인 주간보고 별 댓글 리스트 weekrepId로 
			String weekrepId = "";
			for(WeekrepVO rep : weekrepList) {
				System.err.println("weekrepIdweekrepIdweekrepIdweekrepIdweekrepIdweekrepId"+rep.getWeekrepId());
				weekrepId = rep.getWeekrepId();
				List<WeekrepVO> replyList = weekrepService.replyList(rep.getWeekrepId());
				model.addAttribute("replyList", replyList);
				System.err.println("dsfsdfsdf"+ "replyList"+weekrepId);
			}
			// JSON Array에 담기(Excel 다운로드위해서)
			 JSONArray jArray = new JSONArray();//배열이 필요할때
			 for (int i = 0; i < weekrepList.size(); i++)//배열
			 {
			 JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
			 sObject.put("이름", weekrepList.get(i).getUserNm());
			 sObject.put("소속", weekrepList.get(i).getComNm());
			 sObject.put("직급", weekrepList.get(i).getPosition());
			 
			 
			 if(weekrepList.get(i).getWeekrepCont() == null) {
				 sObject.put("금주업무", "미작성");
			 }else {
				 sObject.put("금주업무", weekrepList.get(i).getWeekrepCont());
			 }
			 
			 if(weekrepList.get(i).getWeekrepCont() == null) {
				 sObject.put("차주업무계획", "미작성");
			 }else {
				 sObject.put("차주업무계획", weekrepList.get(i).getNextCont());
			 }
			 
			 if(weekrepList.get(i).getUniqueness() == null) {
				 sObject.put("특이사항", "미작성");
			 }else {
				 sObject.put("특이사항", weekrepList.get(i).getUniqueness());
			 }
			 jArray.add(sObject);
			 }
			 model.addAttribute("jArray", jArray);
			System.err.println("jArrayjArrayjArrayjArray"+jArray);
			return "weekrep/weekrepList";
		}
		
		
		// 주간보고 등록 수정
		@RequestMapping(value = "/weekrep/insertWeekrep.ajax", method = RequestMethod.POST)
		public ModelAndView insertWeekrep(@ModelAttribute("weekrepVO") WeekrepVO vo, HttpServletRequest request, MultipartHttpServletRequest mre)
				throws Exception {
			logger.info("post insertWeekrep");
			MultipartFile uploadFile = mre.getFile("file"); 
			request.setCharacterEncoding("UTF-8");
			System.out.println("controllerinsertWeekrepcontrollerinsertWeekrep" + vo.getNextCont());
			ModelAndView mv = new ModelAndView("jsonView");
			String pstgBgngDt = weekCal.getMonday(vo.getRepdate());
			String pstgEndDt = weekCal.getSunday(vo.getRepdate());
			vo.setPstgBgngDt(pstgBgngDt);
			vo.setPstgEndDt(pstgEndDt);
			if (weekrepService.weekSt(vo) == 0) {
				// 등록 X >> insert
				weekrepService.insertWeekrep(vo);
				if(uploadFile != null) {
					weekrepService.insertWeekrepFile(vo.getWeekrepId(), uploadFile);
				}
				mv.addObject("formState", "successInsert");
			} else {
				// 등록 O >> update
				weekrepService.updateWeekrep(vo);
				if(uploadFile != null) {
					if(weekrepService.weekFileSt(vo.getWeekrepId()) == 0) {
						weekrepService.insertWeekrepFile(vo.getWeekrepId(), uploadFile);
					}else if(weekrepService.weekFileSt(vo.getWeekrepId()) != 0) {
						weekrepService.modifyWeekrepFile(vo.getWeekrepId(), uploadFile);
					}
				}
				mv.addObject("formState", "successUpate");
			}
			return mv;
		}
		
		// 주간보고 파일 등록
		@RequestMapping(value = "/weekrep/insertWeekrepFile.ajax", method = RequestMethod.POST)
		public ModelAndView insertWeekrepFile(@ModelAttribute("weekrepVO") WeekrepVO vo, MultipartHttpServletRequest mre, HttpServletRequest request) throws Exception {
			logger.info("post insertWeekrepFile");
			String weekrepId = request.getParameter("weekrepId"); 
	        MultipartFile uploadFile = mre.getFile("file"); 
	        System.out.println("uploadFile.getOriginalFilename()uploadFile.getOriginalFilename()"+uploadFile.getOriginalFilename());
			ModelAndView mv = new ModelAndView("jsonView");
			weekrepService.insertWeekrepFile(weekrepId, uploadFile);
			mv.addObject("formState", "successInsert");
			return mv;
		}
		
		// 주간보고 파일 다운로드
		@RequestMapping(value = "/weekrep/downloadWeekrepFile.do", method = RequestMethod.GET)
		public ModelAndView downloadWeekrepFile(@RequestParam("atchFileId")int atchFileId, HttpServletResponse response, HttpServletRequest request) throws Exception {
			logger.info("post downloadWeekrepFile");
			ModelAndView mv = new ModelAndView("jsonView");
			weekrepService.filedownload(atchFileId, response, request);
			mv.addObject("formState", "successInsert");
			return mv;
		}
		
		// 주간보고 댓글 등록
		@RequestMapping(value = "/weekrep/insertReply.ajax", method = RequestMethod.POST)
		public ModelAndView insertReply(@ModelAttribute("weekrepVO") WeekrepVO vo, HttpServletRequest request) throws Exception {
			logger.info("post insertReply");
			ModelAndView mv = new ModelAndView("jsonView");
			weekrepService.insertReply(vo);
			mv.addObject("formState", "successInsert");
			return mv;
		}

		// 주간보고 댓글목록 조회
		@RequestMapping(value = "/weekrep/weekrepDetail.do", method = RequestMethod.GET)
		public String replyList(@RequestParam("weekrepId")String weekrepId, Model model) throws Exception {
			logger.info("get replyList");
			ModelAndView mv = new ModelAndView("jsonView");
			List<WeekrepVO> replyList = weekrepService.replyList(weekrepId);
			WeekrepVO weekrep = weekrepService.repDetail(weekrepId);
			model.addAttribute("replyList", replyList);
			model.addAttribute("weekrep", weekrep);
			mv.addObject("formState", "success");
			mv.addObject("replyList", replyList);
			mv.addObject("weekrep", weekrep);
			return "/weekrep/weekrepDetail";
		}
		// 주간보고 댓글 수정
		@RequestMapping(value = "/weekrep/modifyReply.ajax", method = RequestMethod.POST)
		public ModelAndView modifyReply(@ModelAttribute("weekrepVO") WeekrepVO vo, HttpServletRequest request)
				throws Exception {
			logger.info("post modifyReply");
			ModelAndView mv = new ModelAndView("jsonView");
				weekrepService.modifyReply(vo);
				mv.addObject("formState", "successUpate");
			return mv;
		}
		// 주간보고 댓글 삭제
		@RequestMapping(value = "/weekrep/repDelete.ajax", method = RequestMethod.POST)
		public ModelAndView repDelete(@ModelAttribute("weekrepVO") WeekrepVO vo, HttpServletRequest request)
				throws Exception {
			logger.info("post repDelete");
			ModelAndView mv = new ModelAndView("jsonView");
			weekrepService.repDelete(vo);
			mv.addObject("formState", "successDelete");
			return mv;
		}
		
		// 주간보고 댓글 삭제
		@RequestMapping(value = "/weekrep/fileDelete.ajax", method = RequestMethod.POST)
		public ModelAndView fileDelete(@ModelAttribute("weekrepVO") WeekrepVO vo)
				throws Exception {
			logger.info("post fileDelete");
			ModelAndView mv = new ModelAndView("jsonView");
			weekrepService.fileDelete(vo);
			mv.addObject("formState", "successDelete");
			return mv;
		}
		
		
		// 주간보고 댓글 목록
		@RequestMapping(value = "/weekrep/replyList.ajax", method = RequestMethod.POST)
		public ModelAndView replyList(@RequestParam("weekrepId") String weekrepId, HttpServletRequest request) throws Exception {
			logger.info("post replyList");
			ModelAndView mv = new ModelAndView("jsonView");
			System.err.println("weekrepIdweekrepIdweekrepIdweekrepIdweekrepIdweekrepId"+weekrepId);
			List<WeekrepVO> replyList = weekrepService.replyList(weekrepId);
			mv.addObject("replyList", replyList);
			return mv;
		}
	    
}
