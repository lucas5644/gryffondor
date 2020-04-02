<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Enchérir</title>
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
	<div class="container">
		<!-- Page Heading -->
		<h1 class="my-4">Placer une enchère</h1>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
				<div class="card h-100">
					<div class="card-body contenu">
						<div class="contenu">
							<%
								if (request.getAttribute("listeCodesErreur") != null) {
									List<Integer> listeCodesErreur = (List<Integer>) request.getAttribute("listeCodesErreur");
							%>
							<div class="alert alert-danger" role="alert">
								<strong>Erreur!</strong>
								<ul>
									<%
										for (Integer code : listeCodesErreur) {
									%>

									<li><%=LecteurMessage.getMessageErreur(code)%></li>
									<%
										}
									%>
								</ul>
							</div>
							<%
								}
							%>
							<form action="encherirArticle" method="post">
								<label for="nomArticle">Nom de l'article : </label> <a>${articleCourant.nomArticle}</a><br>
								<label for="description">Description : </label> <a>${articleCourant.description}</a><br>
								<label for="categorie">Catégorie : </label> <a>${articleCourant.categorieArticle.libelle}</a><br>
								<label for="meilleureOffre">Meilleure offre : </label> <a><%=request.getAttribute("meilleureOffre")%></a><br>
								<label for="prixDepart">Mise à prix : </label> <a>${articleCourant.miseAPrix}</a><br>
								<label for="dateFin">Fin de l'enchère : </label> <a>${articleCourant.dateFinEncheres}</a><br>
								<label for="lieuRetrait">Retrait : </label> <a>${articleCourant.lieuRetrait}</a><br>
								<label for="vendeur">Vendeur : </label> <a>${articleCourant.vendeur.pseudo}</a><br>
								<label for="proposition">Ma proposition : </label> <input
									type="number" min="0" step="10" name="proposition"><br>
								<input type="hidden" name="noArticle"
									value="<%=request.getAttribute("noArticle")%>" />
								<div>
									<input type="submit" value="Enchérir" class="btn btn-primary" />
								</div>
								<input name="numeroArticle" type="hidden"
									value="<%=request.getAttribute("numeroArticle")%>">
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container -->
</body>
<%@ include file="footer.html"%>
</html>