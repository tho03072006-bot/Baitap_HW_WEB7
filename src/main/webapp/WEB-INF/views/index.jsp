<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Bai tap HW WEB7</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="container mt-5">
		<h2 class="mb-4">Bai tap HW WEB7 - CRUD API + Swagger + AJAX</h2>
		<div class="list-group">
			<a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/categories">Quan ly Category (AJAX)</a>
			<a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/products">Quan ly Product (AJAX)</a>
			<a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/swagger-ui/index.html" target="_blank">Swagger UI (OpenAPI 3)</a>
		</div>
	</div>
</body>
</html>
