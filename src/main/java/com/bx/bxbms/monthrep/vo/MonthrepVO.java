package com.bx.bxbms.monthrep.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Alias("monthrepVO")
@Data @Getter @Setter
public class MonthrepVO {
	
	private String memId;			// 회원아이디
	private String userId;			// 사용자아이디
	private String regDt;			// 등록일
	private String pstgBgngDt; 		// 수정일
	private String comNm;			// 회사명
	private String monthrepId;   	// 월간보고 아이디
	private String goalCont;			// 월간 목표
	private String premonCont;			// 전월업무현황
	private String monthCont;			// 금월예정 업무
	private String uniqueCont;  		// 특이사항
	private String searchKeyword;
	private String searchType;
	private String position;
	private String membSt;
	private String userNm;
	private String repDate;
	private String mdfcnDt;
	private String atchFileOrgnNm;
	private String atchFileStrgNm;
	private String atchFileStrgPath;
	private Long atchFileSize;
	private String atchFileExtNm;
	private String atchFileId;
}
