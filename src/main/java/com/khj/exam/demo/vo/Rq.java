package com.khj.exam.demo.vo;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.khj.exam.demo.service.MemberService;
import com.khj.exam.demo.util.Ut;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // 각 리퀘스트마다 해당 클래스에 rq클래스를 주입하는 것
public class Rq {

	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;

	@Getter
	private Member loginedMember;

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;

		this.session = req.getSession();

		boolean isLogined = false;

		int loginedMemberId = 0;

		if (session.getAttribute("loginMemberId") != null) {

			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;

		this.req.setAttribute("rq", this);
	}

	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html;charset=utf-8");
		print(Ut.jsHistoryBack(msg));
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void println(String str) {
		print(str + "\n");
	}

	public void login(Member member) {
		session.setAttribute("loginMemberId", member.getId());

	}

	public void logout() {
		session.removeAttribute("loginMemberId");

	}

	public String historyBackJsOnview(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "common/js";
	}

	public String historyBack(String msg) {
		return Ut.jsHistoryBack(msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}

	// rq객체가 자연스럽게 생성하게끔 유도하는 함수
	// 지우면 ㅇㄴ되고
	// 편의를 위해 beforActionInterceptor에서 꼭 호출 해야함
	public void initOnBeforeActionInterceptor() {

	}

}