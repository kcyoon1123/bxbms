package com.bx.bxbms.code.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bx.bxbms.code.dao.CodeMapper;
import com.bx.bxbms.code.vo.CodeVO;
import com.bx.bxbms.dayrep.vo.DayrepVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("codeService")
public class CodeService extends EgovAbstractServiceImpl {
	
	@Resource(name="codeMapper")
	private CodeMapper codeMapper;
	
	@Resource(name="codeGrpIdGnrService")
	private EgovIdGnrService codeGrpIdGnrService;
	
	public void insertCodeGrp(CodeVO vo)throws Exception {
		// 등록일시 현재 날짜
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String date = formatter.format(now);
		vo.setRegDt(date);
		// idGnr으로 아이디 자동생성
		vo.setCodeGrpId(codeGrpIdGnrService.getNextStringId());
		codeMapper.insertCodeGrp(vo);
	}
	
	// 일간보고 목록
	public List<CodeVO> codeGrpList(CodeVO vo) throws Exception {
		return codeMapper.codeGrpList(vo);
	} 
	// 일간보고 목록
	public CodeVO codeGrpDetail(String codeGrpId) throws Exception {
		return codeMapper.codeGrpDetail(codeGrpId);
	} 
}
