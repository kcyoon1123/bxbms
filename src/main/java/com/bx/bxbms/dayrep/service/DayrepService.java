package com.bx.bxbms.dayrep.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bx.bxbms.dayrep.dao.DayrepMapper;
import com.bx.bxbms.dayrep.vo.DayrepVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("dayrepService")
public class DayrepService extends EgovAbstractServiceImpl{
	
	@Resource(name="dayrepMapper")
	private DayrepMapper dayrepMapper;
	
	// 일간보고 목록
	public List<DayrepVO> dayrepList(DayrepVO vo) throws Exception {
		return dayrepMapper.dayrepList(vo);
	} 
	// 일간보고 등록
	public void insertDayrep(DayrepVO vo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		vo.setRegDt(date);
		dayrepMapper.insertDayrep(vo);
	}
	// 일간보고 수정
	public void updateDayrep(DayrepVO vo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		vo.setMdfcnDt(date);
		dayrepMapper.updateDayrep(vo);
	}


}
