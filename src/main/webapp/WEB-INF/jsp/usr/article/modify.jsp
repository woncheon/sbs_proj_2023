<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="게시물 수정"/>
<%@include file="../common/header.jspf" %>

<section class="mt-5">
   <div class="container mx-auto px-3">
       <form method="POST" action="../article/doModify">
       		<input type="hidden" name="id" value="${article.id }" />
          <div class="table-box-type-1">
            <table>
            <colgroup>
              <col width="200"/>
            </colgroup>
              <tbody>
                <tr>
                  <th>번호</th>
                  <td>${article.id}</td>
                </tr>
                <tr>
                  <th>작성날짜</th>
                  <td>${article.getRegDateForPrint()}</td>
                </tr>
                <tr>
                  <th>수정날짜</th>
                  <td>${article.getUpdateDateForPrint()}</td>
                </tr>
                <tr>
                  <th>작성자</th>
                  <td>${article.extra__writerName}</td>
                </tr>
                <tr>
                  <th>제목</th>
                  <td>
                  	<input type="text" class="w-96 input input-bordered w-full max-w-xs " name="title" placeholder="제목" value="${article.title}" />
                    
                  </td>
                </tr>
                <tr>
                  <th>내용</th>
                  <td>
                   <textarea name= "body" placeholder="내용" class="textarea textarea-bordered textarea-lg w-full max-w-xs" ></textarea>
                  </td>
                </tr>
                <tr>
                  <th>수정</th>
                  <td>
                    <input type="submit" value="수정" />
                    <button type="button" onclick="history.back();"> 뒤로가기</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
         
         <div class="btns">
            <button class="btn btn-primary" type="button" onclick="history.back();">뒤로가기</button>
               <a  class="btn btn-link"  href="../article/modify?id=${article.id }">게시물 수정</a>
            <c:if test="${article.extra__actorCanDelete}">
               <a class="btn btn-link"  onclick="if( confirm('정말 삭제하시겠습니까?') == false )return false;" href="../article/doDelete?id=${article.id }">게시물 삭제</a>
            </c:if>
         </div>
       </form>
    </div>
</section>


<%@include file="../common/footer.jspf" %>