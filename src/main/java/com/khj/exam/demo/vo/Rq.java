package com.khj.exam.demo.vo;

import java.io.IOException;

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
   
   private HttpServletRequest req;
   private HttpServletResponse resp;
   private HttpSession session;
   
   public Rq(HttpServletRequest req, HttpServletResponse resp) {
      this.req = req;
      this.resp = resp;
      this.session=req.getSession();
      HttpSession httpSession = req.getSession();
      
      boolean isLogined = false;
      
      int loginedMemberId = 0;
      
      if (httpSession.getAttribute("loginMemberId") != null) {
         
         isLogined = true;
         loginedMemberId = (int) httpSession.getAttribute("loginMemberId");
      }
      
      this.isLogined = isLogined;
      this.loginedMemberId = loginedMemberId;
      
   }
   
   public void printHistoryBackJs(String msg) {
      resp.setContentType("text/html; charset=UTF-8");
      
      print("<script>");
      
      if (!Ut.empty(msg)) {
         println("alert('" + msg + "');");
      }
      println("history.back()");
      
      print("</script>");
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
   
   
}