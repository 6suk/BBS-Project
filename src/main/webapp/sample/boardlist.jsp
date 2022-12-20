<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="../common/heading.jsp"%>
<style>
th, td {
	text-align: center;
}
</style>
</head>

<body>
	<%@ include file="../common/top.jsp"%>
	<!-- =========================== Main Start =========================== -->
	<div class="container" style="margin-top: 80px;">
		<div class="row">
			<%@ include file="../common/aside.jsp"%>
			<div class="col-sm-9">
				<table class="table table-sm table-borderless">
					<form action="" method="post">
						<tr class="d-flex">
							<td class="col-6" style="text-align: left;">
								<h3>
									<strong>게시글 목록</strong> <span style="font-size: 0.6em;">
										<a href="#" class="ms-5"><i class="far fa-file-alt"></i>
											글쓰기</a>
									</span>
								</h3>
							</td>
							<td class="col-2"><select class="form-select me-2">
									<option value="1" selected>제목</option>
									<option value="2">본문</option>
									<option value="3">글쓴이</option>
							</select></td>
							<td class="col-3"><input class="form-control me-2"
								type="search" placeholder="Search" aria-label="Search">
							</td>
							<td class="col-1">
								<button class="btn btn-outline-primary" type="submit">검색</button>
							</td>
						</tr>
					</form>
				</table>
				<hr>
				<table class="table mt-2">
					<tr class="table-secondary ">
						<th class="col-1">번호</th>
						<th class="col-6">제목</th>
						<th class="col-2">글쓴이</th>
						<th class="col-2">날짜/시간</th>
						<th class="col-1">조회수</th>
					</tr>
					<tr>
						<td>102</td>
						<td><a href="#">피할수 없으면 즐겨라. <span class="text-danger">[1]</span></a></td>
						<td>로버트 엘리엇</td>
						<td>09:25:38</td>
						<td>12</td>
					</tr>
					<tr>
						<td>101</td>
						<td><a href="#">행복은 습관이다,그것을 몸에 지니라. <span
								class="text-danger">[2]</span></a></td>
						<td>허버드</td>
						<td>2022-12-14</td>
						<td>23</td>
					</tr>
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
