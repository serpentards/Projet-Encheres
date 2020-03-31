<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Acceuil</title>
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
		
         
		<!-- Lecture de la liste d'article -->
		<c:if test="${ !empty listArticle }">
			<table>
				<thead>
					<tr>
						<th colspan="2">Liste des articles a vendre</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${ listArticle }" var="article">
					<tr>
						<td>
						<a href="" title="Détail de l'article !">${ article.nomArticle }</a><br>
						<c:choose>
							<c:when test="${ article.prixVente != 0 }">
								Prix actuel : ${ article.prixVente }<br>
							</c:when>
							<c:otherwise>
								Mise a prix : ${ article.miseAPrix }<br>
							</c:otherwise>
						</c:choose> Fin de l'enchere : ${ article.dateFinEncheres }<br>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:if>
	<p class="mt-5 mb-3 text-muted">© BollobArt</p>
</body>
</html>