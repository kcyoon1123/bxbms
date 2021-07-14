package com.bx.bxbms.member.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bx.bxbms.member.dao.MemberMapper;
import com.bx.bxbms.member.vo.MemberVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
@Service("memberService")
public class MemberService extends EgovAbstractServiceImpl{
	
	@Resource(name="memberMapper")
	private MemberMapper memberMapper;

	// 회원가입
	public void memberSignUp(MemberVO vo) throws Exception {
		Date date = new Date();
		vo.setRegDt(date);
		memberMapper.memberSignUp(vo);
		
	}
	// 로그인
	public MemberVO login(MemberVO vo) throws Exception {
		return memberMapper.login(vo);
	}
	
	// 회원정보 수정
	public void memberModify(MemberVO vo) throws Exception {
		memberMapper.memberModify(vo);
	}
	// 회원 상태 수정
	public void memberStModify(MemberVO vo) throws Exception {
		memberMapper.memberStModify(vo);
		
	}
	// 회원 정보
	public MemberVO memberInfo(String memId) throws Exception {
		return memberMapper.memberInfo(memId);
	}
	// 회원비밀번호정보 수정
	public void memberPassModify(MemberVO vo) throws Exception {
		memberMapper.memberPassModify(vo);
		
	}
	// 회원 목록
	public List<MemberVO> memberList() throws Exception {
		return memberMapper.memberList();
	}
	// 아이디 중복 체크
	public int idCheck(MemberVO vo) throws Exception {
		return memberMapper.idCheck(vo);
	}
	// 관리자권한 회원상태 수정
	public void adminmemberModify(MemberVO vo) throws Exception {
		System.err.println("=========================="+vo.getRoleId());
		Date date = new Date();
		
		vo.setRegDt(date);
		memberMapper.adminmemberModify(vo);
	}
	// 회원탈퇴
	public void deleteMember(MemberVO vo) throws Exception {
		memberMapper.deleteMember(vo);
		
	}

}
