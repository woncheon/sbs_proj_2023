package com.khj.exam.demo.vo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;

public class Rq {
	@Getter
	boolean isLogined;
    @Getter
    int loginedMemberId;
   
    public Rq(HttpServletRequest req) {
    	HttpSession session=req.getSession();
    	boolean isLogined=false;
    	int loginedMemberId=0;
    	
    	if(session.getAttribute("loginMemberId")!=null) {
    		isLogined=true;
    		loginedMemberId=(int)session.getAttribute("loginMemberId");
    	}
    	
    	this.isLogined=isLogined;
    	this.loginedMemberId=loginedMemberId;
    }
    
}
