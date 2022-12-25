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


	<div class="container">
		<div class="row" style="justify-content: space-evenly">
			<%@ include file="../common/aside.jsp"%>

			<div class="inputtb content col-lg-8">
				<!-- 타이틀 -->
				<div class="inputtb content-title pb-4">
					<h3>${binfo.bid }번 게시글 수정</h3>
					<div class="">
						<button class="btn small subcolor"
							onclick="location.href='<%= BLIST %>?page=${currentBoardPage}'">&lt; Back</button>
					</div>
				</div>
				<!-- 타이틀 끝 -->

				<form action="/bbs/board/update" class="pt-4 mx-3" method="post">
				<input type="hidden" name="bid" value="${binfo.bid }">
					<table class="inputtb board-desc">
						<tr>
							<td><input class="board-input" type="text" placeholder="제목"
								name="title" maxlength="128" required value="${binfo.btitle }"/></td>
						</tr>
						<tr>
							<td><textarea class="board-input" name="content"
									placeholder="내용" maxlength="5000" rows="10">${binfo.bcontent }</textarea>
							</td>
						</tr>
						<tr>
							<td>
								<!-- <label for="files"></label> --> <input class="board-input"
								type="file" placeholder="첨부파일" name="files" multiple value="${binfo.files }"/>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="multibtn pt-4"><input type="submit"
								class="btn full maincolor" value="수정하기" />
								<div class="space10"></div> <input type="reset"
								class="btn full subcolor" value="취소" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../common/bottom.jsp"%>
</body>
</html>
