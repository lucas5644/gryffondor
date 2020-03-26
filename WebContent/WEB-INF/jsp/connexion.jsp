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
<link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico">

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
							<form action="inscrire" method="post">
								<input type="submit" value="S'inscrire">
							</form>
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
		<p class="m-0 text-center text-white">Copyright &copy; Maison
			Gryffondor 2020	<img src="<%=request.getContextPath()%>/images/gryffondor.jpg"
			width="100px" alt="photo Gryffondor">
		</p>
	</div>
	<!-- /.container -->
</footer>
</html>