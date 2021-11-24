<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<style>
body {
background-color: #ccc;}
.container {
margin: 0 auto;
display:flex;
justify-content: space-between;
width: 45%;
border: 1px solid #bbb;

}
.container a {
text-decoration:none;
font-size: 28px;
text-align:center;
text-tranform: uppercase;
color: #f00;
}
</style>
<body>
<div class='container'>
<a href='${pageContext.request.contextPath}/department.jsp'>GERENCIAR DEPARTAMENTOS</a>
<a href='${pageContext.request.contextPath}/seller.jsp'>GERENCIAR VENDEDORES</a>
</div>
</body>
</html>