package com.bx.bxbms.dayrep.dao;

import java.util.List;

import com.bx.bxbms.dayrep.vo.DayrepVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("dayrepMapper")
public interface DayrepMapper {

	// 일간보고 목록
	List<DayrepVO> dayrepList(DayrepVO vo) throws Exception;
	
	// 일간보고 등록
	void insertDayrep(DayrepVO vo) throws Exception;
	
	// 일간보고 수정
	void updateDayrep(DayrepVO vo) throws Exception;
	
}
