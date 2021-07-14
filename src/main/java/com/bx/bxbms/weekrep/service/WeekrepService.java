package com.bx.bxbms.weekrep.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bx.bxbms.util.FileUtil;
import com.bx.bxbms.weekrep.dao.WeekrepMapper;
import com.bx.bxbms.weekrep.vo.WeekrepVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("weekrepService")
public class WeekrepService extends EgovAbstractServiceImpl{
	
	@Resource(name="weekrepMapper")
	private WeekrepMapper weekrepMapper;
	
	@Resource(name="fileUtil")
	private FileUtil fileUtil;
	
	// 주간보고 목록
	public List<WeekrepVO> weekrepList(WeekrepVO vo) throws Exception {
		return weekrepMapper.weekrepList(vo);
	} 
	// 주간보고 파일 여부
	public int weekSt(WeekrepVO vo) throws Exception {
		return weekrepMapper.weekSt(vo);
	}

	public void insertWeekrep(WeekrepVO vo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		vo.setRegDt(date);
		weekrepMapper.insertWeekrep(vo);
	}
	public void updateWeekrep(WeekrepVO vo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		vo.setMdfcnDt(date);
		weekrepMapper.updateWeekrep(vo);
	}

	public void insertWeekrepFile(String weekrepId, MultipartFile uploadFile) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		WeekrepVO fileVO = fileUtil.parseInsertFileInfo(weekrepId, uploadFile);
		fileVO.setRegDt(date);
		weekrepMapper.insertWeekrepFile(fileVO);
		
	}


	public void filedownload(int atchFileId, HttpServletResponse response, HttpServletRequest request) throws Exception {
		WeekrepVO vo = weekrepMapper.selectAtchFile(atchFileId);
		fileUtil.filedownload(vo, response, request);
	}

	public void insertReply(WeekrepVO vo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		vo.setRegDt(date);		
		weekrepMapper.insertReply(vo);
	}

	public List<WeekrepVO> replyList(String weekrepId) throws Exception {
		return weekrepMapper.replyList(weekrepId);
	}

	public WeekrepVO repDetail(String weekrepId) throws Exception {
		
		return weekrepMapper.repDetail(weekrepId);
	}

	public void modifyReply(WeekrepVO vo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		vo.setMdfcnDt(date);
		weekrepMapper.modifyReply(vo);
	}

	public void repDelete(WeekrepVO vo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		vo.setMdfcnDt(date);
		weekrepMapper.repDelete(vo);
		
	}

	public void modifyWeekrepFile(String weekrepId, MultipartFile uploadFile) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		WeekrepVO fileVO = fileUtil.parseInsertFileInfo(weekrepId, uploadFile);
		fileVO.setMdfcnDt(date);
		weekrepMapper.modifyWeekrepFile(fileVO);
		
	}

	public void fileDelete(WeekrepVO vo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		vo.setMdfcnDt(date);
		weekrepMapper.fileDelete(vo);
		
	}

	public int weekFileSt(String weekrepId) throws Exception {
		return weekrepMapper.weekFileSt(weekrepId);
	}

}
