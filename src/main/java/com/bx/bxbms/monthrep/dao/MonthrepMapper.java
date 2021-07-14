package com.bx.bxbms.monthrep.dao;

import java.util.List;

import com.bx.bxbms.monthrep.vo.MonthrepVO;
import com.bx.bxbms.weekrep.vo.WeekrepVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("monthrepMapper")
public interface MonthrepMapper {

	// 월간보고 목록
	List<MonthrepVO> monthrepList(MonthrepVO vo) throws Exception;
	
	// 월간보고 등록
	void insertMonthrep(MonthrepVO vo) throws Exception;
	
	// 월간보고 수정
	void updateMonthrep(MonthrepVO vo) throws Exception;
	
    // 월간보고 다중파일 등록
    void insertMonthrepFile(MonthrepVO vo) throws Exception;
    
    // 주간보고 파일 여부
 	int monthFileSt(String monthrepId)throws Exception; 

 	// 월간보고 수정
 	void modifyMonthrepFile(MonthrepVO vo) throws Exception;

 	// 월간보고 파일 삭제
 	void deleteMonthFile(String monthrepId) throws Exception;

 	// 월간보고 파일목록
 	List<MonthrepVO> monthFileList(String monthrepId) throws Exception;
 	
 	// 파일 정보
    WeekrepVO selectAtchFile(int atchFileId) throws Exception;
}
