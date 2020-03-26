<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico">

<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
	
	
</head>
<h1>Bienvenu</h1>
<body>
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<%=request.getContextPath()%>">Accueil
				Enchère</a>
			<div>
				<a href="">Vendre un Article</a>
			</div>
			
			<div>
				<a href="">Mon profil</a>
			</div>
			
			<div>
				<a href="deconnexion">Déconnexion</a>
			</div>
		</div>
	</nav>

<p>Bonjour Mr ${userConnected.nom}</p>


	<div class="container">

		<!-- Page Heading -->
		<br></br>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
				<div class="card h-100">
					<div class="card-body contenu">
						<div class="contenu">
							
								<!-- On fait ce qu'on veut ici  -->

						</div>
					</div>
				</div>
			</div>

		</div>
		<!-- /.row -->
	</div>
</body>








</body>



<footer class="py-5 bg-dark">
	<div class="container">
		<p class="m-0 text-center text-white">Copyright &copy; Maison
			Gryffondor 2020	<img src="<%=request.getContextPath()%>/img/gryffondor.jpg"
			width="100px" alt="photo Gryffondor">
		</p>
	</div>
	<!-- /.container -->
</footer>
</html>