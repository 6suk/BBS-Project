<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
pageContext.setAttribute("newline", "\n");
%>


<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="../common/heading.jsp"%>
</head>

<body>
	<%@ include file="../common/top.jsp"%>


	<div class="container">
		<div class="row" style="justify-content: space-evenly">
			<%@ include file="../common/aside.jsp"%>

				<div class="inputtb content col-lg-8">
					<div class="inputtb board-view pb-4">
						<div>
							<div class="board-view-bid">
								<span>${board.bid }</span>
							</div>
							<h2 class="board-view-title">${board.btitle }</h2>
							<div class="board-view-info">
								<span>${board.uname }</span><span>${fn:replace(board.modtime,'T','
              ')}</span>
							</div>
						</div>

						<div class="content multibtn">
							<button class="btn action maincolor"
								onclick="location.href='<%= BLIST %>?page=${currentBoardPage}'">
								목록</button>
							<div class="space3"></div>
							<c:choose>
							<c:when test="${board.uid eq uid }">
							<button class="btn action subcolor"
								onclick="location.href='<%= BUPDATE %>?bid=${board.bid}'">
								수정</button>
							<div class="space3"></div>
							<button class="btn action subcolor"
								onclick="location.href='<%= BDEL %>?bid=${board.bid}'">
								삭제</button>
							</c:when>
							<c:otherwise>
							<button class="btn action subcolor" disabled >
								수정</button>
							<div class="space3"></div>
							<button class="btn action subcolor" disabled>
								삭제</button>
							</c:otherwise>
							</c:choose>
						</div>
					</div>

					<div class="row mt-2 mx-1">
						<div class="board-view-desc py-3">
							<div>
								<p class="board-view-file">
									<c:choose>
									<c:when test="${fileList eq null or empty fileList or fileList eq ''}">
									<span>첨부파일</span>
									<span>없음</span>
									</c:when>
									<c:otherwise>
									<span>첨부파일</span>
									<c:forEach var="file" items="${fileList }">
										<a href="/bbs/board/download?file=${file}" download="${file }">${file }</a>
									</c:forEach>
									</c:otherwise>
									</c:choose>
								</p>
							</div>
							<div>
								<p class="board-view-cnt">
									<span>조회 ${board.viewCnt }</span><span>댓글 ${board.replyCnt }</span>
								</p>
							</div>
						</div>

						<div class="board-view-content py-5">
							${board.bcontent }</div>

						<!-- 댓글 -->
						<div class="reply-content pt-3">
							<c:forEach var="r" items="${rList }">
								<c:choose>
								<c:when test="${r.ismine eq 1}">
									<!-- 내가 쓴 댓글 -->
									<div class="d-flex flex-row-reverse mt-3">
										<div class="card subcolor w-75">
											<div class="card-body text-end">
												<div class="reply-info">
													<span>${r.uname }</span>
													<span>${fn:replace(r.regtime,'T','')}</span>
												</div>
												<div class="line"></div>
												<div class="reply-content">${fn:replace(r.rcontent, newline, '<br>') }</div>
											</div>
										</div>
									</div>								
								</c:when>
								<c:otherwise>
									<!-- 남이 쓴 댓글 -->
									<div class="d-flex flex-row mt-3">
										<div class="card graycolor w-75">
											<div class="card-body">
												<div class="reply-info">
													<span>${r.uname }</span>
													<span>${fn:replace(r.regtime,'T','')}</span>
												</div>
												<div class="line"></div>
												<div class="reply-content">${fn:replace(r.rcontent, newline, '<br>') }</div>
											</div>
										</div>
									</div>
								</c:otherwise>
								</c:choose>
							</c:forEach>
			


							<form class="form-inline" action="/bbs/board/reply" method="post">
								<input type="hidden" name="bid" value="${board.bid }" />
								<input type="hidden" name="uid" value="${board.uid }" />
								<div class="reply-write pt-4">
									<textarea class="board-input" id="content" name="content"
										rows="3" placeholder="댓글작성"></textarea>
									<div class="space10"></div>
									<button type="submit" class="btn maincolor">등록</button>
								</div>
							</form>
						</div>
					</div>
				</div>
		</div>
	</div>
	<%@ include file="../common/bottom.jsp"%>
</body>
</html>
