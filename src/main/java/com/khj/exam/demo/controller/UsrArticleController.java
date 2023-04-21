package com.khj.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khj.exam.demo.service.ArticleService;
import com.khj.exam.demo.util.Ut;
import com.khj.exam.demo.vo.Article;
import com.khj.exam.demo.vo.ResultData;
import com.khj.exam.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrArticleController {
   @Autowired
   private ArticleService articleService;
   
   // 액션 메서드 시작
   @RequestMapping("/usr/article/doWrite")
   @ResponseBody
   public String doWrite(HttpServletRequest req, String title, String body, String replaceUri) {
	   Rq rq=(Rq)req.getAttribute("rq");
      
      
      //만약 title or body 입력을 안했을때
      if(Ut.empty(title)) {
         return rq.historyBack("title을(를) 입력 해주세요.");
      }
      
      if(Ut.empty(body)) {
         return  rq.historyBack("body을(를) 입력 해주세요.");
      }
      
      ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
      
      int id = writeArticleRd.getData1();
      
      if(Ut.empty(replaceUri)) {
    	  replaceUri=Ut.f("../article/detail?id=%d", id);
      }
      
      return  rq.jsReplace(Ut.f("%d번 게시물이 생성되었습니다", id), replaceUri);
    		  
    		  //ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), "article", article);
   }
   @RequestMapping("/usr/article/write")
   public String showWrite() {
	   return "/usr/article/write";
   }
   
   @RequestMapping("/usr/article/list")
   public String showList(HttpServletRequest req, Model model) {
	   Rq rq=(Rq)req.getAttribute("rq");
      
      List<Article>  articles = articleService.getForPrintArticles(rq.getLoginedMemberId());
      model.addAttribute("articles", articles );
      
      return "usr/article/list";
   }
   
   @RequestMapping("/usr/article/detail")
   public String showList(HttpServletRequest req, Model model, int id) {
	   Rq rq=(Rq)req.getAttribute("rq");
      
      Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
      
      model.addAttribute("article", article);
      
      return "usr/article/detail";
   }
   
   @RequestMapping("/usr/article/getArticle")
   @ResponseBody
   public ResultData<Article> getArticle(HttpServletRequest req, int id) {
	   Rq rq=(Rq)req.getAttribute("rq");
      
      Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
      
      if ( article == null ) {
         return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
      }
      
      return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), "article", article);
   }
   
   @RequestMapping("/usr/article/doDelete")
   @ResponseBody
   public String doDelete(HttpServletRequest req, int id) {
	   Rq rq=(Rq)req.getAttribute("rq");
      
      Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
      
      if ( article.getMemberId() != rq.getLoginedMemberId()) {
         return rq.historyBack("권한이 없습니다.");
      }
      
      if ( article == null ) {
         ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
      }
      
      articleService.deleteArticle(id);
      
      return rq.jsReplace(Ut.f("%d번 게시물을 삭제 하엿습니다.",  id),"../article/list");
   }
   
   @RequestMapping("/usr/article/modify")
   
   public String showmodify(HttpServletRequest req, Model model, int id, String title, String body) {
	   
	   Rq rq=(Rq)req.getAttribute("rq");
	   
	   
	   Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
	   
	   if ( article == null ) {
		   return rq.historyBackJsOnview(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
	   }
	   
	   ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
	   
	   if ( actorCanModifyRd.isFail()) {
		   return rq.historyBackJsOnview(actorCanModifyRd.getMsg());
	   }
	   model.addAttribute("article", article);
	   return "usr/article/modify";
   }
   @RequestMapping("/usr/article/doModify")
   @ResponseBody
   public String doModify(HttpServletRequest req, int id, String title, String body) {
      
	   Rq rq=(Rq)req.getAttribute("rq");
     
      
      Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
      
      if ( article == null ) {
         return Ut.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
      }
      
      ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
      
      if ( actorCanModifyRd.isFail()) {
         return rq.historyBack(actorCanModifyRd.getMsg());
      }
      
      articleService.modifyArticle(id, title, body);
      
      return rq.jsReplace(Ut.f("%s번 글이 수정되었습니다", id), Ut.f("../article/detail?id=%d", id));
   }
   // 액션 메서드 끝

}