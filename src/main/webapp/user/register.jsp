<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="../common/heading.jsp"%>
</head>

<body>
	<%@ include file="../common/top.jsp"%>
	<!-- =========================== Main Start =========================== -->
	<div class="container" style="margin-top: 80px;">
		<div class="row">
			<%@ include file="../common/aside.jsp"%>
			<div class="col-sm-9">
			<h3><b>회원가입</b></h3><hr>
				<div class="row">
					<div class="col-3"></div>
					<div class="col-6">
						<form action="/bbs/user/register" method="post">
							<table class="table table-borderless">
								<tr>
									<td><label for="uid">사용자 ID</label></td>
									<td><input type="text" name="uid" id="uid"></td>
								</tr>
								<tr>
									<td><label for="pwd">패스워드</label></td>
									<td><input type="password" name="pwd" id="pwd"></td>
								</tr>
								<tr>
									<td><label for="pwd2">패스워드 확인</label></td>
									<td><input type="password" name="pwd" id="pwd"></td>
								</tr>
								<tr>
									<td><label for="uname">이름</label></td>
									<td><input type="text" name="uname" id="uname"></td>
								</tr>
								<tr>
									<td><label for="email">이메일</label></td>
									<td><input type="hidden" name="email" value="null" /><input type="text" name="email" id="email"></td>
								</tr>
								<tr>
									<td><input class="btn btn-primary" type="submit" value="제출"></td>
									<td><input class="btn btn-secondary" type="reset" value="취소"></td>
								</tr>
							</table>
						</form>
					</div>
					<div class="col-3"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- =========================== Main End =========================== -->
	<%@ include file="../common/bottom.jsp"%>
</body>
</html>
