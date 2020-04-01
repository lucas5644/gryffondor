<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap core CSS -->
<link
	href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/css/4-col-portfolio.css"
	rel="stylesheet">
<link rel="icon" href="<%=request.getContextPath()%>/images/gryffondor.jpg">

<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<title>Nouvelle vente</title>
</head>
<body>
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand"
				href="<%=request.getContextPath()%>/AccueilUtilisateur">Mon
				application d'enchères</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="nav-link"
						href="<%=request.getContextPath()%>/AccueilUtilisateur">Accueil
							<span class="sr-only">(current)</span>
					</a></li>

				</ul>
			</div>
		</div>
	</nav>
	<!-- Page Content -->
	<div class="container">
		<!-- Page Heading -->
		<h1 class="my-4">
			Nouvelle vente <img alt="photo vente" width="100px"
				src="<%=request.getContextPath()%>/images/handshake.png">
		</h1>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
				<div class="card h-100">
					<div class="card-body contenu">
						<div class="contenu">
							<form action="ajoutArticle" method="post" encType="multipart/form-data">
								<div class="saisie">
									<label for="article">Article : </label> <input
										class="form-control" name="article">
								</div>
								<div class="saisie">
									<label for="description">Description : </label>
									<input class="form-control" name="description">
								</div>
								<br>
								<div>
									<label for="fichier">Insérez une Image</label>
									<input type="file" name="fichier" id="fichier"/>
								</div>
								<br>
								<div>
									<label for="categorie">Catégorie : </label> <select
										name="categorie" size="1">
										<option value="1">Informatique</option>
										<option value="2">Ameublement</option>
										<option value="3">Vêtement</option>
										<option value="4">Sport et loisir</option>
									</select>
								</div>
								<div>
									<label for="prixDepart">Mise à prix : </label> <input type="number"
										name="prixDepart" min="0" step="10">
								</div>
								<div>
									<label for="dateDebut">Début de l'enchère</label> <input
										class="form-control" type="date" name="dateDebut">
								</div>
								<div>
									<label for="dateFin">Fin de l'enchère</label> <input
										class="form-control" type="date" name="dateFin">
								</div>
								<div>
									<br>
									<h3>Retrait</h3>
									<label for="rue">Rue : </label> <input class="form-control"
										name="rue" value="${userConnected.rue}"> <label
										for="codePostal">Code postal : </label> <input
										class="form-control" name="codePostal"
										value="${userConnected.codePostal}"> <label
										for="ville">Ville : </label> <input class="form-control"
										name="ville" value="${userConnected.ville}">
								</div>
								<br>

								<div>
									<input type="submit" value="Enregistrer"
										class="btn btn-primary" /> <a
										href="<%=request.getContextPath()%>"><input type="button"
										value="Annuler" class="btn btn-primary" /></a>
								</div>
							</form>
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