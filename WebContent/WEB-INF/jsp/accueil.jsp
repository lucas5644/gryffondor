<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.bo.Article"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico">
 
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<title>Accueil</title>
</head>
<body>

	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<%=request.getContextPath()%>">Accueil</a>
			<div>
				<a href="connexion">Inscription / Se connecter</a>
			</div>
		</div>
	</nav>

	<div class="container">

		<!-- Page Heading -->
		<h1 class="my-4">Site d'enchères de dingue</h1>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
				<div class="card h-100">
					<div class="card-body contenu">
						<div class="contenu">

							<form action="AffichageArticleDeconnecte" method="post"
								class="justify-content-center mb-2">
								<div class="form-group">
									<label for="nomArticle">Nom de l'article</label>
									<input name="nomArticle" class="form-control">
								</div>
								<div class="form-group">
									<label for="nomCategorie"></label> <select name="nomCategorie"
										size="1">
										<option value="">Toutes</option>
										<option value="Informatique">Informatique</option>
										<option value="Ameublement">Ameublement</option>
										<option value="Vêtement">Vêtement</option>
										<option value="Sport et loisir">Sport et loisir</option>
									</select>
								</div>
								<div>
									<input type="submit" value="Rechercher" class="btn btn-primary" />
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
				<div class="card h-100">
					<div class="card-body contenu">
						<div class="contenu">
							<c:forEach var="article" items="${listeArticle}">
								<div class="col-lg-5 d-inline-block contenu portfolio-item">
									<a>Nom de l'article : ${article.nomArticle}</a><br> <a>Prix
										de départ : ${article.miseAPrix}</a><br> <a>Fin de
										l'enchère : ${article.dateFinEncheres}</a><br> <a>Pseudo
										: ${article.vendeur.pseudo}</a>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>

			</div>
		</div>
		<!-- /.row -->
	</div>

</body>

<footer class="py-5 bg-dark">
	<div class="container">
		<p class="m-0 text-center text-white">
			Copyright &copy; Maison Gryffondor 2020 <img
				src="<%=request.getContextPath()%>/images/gryffondor.jpg"
				width="100px" alt="photo Gryffondor">
		</p>

	</div>
	<!-- /.container -->
</footer>
</html>