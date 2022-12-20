<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="../common/heading.jsp"%>
</head>

<body>
	<%@ include file="../common/top.jsp"%>
	<!-- =========================== Main Start =========================== -->
	<div class="container">
		<div class="row" style="justify-content: space-evenly">
			<%@ include file="../common/aside.jsp"%>


			<!-- // content - 리스트  -->
			<div class="content col-lg-8">
				<div class="content-title pb-3">
					<h3>게시판</h3>
					<div class="">
						<button class="btn maincolor" onclick="location.href='/bbs/board/write'">
							글쓰기</button>
					</div>
				</div>
				<table class="content-desc">
					<tr>
						<th>no.</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>날짜/시간</th>
						<th>조회수</th>
					</tr>
					<c:forEach var="b" items="${ boardList}">
					<tr>
						<td>${b.bid}</td>
						<c:choose>
						<c:when test="${b.replyCnt > 0 }">
						<td>
							<a href = "/bbs/board/detail?bid=${b.bid }">${b.btitle}
							<span class = "content-reply">[${b.replyCnt }]</span>
							</a>						
						</td>						
						</c:when>
						<c:otherwise>
						<td>
							<a href = "/bbs/board/detail?bid=${b.bid }">${b.btitle}</a>						
						</td>						
						</c:otherwise>
						</c:choose>
						<td>${b.uname}</td>
						
						<c:choose>
						<c:when test="${today eq fn:substring(b.modtime,0,10)}">
							<td>${fn:substringAfter(b.modtime, 'T')}</td>
						</c:when>
						<c:otherwise>
							<td>${fn:replace(b.modtime,'T',' ')}</td>
						</c:otherwise>
						</c:choose>
						<td>${b.viewCnt }</td>
					</tr>
					</c:forEach>
				</table>
				
				<!-- 페이지네이션 -->
				<div class="content-bottom pt-4">
					<div class="content-pagenation">
						<ul class="pagination">
							<li class="page-item"><a class="page-link" href="#">&lt;</a>
							</li>
							<c:forEach var="page" items="${pageList}">
								<li class="page-item ${(currentBoardPage eq page) ? 'active' : ''}">
								<a class="page-link" href="<%= BOARD %>?page=${page}">${page}</a></li>
							</c:forEach>							
							<li class="page-item"><a class="page-link" href="#">&gt;</a>
							</li>
						</ul>
					</div>

					<form class="content-search" action="" method="post">
						<select class="content-search-inner subcolor tran">
							<option value="1" selected>제목</option>
							<option value="2">본문</option>
							<option value="3">글쓴이</option>
						</select> <input type="search" class="content-search-inner subcolor tran" />
						<button class="btn maincolor" type="submit">검색</button>
					</form>
				</div>
			</div>
			<!-- content - 리스트 // -->
		</div>
	</div>
	<!-- =========================== Main End =========================== -->
	<%@ include file="../common/bottom.jsp"%>
</body>
</html>
