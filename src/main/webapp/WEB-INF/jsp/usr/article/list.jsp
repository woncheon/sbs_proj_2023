<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>article 메~인</title>
</head>
<body>
   <h1>article안녕?</h1>
   
   <hr />
   <table border="1">
      <thead>
         <tr>
            <th>번호</th>
            <th>작성날짜</th>
            <th>수정날짜</th>
            <th>작성자</th>
            <th>제목</th>
         </tr>
      </thead>
      <tbody>
         <c:forEach var="article" items="${articles }">
         <tr>
            <td>${article.id }</td>
            <td>${article.regDate }</td>
            <td>${article.updateDate }</td>
            <td>${article.memberId }</td>
            <td>
               <a href="../article/detail?id=${article.id }">${article.title }</a>
            </td>
         </tr>
         </c:forEach>
      </tbody>
   </table>
</body>
</html>