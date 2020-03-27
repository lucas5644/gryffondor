<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="java.util.List"%>
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
<link rel="icon"
	href="<%=request.getContextPath()%>/images/favicon.ico">

<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<title>Mon Profil</title>
</head>
<body>
<% Utilisateur userConnected = (Utilisateur)session.getAttribute("userConnected");
								String iduserConnected = userConnected.getumUtilisateur();
							%>
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<%=request.getContextPath()%>">Application
				Suivi de repas</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="nav-link"
						href="<%=request.getContextPath()%>">Accueil <span
							class="sr-only">(current)</span>
					</a></li>
					
				</ul>
			</div>
		</div>
	</nav>

	<!-- Page Content -->
	<div class="container">

		<!-- Page Heading -->
		<h1 class="my-4"></h1>

		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
				<div class="card h-100">
					<div class="card-body contenu">
						<div class="contenu">
							<%
								if(request.getAttribute("listeCodesErreur")!= null){
								List<Integer> listeCodesErreur = (List<Integer>)request.getAttribute("listeCodesErreur") ;
								%>
								<div class="alert alert-danger" role="alert">
									<strong>Erreur!</strong>
									<ul>
										<%for(Integer code : listeCodesErreur) {%>
										
											<li><%=LectureMessages.getMessageErreur(code)%></li>
										<%} %>
									</ul>
								</div>
							<%} %>

							<form action= "UpdateUser"
								method="post" class="justify-content-center mb-2">
								<div class="saisie">
									<label for="pseudo"> Pseudo: </label>
										<input type="text" name="pseudo" id="pseudo" value="<%=userConnected.getPseudo() %>"/>
								</div>
								<div class="saisie">
									<label for="nom">Nom : </label>
           							<input type="text" name="nom" id="nom" />
								</div>
								<div class="saisie">
									 <label for="prenom">Prénom : </label>
                                     <input type="text" name="prenom" id="prenom" />
								</div>
								<div>
								      <label for="email">Email : </label>
                                      <input type="text" name="email" id="email" />
								</div>
								<div>
							          <label for="telephone">Téléphone : </label>
                                      <input type="text" name="telephone" id="telephone" />
								</div>
								<div>
								
								</div>
								      <label for="rue">Rue : </label>
                                      <input type="text" name="rue" id="rue" />
								<div>
								<div>
									 <label for="codePostal">Code postal : </label>
                                     <input type="text" name="codePostal" id="codePostal" />
								</div>
								<div>
								     <label for="ville">Ville : </label>
                                     <input type="text" name="ville" id="ville" />
								</div>
								<div>
								      <label for="mot de passe actuel">Mot de passe actuel : </label>
                                      <input type="text" name="nom" id="mot de passe actuel" />
								</div>
								<div>
								      <label for="nouveau mot de passe">Nouveau mot de passe : </label>
                                      <input type="text" name="nom" id="neuveau mot de passe" />
								</div>
								<div>
									  <label for="confirmation">Confirmation : </label>
                                      <input type="text" name="nom" id="confirmation" />
								</div>
									<input type="submit" value="Enregistrer" class="btn btn-primary" />
									
								</div>
							</form>
							
							<!-- -Pour le doPost -->
							<form action="DeleteUser" method ="post">
							
							
									<input type="hidden" name="noUtilisateur" value="<%=iduserConnected%>"/>
									<input type="submit" value="Supprimer Utilisateur" class="btn btn-primary" />
							</form>
							<!-- Pour le doGet -->
							<a href="<%=request.getContextPath()/DeleteUser?noUtilisateur="+iduserConnected%>"><input
										type="button" value="Suprimer mon compte" class="btn btn-primary" /></a>

						</div>
					</div>
				</div>
			</div>

		</div>
		<!-- /.row -->

	</div>
	<!-- /.container -->

	<!-- Footer -->
	<footer class="py-5 bg-dark">
		
	</footer>

	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>

