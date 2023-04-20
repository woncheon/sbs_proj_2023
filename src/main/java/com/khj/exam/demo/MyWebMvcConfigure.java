package com.khj.exam.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.khj.exam.demo.interceptor.BeforeActionInterceptor;
@Configuration
public class MyWebMvcConfigure implements WebMvcConfigurer{
	
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/error");
	}
	
	
}
