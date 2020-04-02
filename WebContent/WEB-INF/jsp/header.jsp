<!DOCTYPE html >
<html>
<head>
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/4-col-portfolio.css" rel="stylesheet">
<link rel="icon" href="/ENI-Encheres-1/images/logo_eni_enchere_favicon.png">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<nav class="navbar navbar-light bg-light navbar-expand-lg  fixed-top">
	<div class="container">
	  <a class="navbar-brand" href="<%=request.getContextPath()%>/Accueil">
	    <img src="/ENI-Encheres-1/images/logo_eni_enchere_fond_blanc.png" width="100" height="100" class="d-inline-block align-top" alt="">
	    ENI Enchères
	  </a>
	  <div class="collapse navbar-collapse" id="navbarResponsive">
		  <c:choose>
				<c:when test="${ !empty sessionUtilisateur }">
				<p align="right">
				<a href="<%=request.getContextPath()%>/MesEncheres" >Enchères</a>
				<a href="<%=request.getContextPath()%>/AjoutVente" >Vendre un article</a>
				<a href="<%=request.getContextPath()%>/AfficherProfil" >Mon profil</a>
				<a href="<%=request.getContextPath()%>/Deconnexion" >Déconnexion</a>
				</p>
				</c:when>
				<c:otherwise>
				<p align="right">
				<a href="<%=request.getContextPath()%>/Connexion">S'inscrire - Se connecter</a>
				</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</nav>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/> 

</body>
</html>