<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
<h1>list.jsp</h1>
<table width="500" cellpadding ="0" cellspacing="0" border="1">
<tr>
<th>번호</th>
<th>이름</th>
<th>제목</th>
<th>날짜</th>
<th>히트</th>
</tr>
<c:forEach items="${list}" var="dto">
<tr>
<td>${dto.bId}</td>
<td>${dto.bName}</td>
<td>
<c:forEach begin="1" end="${dto.bIndent}">-</c:forEach>
<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}</a></td>
<td>${dto.bDate}</td>
<td>${dto.bHit}</td>
</tr>
</c:forEach>
<tr>
<td colspan="5"><a href="write_view.do">글작성</a></td>
</tr>
</table>

</body>
</html>