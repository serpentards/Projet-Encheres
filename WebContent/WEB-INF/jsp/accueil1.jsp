<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Accueil</title>
</head>
<body>
	<c:choose>
		<c:when test="${ !empty sessionUtilisateur }">
		<p align="right">
		<a href="<%=request.getContextPath()%>/Deconnexion" >Enchères</a>
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

	<h1 align="center">Liste des enchères</h1>
	
	<h2 align="left">Filtres :</h2>
		<label>Categorie : </label><select name="categorieChoisie">
			<c:forEach items="${ categories }" var="categorie">
				<option value="${ categorie.noCategorie }">${ categorie.libelle }</option>
			</c:forEach>
		</select><br>
	<p class="mt-5 mb-3 text-muted">© BollobArt</p>
</body>
</html>