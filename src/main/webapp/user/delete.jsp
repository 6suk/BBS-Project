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
				<h3>
					<b>회원 탈퇴</b>
				</h3>
				<hr>
				<div class="row">
					<div class="col-3"></div>
					<div class="col-6">
						<div class="card border-warning mt-3">
							<div class="card-body">
								<strong class="card-title">삭제하시겠습니까?</strong>
								<p class="card-text text-center">
									<br>
									<button class="btn btn-primary"
										onclick="location.href='/bbs/user/deleteConfirm?uid=${param.uid}'">삭제</button>
									<button class="btn btn-secondary"
										onclick="location.href='<%=LIST%>'">취소</button>
								</p>
							</div>
						</div>
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
