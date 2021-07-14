package com.bx.bxbms.weekrep.dao;

import java.util.List;

import com.bx.bxbms.weekrep.vo.WeekrepVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("weekrepMapper")
public interface WeekrepMapper {

	// 주간보고 목록
	List<WeekrepVO> weekrepList(WeekrepVO vo) throws Exception;
	
	// 주간보고 작성 여부
	int weekSt(WeekrepVO vo)throws Exception; 
	
	// 주간보고 등록
    void insertWeekrep(WeekrepVO vo) throws Exception;

    // 주간보고 수정
    void updateWeekrep(WeekrepVO vo) throws Exception;
	
    // 파일 등록
    void insertWeekrepFile(WeekrepVO vo) throws Exception;
    
    // 파일 수정
    void modifyWeekrepFile(WeekrepVO vo) throws Exception;
    
    // 파일 정보
    WeekrepVO selectAtchFile(int atchFileId) throws Exception;
    
    // 댓글 등록
    void insertReply(WeekrepVO vo) throws Exception;
    
    // 주간보고 댓글 목록
 	List<WeekrepVO> replyList(String weekrepId) throws Exception;
 	
 	// 주간보고 상세
 	WeekrepVO repDetail(String weekrepId) throws Exception;
 	
	// 댓글 수정
    void modifyReply(WeekrepVO vo) throws Exception;
    
    // 댓글 삭제
    void repDelete(WeekrepVO vo) throws Exception;
    
    // 댓글 삭제
    void fileDelete(WeekrepVO vo) throws Exception;
    
    // 주간보고 파일 여부
 	int weekFileSt(String weekrepId)throws Exception; 
}
