
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:url value="/sellers" var="sellers"/>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Inserção de vendedor</title>
</head>
<style>

body {
background-color: #ccc;}
label {
margin: 10px;
display:block;
}
a {
text-decoration:none;
font-size: 22px;
text-align:center;
text-tranform: uppercase;
color: #f00;
}
</style>
<body>
<a href='${pageContext.request.contextPath}'>VOLTAR PARA INDEX</a>
<form method="POST" action="${sellers}/editar">
<h1>Insira as informações do novo vendedor: </h1>
<input type='number' value='${seller.id}' style='display:none' name='id'>
<label>
Nome:
<input type='text' value='${seller.name}' name='nome'>
</label><br>
<label>
Email:
<input type='email' value='${seller.email }' name='email'>
</label><br>
<label>
Data de Nascimento:
<fmt:parseDate value="${seller.birthdate}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>
<input type='date' value="<fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd"/>" name='dataNascimento'>
</label><br>
<label>
Salário Base:
<input type='text' value='${seller.baseSalary }' name='salarioBase'>
<br>
<c:if test="${not empty invalido }">
<div style='padding:15px; background-color:red;color:#fff;text-align:center;'>Digite um valor numérico válido</div>
</c:if>
</label><br>
<label>
<c:if test="${not empty for_dep }">
Departamento:
<select name="id_dep">
<c:forEach items="${for_dep}" var="dep">
<option value='${dep.id }'>${dep.name }</option>
</c:forEach>
</select>
</c:if>
</label><br>
<button style='margin-left:20px; margin-top:10px;'>Enviar</button>
</form>
</body>
</html>