<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<div class="inputtb content-title pb-4">
				<h3>게시글 쓰기</h3>
				<div class="">
					<button class="btn small subcolor" onclick="location.href='<%= LIST %>'">
						Home</button>
				</div>
			</div>

			<form action="/bbs/board/write" class="pt-3" method="post">
				<table class = "inputtb content-desc">
					<tr>
						<!-- <td><label for="title">제목</label></td> -->
						<td><input type="text" placeholder="*제목" name="title" maxlength="128" required /></td>
					</tr>
					<tr>
						<!-- <td><label for="content">내용</label></td> -->
						<td><textarea name="content" placeholder="내용" maxlength="5000" rows= "10" required></textarea></td>
					</tr>
					<tr>
						<!-- <td><label for="files">첨부파일</label></td> -->
						<td><input type="text" placeholder="첨부파일" name="files" id="files"></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="submit" class= "btn full maincolor" value="글쓰기" />
						<input type="reset" class= "btn full subcolor" value="취소" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	</div>
	<%@ include file="../common/bottom.jsp"%>
</body>
</html>
