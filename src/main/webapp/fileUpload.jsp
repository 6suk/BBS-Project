<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        th, td { text-align: center; }
    </style>
    <script>
    	var index = 1;
    	$(document).ready(function() {
    		$('#btn').on('click', function(e) {
    			$('<input></input>')
    				.attr({type: 'file'; name: 'file'+index++})
    				.appendTo('#additionalFile');
    		});
    	});
    </script>
</head>

<body>
    <%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>파일 업로드</strong></h3>
            	<hr>
                <div class="row">
			        <div class="col-1"></div>
			        <div class="col-10">
					    <form action="/bbs/board/upload" method="post" enctype="multipart/form-data">
					    	<table class="table table-borderless">
					    		<tr>
					    			<td>param:</td>
					    			<td><input type="text" name="param"></td>
					    		</tr>
					    		<tr>
					    			<td>파일:</td>
					    			<td><input type="file" name="file1">
					    		</tr>
					    		<tr>
					    			<td>파일:</td>
					    			<td><input type="file" name="file2">
					    		</tr>
					    		<tr>
					    			<td colspan="2"><input type="submit" class="btn maincolor" value="Upload"></td>
					    		</tr>
					    	</table>
					        <div id="additionalFile"></div>
					    </form>		            
					        
			        </div>
			        <div class="col-1"></div>
			    </div>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>