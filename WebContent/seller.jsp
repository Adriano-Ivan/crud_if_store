<%@page import="models.Seller"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:url value="/sellers" var="sellers"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Department</title>
<link rel='stylesheet' href='bootstrap_css/boostrap.min.css'>
<style>
body {
background-color: #ccc;}
table, th, td,caption{
border:1px solid black;
border-collapse:collapse;
padding:8px;
}
a {
text-decoration:none;
font-size: 22px;
text-align:center;
text-tranform: uppercase;
color: #f00;
}
</style>
</head>

<body>
<a href='${pageContext.request.contextPath}'>VOLTAR PARA INDEX</a>
<div class='container'>
<h1>Vendedores</h1>
<form method='get' action='${sellers}/listar'><button>Listar todos os vendedores</button></form><br>
<!--  
<form method="post" action="${sellers}/inserir">
<h2>Você pode inserir aqui</h2>
<label>
Nome do novo departamento:
<input name="departamento" type='text'>
<button>INSERIR</button>
</label>
</form>
-->

<form method='get' action='${sellers}/listarPorId'>
<h2>Procure um vendedor específico por ID:</h2>
<LABEL>ID: <input type='text' name='id'></LABEL>
<button>Procurar por ID</button>
</form><br>
<c:if test="${not empty for_dep }">
<form method='get' action='${sellers}/listarPorDep'>
<h2>Procure um vendedor específico por departamento:</h2>
<LABEL>
<select name="id_dep">
<c:forEach items="${for_dep}" var="dep">
<option value='${dep.id }'>${dep.name }</option>
</c:forEach>
</select>
</LABEL>
<button>Procurar por departamento</button>
</form><br>
</c:if>
<!-- 
<c:if test="${not empty desejaEditar }">
<form method='post' action='${dep}/editar'>
 
<h2 style='color:#f00'>Edite aqui o nome do departamento</h2>
<input type='number' name='id' value='${id}' style='display:none'>
<label>Novo nome do departamento de id ${id}: <input value='${nomeDepartamento}' type='text' name='nome'></label>
<button style='color:#fff;background-color:green;'>Editar</button>
</form>

</c:if>
-->
<form method='get' action='${sellers}/inserir_campo'>
<button style='color:#fff;background-color:green;'>Inserir elemento</button>
</form><br>
<div class="table-responsive d-block d-md-table">
	<table class="table
        table-hover
        table-striped
        table-bordered">
        <caption>Vendedores</caption>
        <c:if test="${not empty listaSellers}">
        <tr>
        	<th>ID</th>
			<th>Nome</th>
			<th>Email</th>
			<th>Data de nascimento</th>
			<th>Salário-base</th>
			<th>Departamento</th>
			<th>Editar</th>
			<th>Excluir</th>
		</tr>
		</c:if>
	<c:forEach items="${listaSellers}" var="seller">
		<tr>
			<td>${seller.id }</td>
			<td>${seller.name}</td>
			<td>${seller.email }</td>
			<fmt:parseDate value="${seller.birthdate}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>
			<td><fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy"/></td>
			<td>${seller.baseSalary }</td>
			<td>${seller.department.name }</td>
			<td>
				<form method="GET" action="${sellers}/editar_campo">
					<input type='number' name='id'value='${seller.id}' style='display:none'>
					<button style='color:#fff;background-color:green;'>Editar</button>
				</form>
			</td>
			<td>
				<form method="POST" action="${sellers}/deletar">
					<input type='number' name='id'value='${seller.id}' style='display:none'>
					<button style='color:#fff;background-color:#f00;'>Excluir</button>
				</form>
			</td>
		</tr>
	</c:forEach>
	</table>
</div>
</div>
	<script src='bootstrap_js/bootstrap.bundle.min.js'></script>
</body>
</html>