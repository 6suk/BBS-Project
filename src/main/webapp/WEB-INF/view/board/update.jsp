<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="../common/heading.jsp"%>
<script src="https://cdn.ckeditor.com/4.18.0/standard/ckeditor.js"></script>
</head>

<body>
	<%@ include file="../common/top.jsp"%>


	<div class="container">
		<div class="row" style="justify-content: space-evenly">
			<%@ include file="../common/aside.jsp"%>

			<div class="inputtb content col-lg-8">
				<!-- 타이틀 -->
				<div class="inputtb content-title pb-4">
					<h3>${binfo.bid }번게시글수정</h3>
					<div class="">
						<button class="btn small subcolor"
							onclick="location.href='<%= BLIST %>?page=${currentBoardPage}'">&lt;
							Back</button>
					</div>
				</div>
				<!-- 타이틀 끝 -->

				<form action="<%=FILEUP%>=update" class="pt-4 mx-3" method="post"
					enctype="multipart/form-data">
					<input type="hidden" name="bid" value="${binfo.bid }">
					<table class="inputtb board-desc">
						<tr>
							<td><input class="board-input" type="text" placeholder="제목"
								name="title" maxlength="128" required value="${binfo.btitle }" /></td>
						</tr>
						<tr>
							<td><textarea class="board-input" name="content"
									placeholder="내용" maxlength="5000" rows="10">${binfo.bcontent }</textarea>
							</td>
						</tr>
						<tr>
							<td><div class="board-input files" style="display: flex;">
									<c:choose>
										<c:when test="${fn:length(fileList) ne 0}">
											<div class="board-input file"
												style="border-right: 1px solid #eee;">
												<label for="files">삭제</label>
													<div>
												<c:forEach var="f" items="${fileList }">
													<div>
														<input type="checkbox" name="delFile" value="${f }" />${f }
													</div>
												</c:forEach>
													</div>
											</div>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
									<div class="board-input file">
										<label for="files">추가</label> <input style="border: none;"
											class="board-input" type="file" name="files" multiple />
									</div>
								</div></td>
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
	<script>
		CKEDITOR.replace('content', {
			filebrowserImageUploadUrl : '/bbs/board/imageupload',
			filebrowserUploadMethod : 'form',
			height : 400
		});
	</script>
</body>
</html>
