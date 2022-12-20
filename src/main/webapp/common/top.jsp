<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%!
	private static final String LIST = "/bbs/user/list", LIST_VIEW = "/bbs/user/list.jsp",
	LOGIN = "/bbs/user/login", LOGIN_VIEW = "/bbs/user/login.jsp", LOGOUT = "/bbs/user/logout",
	REG = "/bbs/user/register", REG_VIEW = "/bbs/user/register.jsp", UPDATE = "/bbs/user/update",
	UPDATE_VIEW = "/bbs/user/update.jsp", DEL = "/bbs/user/delete", MSG = "/bbs/user/msg.jsp",
	BOARD = "/bbs/board/list";
%>

<nav class="navbar navbar-expand navbar-light fixed-top">
      <div class="container-md px-md-5">
        <ul class="navbar-nav">
          <a class="navbar-brand" href="<%= LIST %>">
          <img src="../img/logo5.png" class="logo"/>
          </a>
          <li class="nav-item">
            <a class="nav-link" href="<%= LIST %>">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link ${menu eq 'board' ? 'active' : ''}" href="<%= BOARD %>">게시판</a>
          </li>
          <li class="nav-item">
            <a class="nav-link ${menu eq 'user' ? 'active' : ''}" href="<%= LIST %>">사용자</a>
          </li>
        </ul>
        <c:choose>
        <c:when test="${empty uid}">
        <ul class="navbar-nav">
          <li class="nav-item" style="font-weight: 800">
            <a class="nav-link" href="<%= LOGIN %>">로그인</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%= REG %>">회원가입</a>
          </li>
        </ul>
        </c:when>
        <c:otherwise>
          <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="<%= LOGOUT %>">로그아웃</a>
          </li>
        </ul>
        </c:otherwise>
        </c:choose>
        
        
        
      </div>
    </nav>