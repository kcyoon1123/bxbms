package com.bx.bxbms.member.dao;

import java.util.List;


import com.bx.bxbms.member.vo.MemberVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("memberMapper")
public interface MemberMapper {
	
	// 회원가입
	void memberSignUp(MemberVO vo) throws Exception;
	
	// 로그인
	MemberVO login(MemberVO vo) throws Exception;
	
	// 회원정보 수정
    void memberModify(MemberVO vo) throws Exception;
    
    // 회원정보 수정
    void memberStModify(MemberVO vo) throws Exception;
   
    // 회원정보
 	MemberVO memberInfo(String memId) throws Exception;
 	
 	// 회원비밀번호정보 수정
 	void memberPassModify(MemberVO vo) throws Exception;
 	
 	// 회원 목록
	List<MemberVO> memberList() throws Exception; 
	
	// 아이디 중복 체크
	int idCheck(MemberVO vo)throws Exception; 

	// 관리자권한 회원상태 수정
	void adminmemberModify(MemberVO vo) throws Exception;
	
	// 회원탈퇴
	void deleteMember(MemberVO vo) throws Exception;
		
		
}
