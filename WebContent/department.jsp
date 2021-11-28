<%@page import="models.Department"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/departamentos" var="dep"/>
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
<h1>Departamentos</h1>
<form method='get' action='${dep}/listar'><button>Listar todos os departamentos</button></form><br>
<form method="post" action="${dep}/inserir">
<h2>Você pode inserir aqui</h2>
<label>
Nome do novo departamento:
<input name="departamento" type='text'>
<button>INSERIR</button>
</label>
</form>
<form method='get' action='${dep}/listarPorId'>
<h2>Procure um departamento específico por ID:</h2>
<LABEL>ID: <input type='text' name='id'></LABEL>
<button>Procurar por ID</button>
</form><br>
<c:if test="${not empty desejaEditar }">
<form method='post' action='${dep}/editar'>
<h2 style='color:#f00'>Edite aqui o nome do departamento</h2>
<input type='number' name='id' value='${id}' style='display:none'>
<label>Novo nome do departamento de id ${id}: <input value='${nomeDepartamento}' type='text' name='nome'></label>
<button style='color:#fff;background-color:green;'>Editar</button>
</form>
</c:if>
<div class="table-responsive d-block d-md-table">
	<table class="table
        table-hover
        table-striped
        table-bordered">
        <caption>DEPARTAMENTOS</caption>
        <c:if test="${not empty listaDepartment}">
        <tr>
        	<th>ID</th>
			<th>Departamento</th>
			<th>Editar</th>
			<th>Excluir</th>
		</tr>
		</c:if>
	<c:forEach items="${listaDepartment}" var="department">
		<tr>
			<td>${department.id }</td>
			<td>${department.name}</td>
			<td>
				<form method="GET" action="${dep}/editar_campo">
					<input type='text' value='${department.name }' name='nome'style='display:none'>
					<input type='number' name='id'value='${department.id}' style='display:none'>
					<button style='color:#fff;background-color:green;'>Editar</button>
				</form>
			</td>
			<td>
				<form method="POST" action="${dep}/deletar">
					<input type='number' name='id'value='${department.id}' style='display:none'>
					<button style='color:#fff;background-color:#f00;'>Excluir</button>
				</form>
			</td>
		</tr>
	</c:forEach>
	</table>
</div>
</div>
	<c:if test="${not empty nao_permitido }">
		<script>
		alert("Não é permitido excluir um departamento que tenha vendedores !");
		</script>
	</c:if>
	<script src='bootstrap_js/bootstrap.bundle.min.js'></script>
</body>
</html>