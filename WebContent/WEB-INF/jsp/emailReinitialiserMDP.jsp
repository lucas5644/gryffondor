<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.bo.Article"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link
	href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/css/4-col-portfolio.css"
	rel="stylesheet">
<link rel="icon"
	href="<%=request.getContextPath()%>/images/gryffondor.jpg">

<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<title> Email pour reinitialiser le mot de passe</title>
</head>
<body>

	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<%=request.getContextPath()%>">Accueil</a>
			<div>
				<a class="btn btn-primary" role="button" href="connexion">Inscription
					/ Se connecter</a>
			</div>
		</div>
	</nav>

	<div class="container">

		<!-- Page Heading -->
		<h1 class="my-4">Email pour reinitialiser le mot de passe</h1>



		<form action= "Email"method="post"class="justify-content-center mb-2">
		<input type="hidden" name="email" value="<%=request.getAttribute("email")%>" />
			<label for="email">Email : </label> <input type="text" placeholder ="exemple@email.fr" name ="email">
			<input type="submit" name="Valider" class="btn btn-primary">
		</form>
		</div>
</body>
</html>