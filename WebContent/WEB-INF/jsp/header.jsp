<!DOCTYPE html >
<html>
<head>
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/4-col-portfolio.css" rel="stylesheet">
<link rel="icon" href="/Projet-Encheres/images/logo_eni_enchere_favicon.png">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<nav class="navbar navbar-light  fixed-top" style="background-color: lightgrey"> 
		<div class="col-sm-auto justify-content-center">
		<a class="navbar-brand" href="<%=request.getContextPath()%>/Accueil">
				<img src="/Projet-Encheres/images/logo_eni_enchere_fond_blanc.png" width="100" height="100" class="d-inline-block align-top" alt="">ENI Enchères
		  </a>
		</div>
		
		<div class="row justify-content-end">
			<c:choose>
				<c:when test="${ !empty sessionUtilisateur }">
					<div class="btn-group" role="group" aria-label="Basic example" align="right">
						<button type="button" class="btn btn-secondary" onClick="javascript:location.href='<c:url value="/MesEncheres"/>'">Enchères</button>
						<button type="button" class="btn btn-secondary" onClick="javascript:location.href='<c:url value="/AjoutVente"/>'">Vendre un article</button>
						<button type="button" class="btn btn-secondary" onClick="javascript:location.href='<c:url value="/AfficherProfil"/>'" >Mon profil</button>
						<button type="button" class="btn btn-secondary" onClick="javascript:location.href='<c:url value="/Deconnexion"/>'">Déconnexion</button>
					</div>
				</c:when>
			<c:otherwise>
				<button type="button" class="btn btn-secondary" onClick="javascript:location.href='<c:url value="/Connexion"/>'">S'inscrire - Se connecter</button>
			</c:otherwise>
			</c:choose>
		</div>
</nav> 
<br/>
<br/>
<br/>
<br/>
<br/>
<br/> 
<br/>

</body>
</html>