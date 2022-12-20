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
	
	
	<div class="container">
		<div class="row" style="justify-content: space-evenly">
			<%@ include file="../common/aside.jsp"%>
			
		<div class="inputtb content col-lg-8">
			<div class="inputtb content-title pb-4">
				<h3>${board.btitle }</h3>
				<div class="content-multibtn">
					<button class="btn small maincolor" onclick="location.href='/bbs/board/list?page=${currentBoardPage}'">
						목록</button>
					<c:choose>
					<c:when test="${board.uid eq uid}">
						<button class="btn small subcolor" onclick="location.href='/bbs/board/update?bid=${board.bid}'">
						수정</button>
						<button class="btn small subcolor" onclick="location.href='/bbs/board/delete?bid=${board.bid}'">
						삭제</button>
					</c:when>
					<c:otherwise>
						<button class="btn small subcolor" disabled>
						수정</button>
						<button class="btn small subcolor" disabled>
						삭제</button>
					</c:otherwise>
					</c:choose>
				</div>
			</div>

                <div class="row mt-2 mx-1">
                    <div class="borad inputtb content-title py-3">
                    	<div>
                        <p>글번호 : ${board.bid } | 작성일 : ${fn:replace(board.modtime,'T',' ')}</p>
                        <p>첨부파일 : <a href="#" class = "on">cat.jpg</a> / <a href="#" class = "on">test.zip</a></p>
                        </div>
                        <div>
                        <h5>${board.uname }</h5>
                        <p>조회 ${board.viewCnt }&nbsp;&nbsp;댓글 ${board.replyCnt }</p>
                        </div>
                    </div>

                    <div class="content detail py-5">
                        ${board.bcontent }
                    </div>

                    <div class="col-12"><hr></div>
                    <div class="col-12">
                        <div class="d-flex flex-row mt-1">
                            <div class="card bg-light text-dark w-75">
                                <div class="card-body">
                                    마리아&nbsp;&nbsp;2022-05-17 14:30:28<br>    <!-- uname, regTime-->
                                    저도 궁금합니다.😆  <!-- content -->
                                </div>
                            </div>
                        </div>
                        
                        <div class="d-flex flex-row-reverse mt-1">
                            <div class="card w-75">
                                <div class="card-body text-end">
                                    로버트 엘리엇&nbsp;&nbsp;2022-05-17 14:30:28<br>    <!-- uname, regTime-->
                                    email로 문의해 주시면 친절하게 안내해 드릴게요.😄👍😆  <!-- content -->
                                </div>
                            </div>
                        </div>
                            
                        <form class="form-inline" action="/bbs/reply" method="post">
                            <input type="hidden" name="bid" value="">     <!-- bid -->
                            <input type="hidden" name="uid" value="">     <!-- uid -->
                            <table class="table table-borderless mt-2">
                                <tr class="d-flex">
                                    <td class="col-1 text-end">
                                        <label for="content">댓글</label>
                                    </td>
                                    <td class="col-9">
                                        <textarea class="form-control" id="content" name="content" rows="3"></textarea>
                                    </td>
                                    <td class="col-2">
                                        <button type="submit" class="btn btn-primary">등록</button>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>

            </div>
		</div>
	</div>
	<%@ include file="../common/bottom.jsp"%>
</body>
</html>
