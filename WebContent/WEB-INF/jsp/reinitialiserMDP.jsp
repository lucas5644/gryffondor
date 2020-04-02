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
<title>Reinitialiser mot de passe</title>
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
		<h1 class="my-4">Reinitialiser mot de passe</h1>
		<p>Enregistre votre nouveau mot de passe</p>
	<form action="ReinitialisationMDP" method="post" class="justify-content-center mb-2">
		<div>
			<label for="email">Email : </label> <input
				type="text" placeholder="email@mail.fr" name="email" id="email" />
		
		</div>
		<div>
			<label for="nouveau mot de passe">Nouveau mot de passe : </label> <input
				type="password" placeholder="mot de passe" name="nouveauMotDePasse" id="neuveau mot de passe" />
		</div>
		<div>
			<label for="confirmation">Confirmation : </label> <input
				type="password" placeholder="confirmation" name="cNouveauMotDePasse" id="confirmation" />
		</div>
		<div>
			<input type="submit" value="Enregistrer" class="btn btn-primary" />
		</div>
	</form>

</div>
</body>
</html>