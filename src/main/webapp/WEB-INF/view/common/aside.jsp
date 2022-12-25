<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="col-lg-3">
	<div class="intro">
		<div class="intro-img">
			<img width="80%"
				src="https://emojipedia-us.s3.amazonaws.com/source/microsoft-teams/337/blue-heart_1f499.png" />
		</div>
		<div class="mspace"></div>
		<div class="intro-msg m-4">
			<c:choose>
				<c:when test="${empty uid}">
					<div class="intro-msg-main p-2 pb-3">
						<h4>
							<b>로그인</b>을 해주세요!
						</h4>
					</div>
					<div class="intro-btn">
						<button class="btn mdi100 maincolor mx-1"
							onclick="location.href='/bbs/user/login'">로그인</button>
						<button class="btn mdi100 subcolor mx-1"
							onclick="location.href='/bbs/user/register'">회원가입</button>
					</div>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${fn:length(uname) >= 4}">
							<div class="intro-msg-main p-2 pb-3"
								style="align-items: flex-end;">
								<h4 style="line-height: 1.3;">${uname}님<br>환영합니다!
								</h4>
								<span class="material-symbols-outlined"
									style="padding-bottom: 0.2rem;"> waving_hand </span>
							</div>
						</c:when>
						<c:otherwise>
							<div class="intro-msg-main p-2 pb-3">
								<h4>${uname}님 환영합니다!</h4>
								<span class="material-symbols-outlined"> waving_hand </span>
							</div>
						</c:otherwise>
					</c:choose>


					<table class="my-3 mx-2">
						<tr>
							<th>아이디</th>
							<td>${uid}</td>
						</tr>
						<tr>
							<th>가입일</th>
							<td>${regDate}</td>
						</tr>
						<c:choose>
							<c:when test="${empty email}">
								<tr>
									<th>이메일</th>
									<td>미등록</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<th>이메일</th>
									<td>${email}</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>

					<div class="intro-btn">
						<button class="btn mdi100 subcolor mx-1"
							onclick="location.href='/bbs/user/update?uid=${uid}'">정보
							수정하기</button>
						<button class="btn mdi100 graycolor mx-1"
							onclick="location.href='/bbs/user/logout'">로그아웃</button>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>