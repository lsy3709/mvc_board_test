<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table width="500" cellpadding="0" cellspacing="0" border="1">
<form action="modify.do" method="post">
<input type="hidden" name="bId" value="${conten_view.bId}">
<tr>
<td>번호</td>
<td>${content_view.bId}</td>
</tr>
<tr>
<td>히트</td>
<td>${content_view.bHit}</td>
</tr>
<tr>
<td>이름</td>
<td><input type="text" name="bName" value="${content_view.bName}"></td>
</tr>
<tr>
<td>제목</td>
<td><input type="text" name="bTitle" value="${content_view.bTitle}"></td>
</tr>
<tr>
<td>내용</td>
<td><textarea rows="10" name="bContent">${reply_view.bContent}</textarea></td>
</tr>
<tr>
<td colspan="2"><input type="submit" value="답변"><a href="list.do">목록</a>

</form>

</table>
</body>
</html>