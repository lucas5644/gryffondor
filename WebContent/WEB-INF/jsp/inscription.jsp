<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
<title>Inscription</title>
</head>


<h1 style="display-text:center">Inscription</h1>
<body>

	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<%=request.getContextPath()%>">Accueil
				Enchère</a>
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
							<form action="inscriptionUser" method="post">
							<div class="col-lg-6 col-md-6 col-sm-6 portfolio-item">
								<a>Prenom : </a> <input type="text" value="" name="prenom">

								<a>Nom : </a> <input type="text" value="" name="nom"> <br>
							</div>
							<div class="col-lg-6 col-md-6 col-sm-6 portfolio-item">
								<a>Pseudo : </a> <input type="text" value="" name="pseudo">
								<%if(request.getAttribute("erreurPseudo")!=null) {%>
									<a style="color:red">Le pseudo est déja utilisé.</a>
								<%}%>
							

								<a>Email : </a> <input type="text" value="" name="email">
								<br>
							</div>
							<div class="col-lg-6 col-md-6 col-sm-6 portfolio-item">
								 <a>Téléphone : </a> <input type="text" value=""
									name="telephone"> <a>Rue : </a> <input type="text"
									value="" name="rue"> <br><br>
								 <a>Ville : </a><input
									type="text" value="" name="ville">
									 <a>Code Postal :</a> <input type="text" value="" name="cp"> <br> <br><a>Mot
									de Passe</a> <input type="password" value="" name="mdp"> <input
									type="submit">
							</div>
							</form>
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