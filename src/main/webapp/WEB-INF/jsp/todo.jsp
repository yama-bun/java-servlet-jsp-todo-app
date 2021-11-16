<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.User, model.Memo, java.util.List" %>
<% 
// セッションからユーザー情報取得
User loginUser = (User) session.getAttribute("loginUser");
// リクエストスコープに保存されたメモを取得
/* List<Memo> memoList = (List<Memo>) request.getAttribute("memoList"); */
String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ToDo一覧ページ</title>
<style>
	li {
		margin-bottom: 10px;
	}
	time {
		margin: 0px 10px;
	}
	.btn { 
		
		background-color: gray;
		margin: 1px;
		padding: 5px;
		border-radius: 5px;
		color: black;
		text-decoration: none;
	}
	.done {
		text-decoration: line-through;
	}
	.inline {
		display: inline-flex;
	}
	.error {
		color: red;
	}
</style>
</head>
<body>
<% if(errorMsg != null){ %>
	<p class="error"><%= errorMsg %></p>
<% } %>
<% if(loginUser != null) { %>
	<h1><%= loginUser.getName() %>さんのTodo</h1>
	<form action="/todo/Main" method="post">
		<label for="content">内容</label>
		<input type="text" name="content">
		<label for="day">期日</label>
		<input type="text" name="day">
		<input type="submit" value="作成">
		<a class="btn" href="/todo/Main">更新</a>
	</form>
	<ul>
	<c:forEach var="memo" items="${memoList}">
		<li>
		<!-- タスク完了時は、doneクラスにて打ち消し線をつける -->
		<c:choose>
			<c:when test="${!memo.done }">
				<span><c:out value="${memo.content }"/></span>
				<time><c:out value="${memo.day }"/></time>
				<div class="inline">
					<form action="/todo/UpdateMemo" method="post">
						<input type="hidden" name="id" value="<c:out value="${memo.id }"/>">
						<input class="btn" type="submit" value="完了">
					</form>
					<form action="/todo/DeleteMemo" method="post">
						<input type="hidden" name="id" value="<c:out value="${memo.id }"/>">
						<input class="btn" type="submit" value="削除" onclick="return checkDelete()">
					</form>
				</div>
			</c:when>
			<c:otherwise>
				<span class="done"><c:out value="${memo.content }"/></span>
				<time class="done"><c:out value="${memo.day }"/></time>
				<div class="inline">
					<form action="/todo/DeleteMemo" method="post">
						<input type="hidden" name="id" value="<c:out value="${memo.id }"/>">
						<input class="btn" type="submit" value="削除" onclick="return checkDelete()">
					</form>
				</div>
			</c:otherwise>
		</c:choose>
		</li>
	</c:forEach>
	</ul>
	<a href="/todo/Logout">ログアウト</a>
<% } else { %>
	<h1>ログインに失敗しました。</h1>
	<p class="error">名前を入力してください。</p>
<% } %>
	<a href="index.jsp">戻る</a>
	<script>
		function checkDelete() {
				if (window.confirm('削除してよろしいですか？')) {
					return true;
				} else {
					return false;
				}
			}
	</script>
</body>
</html>