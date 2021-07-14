package com.bx.bxbms.member.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Alias("memberVO")
@Data @Getter @Setter
public class MemberVO {
	
	private String memId;		// 회원아이디
	private String userId;		// 사용자아이디
	private String userPass;		// 사용자비밀번호
	private String userNm;			// 사용자명
	private String comNm;			// 회사명
	private String deptNm;			//부서명
	private String position;		// 직급
	private String phoneNo;		   //전화번호
	private String emlAddr;			// 이메일주소
	private String addr;			// 주소
	private String membSt;			// 회원상태
	private String roleId;			// 권한아이디
	private String roleNm;			// 권한명
	private Date regDt;				// 등록일시
	private String memSe;			// 회원구분
}
