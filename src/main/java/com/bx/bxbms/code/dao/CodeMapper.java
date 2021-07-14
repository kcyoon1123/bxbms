package com.bx.bxbms.code.dao;

import java.util.List;

import com.bx.bxbms.code.vo.CodeVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("codeMapper")
public interface CodeMapper {
	
	// 공통코드그룹 등록
	void insertCodeGrp(CodeVO vo) throws Exception;
	
	// 공통코드그룹 목록
	List<CodeVO> codeGrpList(CodeVO vo) throws Exception;
	
	
	CodeVO codeGrpDetail(String codeGrpId)throws Exception;

}
