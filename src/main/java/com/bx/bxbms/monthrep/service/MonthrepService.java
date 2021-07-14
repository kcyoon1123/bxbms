package com.bx.bxbms.monthrep.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bx.bxbms.monthrep.dao.MonthrepMapper;
import com.bx.bxbms.monthrep.vo.MonthrepVO;
import com.bx.bxbms.util.FileUtil;
import com.bx.bxbms.weekrep.vo.WeekrepVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("monthrepService")
public class MonthrepService extends EgovAbstractServiceImpl{
	
	@Resource(name="monthrepMapper")
	private MonthrepMapper monthrepMapper;
	
	@Resource(name="fileUtil")
	private FileUtil fileUtil;
	
	
	// 월간보고 목록
	public List<MonthrepVO> monthrepList(MonthrepVO vo) throws Exception {
		return monthrepMapper.monthrepList(vo);
	} 
	// 월간보고 등록
	public void insertMonthrep(MonthrepVO vo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		vo.setRegDt(date);
		monthrepMapper.insertMonthrep(vo);
	}
	// 월간보고 수정
	public void updateMonthrep(MonthrepVO vo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		vo.setMdfcnDt(date);
		monthrepMapper.updateMonthrep(vo);
	}

	
	// 월간보고 다중 파일 등록
	public void insertMonthrepFile(String monthrepId, List<MultipartFile> fileList) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		List<MonthrepVO> fileVO = fileUtil.parseInsertFilesInfo(monthrepId, fileList);
		for(MonthrepVO vo : fileVO){
			vo.setRegDt(date);
			monthrepMapper.insertMonthrepFile(vo);
		}	
		
	}
	
	// 주간보고 파일 여부
    public int monthFileSt(String monthrepId) throws Exception {
		return monthrepMapper.monthFileSt(monthrepId);
	}
    
    // 월간보고 수정
    public void modifyMonthrepFile(String monthrepId, List<MultipartFile> fileList) throws Exception {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date now = new Date();
    	String date = formatter.format(now);
    	List<MonthrepVO> fileVO = fileUtil.parseInsertFilesInfo(monthrepId, fileList);
    	for(MonthrepVO vo : fileVO){
    		vo.setMdfcnDt(date);
    		monthrepMapper.modifyMonthrepFile(vo);
    	}
    }
    
    // 월간보고 파일 삭제
    public void deleteMonthFile(String monthrepId) throws Exception {
    		monthrepMapper.deleteMonthFile(monthrepId);
    }
    
    // 월간보고 목록
 	public List<MonthrepVO> monthFileList(String monthrepId) throws Exception {
 		return monthrepMapper.monthFileList(monthrepId);
 	} 
 	
 	public void filedownload(int atchFileId, HttpServletResponse response, HttpServletRequest request) throws Exception {
 		WeekrepVO vo = monthrepMapper.selectAtchFile(atchFileId);
		fileUtil.filedownload(vo, response, request);
	}
 	
}
