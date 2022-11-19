<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.example.dao.BoardDAO"%>
<%@ page import="com.example.util.JDBCUtil" %>
<%@ page import="com.example.bean.BoardVO" %>
" %>
<%@ page import="com.example.util.JDBCUtil" %>

<% request.setCharacterEncoding("utf-8"); %>

<%--<jsp:useBean id="u" class="com.example.bean.BoardVO" />--%>
<%--<jsp:setProperty property="*" name="u"/>--%>


<%
	request.setCharacterEncoding("utf-8");
	BoardDAO boardDAO = new BoardDAO();
	JDBCUtil.FileUpload upload = new JDBCUtil.FileUpload();
	BoardVO u = upload.uploadPhoto(request);
	int i = boardDAO.insertBoard(u);
	String msg = "데이터 추가 성공 !!";
	if(i == 0) msg = "[에러] 데이터 추가 ";
%>

<script>
	alert('<%=msg%>');
	location.href='posts.jsp';
</script>