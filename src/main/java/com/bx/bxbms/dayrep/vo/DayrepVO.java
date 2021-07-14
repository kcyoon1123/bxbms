package com.bx.bxbms.dayrep.vo;

import org.apache.ibatis.type.Alias;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Alias("dayrepVO")
@Data @Getter @Setter
public class DayrepVO {
	private String dayrepId;
	private String dayrepCont;
	private String regId;
	private String regDt;
	private String mdfcnDt;
	private String repDate;
	private String searchKeyword;
	private String searchType;
	private String comNm;
	private String userNm;
	private String userId;
	private String position;
	private String membSt;
}
