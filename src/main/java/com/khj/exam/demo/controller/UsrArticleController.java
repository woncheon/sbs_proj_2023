package com.khj.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khj.exam.demo.service.ArticleService;
import com.khj.exam.demo.util.Ut;
import com.khj.exam.demo.vo.Article;
import com.khj.exam.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;

	// 액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession session, String title, String body) {
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (session.getAttribute("loginMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-3", "로그인을 먼저 해주세요");
		}

		if (Ut.empty(title)) {
			return ResultData.from("F-1", "title을 입력해주세요");
		}

		if (Ut.empty(body)) {
			return ResultData.from("F-2", "body를 입력해 주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId, title, body);
		int id = (int) writeArticleRd.getData1();
		Article article = articleService.getArticle(id);

		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(),"article", article);
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {
		List<Article> articles = articleService.getArticles();
		return ResultData.from("S-1", "게시물 리스트입니다.","articles", articles);
	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {
		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), "article", article);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession session,int id) {
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (session.getAttribute("loginMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-3", "로그인을 먼저 해주세요");
		}

		
		
		Article article = articleService.getArticle(id);

		if(article.getMemberId()!=loginedMemberId) {
			return ResultData.from("F-2", "본인 글이 아닙니다.");
		}
		
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다", id));
		}

		articleService.deleteArticle(id);

		return ResultData.from("F-1", Ut.f("%d번 게시물이 삭제되었습니다", id),"id" ,id);
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpSession session, int id, String title, String body) {
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (session.getAttribute("loginMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-3", "로그인을 먼저 해주세요");
		}

		
		
		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다", id));
		}
		
		ResultData actorCanModifyRd=articleService.actorCanModify(loginedMemberId,article);
		
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		

		return articleService.modifyArticle(id,title, body);
	}
	// 액션 메서드 끝

}
