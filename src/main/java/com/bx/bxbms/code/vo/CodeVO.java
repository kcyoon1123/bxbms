package com.bx.bxbms.code.vo;


import org.apache.ibatis.type.Alias;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Alias("codeVO")
@Data @Getter @Setter
public class CodeVO {

	private String codeGrpId;		// 코드그룹아이디
	private String codeId;			// 코드아이디
	private String codeOrdr;		// 코드순서
	private String codeNm;			// 코드명
	private String codeDesc;		// 코드설명
	private String regId;			// 등록아이디
	private String regDt;			// 등록일시
	private String mdfcnId;			// 수정아이디
	private String mdfcnDt;			// 수정일시
	private String useYn;			// 사용여부
	private String delYn;			// 삭제여부
	private String codeGrpNm;		// 코드그룹명
	private String codeGrpDesc;		// 코드그룹설명
	
}
