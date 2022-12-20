<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="../common/heading.jsp"%>
</head>

<body>
	<%@ include file="../common/top.jsp"%>
	<div class="inputtb container justify-content-center">
		<div class="inputtb content">
			<div class="inputtb content-title pb-4">
				<h3>회원가입</h3>
				<div class="">
					<button class="btn small subcolor" onclick="location.href='<%= LIST %>'">
						Home</button>
				</div>
			</div>

			<form action="<%= REG %>" class="pt-3" method="post">
				<table class = "inputtb content-desc">
					<tr>
						<th>아이디</th>
						<td><input type="text" name="uid" placeholder="*아이디" maxlength="12" required /></td>
					</tr>
					<tr>
						<th>패스워드</th>
						<td><input type="password" name="pwd" placeholder="*패스워드" maxlength="60" required /></td>
					</tr>
					<tr>
						<th>패스워드 확인</th>
						<td><input type="password" name="pwd" placeholder="*패스워드 확인" maxlength="60" required /></td>
					</tr>
					<tr>
						<th>이름</th>
						<td><input type="text" name="uname" placeholder="*이름" maxlength="10" required /></td>
					</tr>
					<tr>
						<th>이메일</th>
						<td><input type="hidden" name="email" value="null" /> <input type="email" name="email" placeholder="이메일" maxlength="20" />
						</td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" class= "btn full maincolor" value="회원가입" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<%@ include file="../common/bottom.jsp"%>
</body>
</html>
