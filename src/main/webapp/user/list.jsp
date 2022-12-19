<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
				<div class="title">
					<h3>사용자 목록</h3>
					<div>

						<c:choose>
							<c:when test="${empty uid}">
								<input class="mainbtn" type="button" value="로그인"
									onclick="location.href ='<%=LOGIN%>'" />
								<input class="subbtn" type="button" value="회원가입"
									onclick="location.href ='<%=REG_VIEW%>'" />
							</c:when>

							<c:otherwise>
								<input class="mainbtn" type="button" value="로그아웃"
									onclick="location.href ='<%=LOGOUT%>'" />
								<input class="subbtn" type="button" value="회원가입"
									onclick="location.href ='<%=REG_VIEW%>'" style="display: none" />
							</c:otherwise>
						</c:choose>

					</div>
				</div>

				<table>
					<tr style="background-color: #e8f0fd42">
						<th>No.</th>
						<th>아이디</th>
						<th>이름</th>
						<th>email</th>
						<th>가입일</th>
						<th>수정 / 삭제</th>
					</tr>
					<c:forEach var="u" items="${userList}" varStatus="loop">
						<tr>
							<td>${loop.count}</td>
							<td>${u.uid}</td>
							<td>${u.uname}</td>
							<td>${u.email}</td>
							<td>${u.regDate}</td>
							<td>
								<%-- 수정 버튼 시작 --%> <c:choose>
									<c:when test="${not(uid eq u.uid)}">
										<button class="subbtn" type="button" disabled>수정</button>
									</c:when>
									<c:otherwise>
										<button class="subbtn" type="button"
											onclick="location.href='<%= UPDATE %>?uid=${u.uid}'">수정</button>
									</c:otherwise>
								</c:choose> <%-- 삭제 버튼 시작 --%> <c:choose>
									<c:when test="${not(uid eq 'admin')}">
										<button class="subbtn" type="button" disabled>삭제</button>
									</c:when>
									<c:otherwise>
										<button class="subbtn" type="button"
											onclick="location.href='<%= DEL %>?uid=${u.uid}'">삭제</button>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
				<ul class="pagination justify-content-center mt-4">
					<li class="page-item"><a class="page-link" href="#">&laquo;</a></li>
					<li class="page-item"><a class="page-link" href="#">1</a></li>
					<li class="page-item active"><a class="page-link" href="#">2</a></li>
					<li class="page-item"><a class="page-link" href="#">3</a></li>
					<li class="page-item"><a class="page-link" href="#">&raquo;</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- =========================== Main End =========================== -->
	<%@ include file="../common/bottom.jsp"%>
</body>
</html>
