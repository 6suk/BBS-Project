<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%!
	private static final String LIST = "/bbs/user/list", LIST_VIEW = "/bbs/user/list.jsp",
	LOGIN = "/bbs/user/login", LOGIN_VIEW = "/bbs/user/login.jsp", LOGOUT = "/bbs/user/logout",
	REG = "/bbs/user/register", REG_VIEW = "/bbs/user/register.jsp", UPDATE = "/bbs/user/update",
	UPDATE_VIEW = "/bbs/user/update.jsp", DEL = "/bbs/user/delete", MSG = "/bbs/user/msg.jsp",
	BOARD = "/board/list", BOARD_VIEW = "/board/list.jsp";
%>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
	<div class="container-fluid">
		<ul class="navbar-nav">
			<a class="navbar-brand ms-5 me-5" href="#"> <img
				src="../img/ckworld-logo.png" alt="Logo" style="height: 36px;"
				class="rounded-3">
			</a>
			<li class="nav-item"><a class="nav-link" href="#"><i
					class="fa-solid fa-house"></i> Home</a></li>
			<li class="nav-item ms-3">
			<a class="nav-link ${menu eq 'board' ? 'active' : ''}" href="#"><i
					class="far fa-list-alt"></i> 게시판</a></li>
			<li class="nav-item ms-3">
			<a class="nav-link ${menu eq 'user' ? 'active' : ''}" href="<%=LIST%>"><i
					class="fas fa-user-friends"></i> 사용자</a></li>
			<li class="nav-item ms-3"><a class="nav-link" href="<%=LOGOUT%>"><i
					class="fas fa-sign-out-alt"></i> 로그아웃</a></li>
		</ul>
		<span class="navbar-text me-3">${uname}님 환영합니다.</span>
	</div>
</nav>