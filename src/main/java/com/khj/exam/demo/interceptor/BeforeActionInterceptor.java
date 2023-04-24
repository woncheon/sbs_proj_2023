package com.khj.exam.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.khj.exam.demo.service.MemberService;
import com.khj.exam.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor {
	private Rq rq;
	
	
	public BeforeActionInterceptor(Rq rq) {
		this.rq=rq;
	}
	@Autowired
	private MemberService memberService;
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		/*
		 * rq에 컴포넌트를 붙였기에 자동으로 만들어줘서 여기서 rq를 해줄 필요가 없음
		 * Rq rq=new Rq(req,resp,memberService);
		 *  req.setAttribute("rq", rq);
		 */
		rq.initOnBeforeActionInterceptor();

		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}
