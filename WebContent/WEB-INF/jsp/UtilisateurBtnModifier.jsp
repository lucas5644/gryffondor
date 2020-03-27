<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="ENI Ecole">
<meta name="author" content="ENI Ecole">


<!-- Bootstrap core CSS -->
<link
	href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/css/4-col-portfolio.css"
	rel="stylesheet">
<link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico">

<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">


<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
	<div class="container">
		<a class="navbar-brand"
			href="<%=request.getContextPath()%>/Accueil/Utilisateur">
			Accueil</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarResponsive" aria-controls="navbarResponsive"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>


	</div>

</nav>

<!-- Page Heading -->
<h1 class="my-4">Utilisateur</h1>



					<title>Afficher utilisateur</title>
					<style>
ul {
	list-style-type: none;
}
</style>
</head>
<body>
	<h1>${utilisateur.pseudo}</h1>

	<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
	
			<ul>

				<li>Pseudo : ${userConnected.pseudo}</li>
				<li>Nom : ${userConnected.nom}</li>
				<li>Prénom : ${userConnected.prenom}</li>
				<li>Email : ${userConnected.email}</li>
				<li>Téléphone : ${userConnected.telephone}</li>
				<li>Rue : ${userConnected.rue}</li>
				<li>Code postal : ${userConnected.codePostal}</li>
				<li>Ville : ${userConnected.ville}</li>

			</ul>
			
		</div>
	
	<form action="">
		<input type="submit" name="Modifier" value="Modifier"
			class="btn btn-primary">
	</form>

	<%@ include file="footer.html"%>
	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>


</html>