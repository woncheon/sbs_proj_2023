package com.khj.exam.demo.vo;

import java.io.IOException;

import com.khj.exam.demo.service.MemberService;
import com.khj.exam.demo.util.Ut;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;

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
   
   public Rq(HttpServletRequest req, HttpServletResponse resp,MemberService memberService) {
      this.req = req;
      this.resp = resp;
      
      this.session = req.getSession();
      
      boolean isLogined = false;
      
      int loginedMemberId = 0;
      
      if (session.getAttribute("loginMemberId") != null) {
         
         isLogined = true;
         loginedMemberId = (int) session.getAttribute("loginMemberId");
         loginedMember=memberService.getMemberById(loginedMemberId);
      }
      
      this.isLogined = isLogined;
      this.loginedMemberId = loginedMemberId;
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
   public String jsReplace(String msg,String uri) {
	   return Ut.jsReplace(msg, uri);
   }
   
}