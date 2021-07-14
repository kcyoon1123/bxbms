package com.bx.bxbms.weekrep.vo;

import org.apache.ibatis.type.Alias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Alias("weekrepVO")
@Data @Getter @Setter
public class WeekrepVO {

	private String weekrepId;
	private String weekrepCont;
	private String nextCont;
	private String uniqueness;
	private String regId;
	private String regDt;
	private String pstgBgngDt;
	private String pstgEndDt;
	private String mdfcnId;
	private String mdfcnDt;
	private String delYn;
	private String memId;
	private String atchFileId;
	private String userId;
	private String comNm;
	private String userNm;
	private String position;
	private String atchFileOrgnNm;
	private String atchFileStrgNm;
	private String atchFileStrgPath;
	private Long atchFileSize;
	private String atchFileExtNm;
	private String selectDate;
	private String repId;
	private String repCont;
	private String searchType;
	private String searchKeyword;
	private String repdate;
	
}
