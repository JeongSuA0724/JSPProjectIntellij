<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.example.dao.BoardDAO"%>
<%@ page import="com.example.util.JDBCUtil" %>
<%@ page import="com.example.bean.BoardVO" %>

<% request.setCharacterEncoding("utf-8"); %>

<%--<jsp:useBean id="u" class="com.example.bean.BoardVO" />--%>
<%--<jsp:setProperty property="*" name="u"/>--%>

<%
	BoardDAO boardDAO = new BoardDAO();
	JDBCUtil.FileUpload upload = new JDBCUtil.FileUpload();
	BoardVO u = upload.uploadPhoto(request);
	int i=boardDAO.updateBoard(u);
	response.sendRedirect("posts.jsp");
%>