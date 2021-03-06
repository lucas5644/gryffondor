<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.bo.Utilisateur"%>
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
<title>Mon Profil</title>
</head>
<body>
<% Utilisateur userConnected = (Utilisateur)session.getAttribute("userConnected");
								String iduserConnected = String.valueOf(userConnected.getNoUtilisateur());
							%>
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<%=request.getContextPath()%>/AccueilUtilisateur">Mon application d'enchères</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
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
										
											<li><%=LecteurMessage.getMessageErreur(code)%></li>
										<%} %>
									</ul>
								</div>
							<%} %>

							<form action= "updateUtilisateur"
								method="post" class="justify-content-center mb-2">
								<div class="saisie">
									<label for="pseudo"> Pseudo: </label>
										<input type="text" name="pseudo" id="pseudo" value="<%=userConnected.getPseudo() %>"/>
								</div>
								<div class="saisie">
									<label for="nom">Nom : </label>
           							<input type="text" name="nom" id="nom" value="${userConnected.nom}"/>
								</div>
								<div class="saisie">
									 <label for="prenom">Prénom : </label>
                                     <input type="text" name="prenom" id="prenom" value="${userConnected.prenom}" />
								</div>
								<div>
								      <label for="email">Email : </label>
                                      <input type="text" name="email" id="email" value="${userConnected.email}" />
								</div>
								<div>
							          <label for="telephone">Téléphone : </label>
                                      <input type="text" name="telephone" id="telephone" value="${userConnected.telephone}" />
								</div>
								<div>
								
								</div>
								      <label for="rue">Rue : </label>
                                      <input type="text" name="rue" id="rue" value="${userConnected.rue}" />
								<div>
								<div>
									 <label for="codePostal">Code postal : </label>
                                     <input type="text" name="codePostal" id="codePostal" value="${userConnected.codePostal}" />
								</div>
								<div>
								     <label for="ville">Ville : </label>
                                     <input type="text" name="ville" id="ville" value="${userConnected.ville}" />
								</div>
								<div>
								      <label for="mot de passe actuel">Mot de passe actuel : </label>
                                      <input type="password" name="motDePasse" id="mot de passe actuel" />
								</div>
								<div>
								      <label for="nouveau mot de passe">Nouveau mot de passe : </label>
                                      <input type="password" name="nouveauMotDePasse" id="neuveau mot de passe" />
								</div>
								<div>
									  <label for="confirmation">Confirmation : </label>
                                      <input type="password" name="cNouveauMotDePasse" id="confirmation" />
								</div>
								<div>
									  <label >Crédit : ${userConnected.credit}</label>
                                     
								</div>
									<input type="submit" value="Enregistrer" class="btn btn-primary" />
									
								</div>
							</form>
							
							<!-- -Pour le doPost -->
							<form action="DeleteUser" method ="post">
							
									<input type="hidden" name="noUtilisateur" value="<%=iduserConnected%>"/>
									<input type="submit" value="Supprimer Utilisateur" class="btn btn-primary" />
									<%if(request.getAttribute("echec" )!= null){ %>
									<a><%=request.getAttribute("echec") %></a>
									<%} %>
							</form>
							

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
		  <div class="container">
        <p class="m-0 text-center text-white">Copyright &copy; Maison
            Gryffondor 2020    <img src="<%=request.getContextPath()%>/images/gryffondor.jpg"
            width="100px" alt="photo Gryffondor">
        </p>
    </div>
    <!-- /.container -->
	</footer>

	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>

