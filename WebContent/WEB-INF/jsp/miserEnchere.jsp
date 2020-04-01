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
			<a class="navbar-brand" href="<%=request.getContextPath()%>">Accueil</a>
			<div>
				<a class="btn btn-primary" role="button" href="connexion">Inscription
					/ Se connecter</a>
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
							<form action="encherirArticle" method="post">
								<label for="nomArticle">Nom de l'article : </label>
								<a><%=request.getAttribute("nomArticle")%></a><br>
								<label for="description">Description : </label>
								<a><%=request.getAttribute("description")%></a><br>
								<label for="categorie">Catégorie : </label>
								<a><%=request.getAttribute("categorie")%></a><br>
								<label for="meilleureOffre">Meilleure offre : </label>
								<a><%=request.getAttribute("meilleureOffre")%></a><br>
								<label for="prixDepart">Mise à prix : </label>
								<a><%=request.getAttribute("prixDepart")%></a><br>
								<label for="dateFin">Fin de l'enchère : </label>
								<a><%=request.getAttribute("dateFin")%></a><br>
								<label for="lieuRetrait">Retrait : </label>
								<a><%=request.getAttribute("lieuRetrait")%></a><br>
								<label for="vendeur">Vendeur : </label>
								<a><%=request.getAttribute("vendeur")%></a><br>
								<label for="proposition">Ma proposition : </label>
								<input type="number" min="0" step="10" name="proposition"><br>
								<div>
									<input type="submit" value="Enchérir" class="btn btn-primary" />
								</div>
								<input name="numeroArticle" type="hidden" value="<%=request.getAttribute("numeroArticle")%>">
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