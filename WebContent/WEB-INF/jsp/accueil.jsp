<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.bo.Article"%>
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

							<!-- ici mettre la liste Colonne 1  -->
							<form action="AffichageArticleDeconnecte" method="post"
								class="justify-content-center mb-2">
								<div class="form-group">
									<label for="nomArticle">Nom de l'article</label>
									<textarea name="nomArticle" class="form-control"></textarea>
								</div>
								<div class="form-group">
									<label for="nomCategorie"></label> <select name="nomCategorie"
										size="1">
										<option value="1">Informatique
										<option value="2">Ameublement
										<option value="3">Vêtement
										<option value="4">Sport et loisir
									</select>
								</div>
								<div>
								
									<a href="<%=request.getContextPath()%>/accueil"> <input
										type="submit" value="Rechercher" class="btn btn-primary" /></a>
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
							<%
								if (request.getAttribute("listeArticle") != null) {
									List<Article> listeArticle = (List<Article>) request.getAttribute("listeArticle");
									if (listeArticle.size() > 0) {
							%>
							<table class="table">
								<thead class="thead-dark">
									<tr>
										<th scope="col">Nom de l'article :</th>
										<th scope="col">Prix :</th>
										<th scope="col">Fin de l'enchère</th>
										<th scope="col">Vendeur</th>
									</tr>
								</thead>
								<tbody>
									<%
										for (Article article : listeArticle) {
									%>

									<tr>
										<td><%=article.getNomArticle()%></td>
										<td><%=article.getMiseAPrix()%></td>
										<td><%=article.getDateFinEncheres().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
										<td><%=article.getVendeur().getPseudo()%></td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
							<%
								} else {
							%>

							<div class="alert alert-info" role="alert">
								<strong>Il n'y a aucun article à afficher</strong>
							</div>
							<%
								}
								}
							%>

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