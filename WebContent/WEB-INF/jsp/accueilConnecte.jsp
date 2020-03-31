<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Accueil</title>
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


</head>
<body>
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand"
				href="<%=request.getContextPath()%>/AccueilUtilisateur">Mon
				application d'enchères</a>

			<div>
				<a class="btn btn-primary nav-link" role="button"
					href="AjouterArticle">Vendre un Article</a>
			</div>
			<div>
				<a class="btn btn-primary nav-link" role="button" href="monprofil">Mon
					profil</a>
			</div>
			<div>
				<a class="btn btn-primary nav-link" role="button" href="deconnexion">Déconnexion</a>
			</div>
		</div>
	</nav>

	<div class="container">

		<!-- Page Heading -->
		<h1 class="my-4">
			Bienvenue ${userConnected.prenom} ${userConnected.nom} <img
				alt="icône connecté"
				src="<%=request.getContextPath()%>/images/connected.png"
				width="40px">
		</h1>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
				<div class="card h-100">
					<div class="card-body contenu">
						<div class="contenu">

							<form action="RechercheArticleConnecte" method="post"
								class="justify-content-center mb-2">
								<div class="form-group">
									<label for="nomArticle">Nom de l'article</label> <input
										name="nomArticle" class="form-control">
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
								<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
									<div class="col-lg-6 col-md-6 col-sm-6 portfolio-item">
										<input type="radio" name="mode" id="modeAchat" value="modeAchat" checked> <label
											for="modeAchat">Achats</label><br> 
										<input type="checkbox" id="achatOuvert" name="achatOuvert" value="achatOuvert"> <label for="achatOuvert">Enchère
											Ouvertes</label><br> 
										<input type="checkbox" id="achatEnCours"
											name="achatEnCours" value="achatEnCours"> <label
											for="achatEnCours">Mes enchères en Cours</label><br> 
										<input type="checkbox" id="achatRemport" name="achatRemport"
											value="achatRemport"> <label for="achatRemport">Mes Enchères Remportées</label><br>

									</div>

									<div class="col-lg-6 col-md-6 col-sm-6 portfolio-item">
										<input type="radio" name="mode" id="modeVente" value="modeVente"> <label
											for="modeVente">Mes Ventes</label> 
										<input type="checkbox"
											id="venteEnCours" name="venteEnCours" value="venteEnCours">
											<label for="venteEnCours">Mes Ventes en cours</label><br> 
										<input type="checkbox" id="venteNonDebutees" name="venteNonDebutees"
											value="venteNonDebutees"> <label for="venteNonDebutees">Ventes non débutées</label><br> 
										<input type="checkbox"
											id="venteTerminees" name="venteTerminees" value="venteTerminees">
											<label for="venteTerminees">Ventes terminées</label><br>
									</div>
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
							<c:forEach var="article" items="${ListeResultat}">
								<form action="RedirectionClicArticle" method="post">
									<div class="col-lg-5 d-inline-block contenu portfolio-item">
									<input type="hidden" name="article" value="${article.vendeur.noUtilisateur}">
										 <a>Nom de l'article :</a><input type="submit" value="${article.nomArticle}"><br> <a>Prix
											de départ : ${article.miseAPrix}</a><br> <a>Fin de
											l'enchère : ${article.dateFinEncheres}</a><br> <a>Pseudo
											: ${article.vendeur.pseudo}</a>
									</div>
								</form>
							</c:forEach>
						</div>
					</div>
				</div>

			</div>
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container -->
	<%@ include file="footer.html"%>
	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>

</html>