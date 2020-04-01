<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Liste Utilisateur</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand"
				href="<%=request.getContextPath()%>/accueilAdmin">Mon
				application d'enchères</a>

			<div>
				<a class="btn btn-primary nav-link" role="button"
					href="AjouterArticle">Vendre un Article</a>
			</div>
			<div>
				<a class="btn btn-primary nav-link" role="button"
					href="#">Liste Utilisateur</a>
			</div>
			<div>
				<a class="btn btn-primary nav-link" role="button" href="monprofil">Mon
					profil</a>
			</div>
			<div>
				<a class="btn btn-primary nav-link" role="button" href="deconnexion">Déconnexion</a>
			</div>
		</div>
	</nav>

	<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
		<div class="card h-100">
			<div class="card-body contenu">
				<div class="contenu">
					<c:forEach var="utilisateur" items="${ListeUtilisateur}">
						<form action="DeleteUserAdmin" method="post">
							<div class="col-lg-12 d-inline-block contenu portfolio-item">
									
								 <a>no utilisateur : ${utilisateur.noUtilisateur}</a><br>
								 <a>Pseudo : ${utilisateur.pseudo}</a><br>
								 <a>Nom : ${utilisateur.nom}</a><br> 
								 <a>Prénom	: ${utilisateur.prenom}</a><br>
								 <a>Email	: ${utilisateur.email}</a><br>
								 <a>Telephone	: ${utilisateur.telephone}</a><br>
								 <a>Rue	: ${utilisateur.rue}</a><br>
								 <a>Code Postal	: ${utilisateur.codePostal}</a><br>
								 <a>Ville	: ${utilisateur.ville}</a><br>
								 <a>Mot de passe : ${utilisateur.motDePasse}</a><br>
								 <a>Crédit	: ${utilisateur.credit}</a><br>
								 <a>Administrateur	: ${utilisateur.administrateur}</a>
							<input type="Button" value="DeleteUserAdmin" name="Supprimer utilisateur">
							<input type="Button" value="" name="desactiver un comptes">
							
							</div>
						</form>
					</c:forEach>
				</div>
			</div>
		</div>

	</div>

</body>
</html>