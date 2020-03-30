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
<link rel="icon" href="<%=request.getContextPath()%>/images/gryffondor.jpg">

<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">




<!-- Page Heading -->
		<h1 class="my-4">Utilisateur</h1>

		
<title> Afficher  utilisateur</title>
<style>
	ul{
		list-style-type: none;
	}

</style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="nav-link"
						href="<%=request.getContextPath()%>/AccueilUtilisateur">Accueil <span
							class="sr-only">(current)</span>
					</a></li>
					
				</ul>
			</div>
		</div>
	</nav>
	
	<h1>${utilisateur.pseudo} </h1>
	
	
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
	
	
</body>

 <%@ include file="footer.html"  %>

	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>


</html>