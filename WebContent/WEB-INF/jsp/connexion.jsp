<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>S'inscrire / Se connecter</title>

<link
	href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/css/4-col-portfolio.css"
	rel="stylesheet">
<link rel="icon" href="<%=request.getContextPath()%>/images/gryffondor.jpg">

<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
</head>




<body>
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<%=request.getContextPath()%>">Accueil
				Enchère</a>
			<div></div>
		</div>
	</nav>


	<div class="container">

		<!-- Page Heading -->
		<br></br>
		<c:out value="${pseudo }"/>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
				<div class="card h-100">
					<div class="card-body contenu">
						<div class="contenu">
							<form action=tryConnexion method="post">
								<label for="pseudo" class="bar">Pseudo</label>
								<input type="text" value="" name="pseudo"> <label
									for="mdp" class="bar">Mot de Passe</label>
								<input type="password" value="" name="mdp"> <input
									type="submit" value="Connexion">
							<div class="contenu">
							<input type="checkbox" name="seSouvenirDeMoi" value="seSouvenirDeMoi">
							<label for ="seSouvenirDeMoi"> Se souvenir de moi</label>
						</div>
							</form>
						</div>
						<div class="contenu"> 
							<a href="inscription">Inscription</a>
								
						</div>
						<div class="contenu">
							<a href="emailReinitialiserMDP">Mot de passe oublié</a>
						</div>
						
					</div>
				</div>
			</div>

		</div>
		<!-- /.row -->
	</div>
</body>
 <%@ include file="footer.html"  %>
</html>