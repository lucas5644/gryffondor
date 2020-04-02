<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
							</form>
						</div>
						<div class="contenu"> 
							<a href="inscription">Inscription</a>
						</div>
						<div class="contenu">
							<a href="">Mot de passe oublié</a>
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