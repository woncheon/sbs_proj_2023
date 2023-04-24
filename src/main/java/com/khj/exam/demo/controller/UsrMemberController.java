package com.khj.exam.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khj.exam.demo.service.MemberService;
import com.khj.exam.demo.util.Ut;
import com.khj.exam.demo.vo.Member;
import com.khj.exam.demo.vo.ResultData;
import com.khj.exam.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsrMemberController {
	private MemberService memberService;
	private Rq rq;
	public UsrMemberController(MemberService memberService,Rq rq) {
		this.memberService = memberService;
		this.rq=rq;
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
			
		if ( Ut.empty(loginId)) {
			return ResultData.from("F-1","loginId를 입력해주세요.");
		}
		
		if ( Ut.empty(loginPw)  ) {
			return ResultData.from("F-2","loginPw를 입력해주세요.");
		}
		
		if ( Ut.empty(name) ) {
			return ResultData.from("F-3","Name을 입력해주세요.");
		}
		
		if ( Ut.empty(nickname)) {
			return ResultData.from("F-4","nickname을 입력해주세요.");
		}
		
		if ( Ut.empty(cellphoneNo)) {
			return ResultData.from("F-5","cellphoneNo를 입력해주세요.");
		}
		if ( Ut.empty(email)) {
			return ResultData.from("F-6","email을 입력해주세요.");
		}
		
		ResultData<Integer> joinRd= memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		if(joinRd.isFail()) {
			return (ResultData)joinRd;
		}
		
		
		Member member = memberService.getMemberById(joinRd.getData1());
		
		return ResultData.newData(joinRd, "member", member);
	}
	
	
	
	@RequestMapping("/usr/member/login")
	public String showLogin(HttpSession session) {
		return "usr/member/login";
		
		
	}
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req, String loginId, String loginPw) {
		
		if(rq.isLogined()) {
			return rq.historyBack("이미 로그인 하셨습니다.");
		}
		
		if ( Ut.empty(loginId)) {
			return rq.historyBack("loginId를 입력해주세요.");
		}
		
		if ( Ut.empty(loginPw)  ) {
			return rq.historyBack("loginPw를 입력해주세요.");
		}

		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member==null) {
			return rq.historyBack("존재하지 않는 아이디");
		}
		if(member.getLoginPw().equals(loginPw)==false) {
			return rq.historyBack("비밀번호 불일치");
		}
		rq.login(member);
		return rq.jsReplace(Ut.f("%s님 환영합니다.", member.getNickname()), "/");
	}
	
	
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest req) {
		
		if(!rq.isLogined()) {
			return rq.historyBack("이미 로그아웃 상태입니다.");
		}

		
		rq.logout();
		return rq.jsReplace("로그아웃 되었습니다.","/");
	}
	
	
	
	
	@RequestMapping("/usr/member/getMembers")
	@ResponseBody
	public List<Member> getMembers() {
		return memberService.getMembers();
	}
}
