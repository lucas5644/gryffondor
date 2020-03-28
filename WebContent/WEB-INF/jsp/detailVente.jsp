<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Detail Article</title>

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
			<div>
				<a href="AjouterArticle">Vendre un Article</a>
			</div>
			
			<div>
				<a href="monprofil">Mon profil</a>
			</div>
			
			<div>
				<a href="deconnexion">Déconnexion</a>
			</div>
		</div>
	</nav>
	
	
	
	
	
	
	
	
	
	
	
	

</body>
 <%@ include file="footer.html"  %>
</html>