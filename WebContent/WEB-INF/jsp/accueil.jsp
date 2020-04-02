<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
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
			<a href="<c:url value="/Deconnexion"/>">Enchères</a>
			<a href="<c:url value="/AjoutVente"/>" >Vendre un article</a>
			<a href="<c:url value="/AfficherProfil"/>" >Mon profil</a>
			<a href="<c:url value="/Deconnexion"/>" >Déconnexion</a>
		</p>
		</c:when>
		<c:otherwise>
		<p align="right">
			<a href="<c:url value="/Connexion"/>">S'inscrire - Se connecter</a>
		</p>
		</c:otherwise>
	</c:choose>
	<!-- Lecture des messages d'erreur -->
	<c:if test="${ !empty listeCodesErreur }">
		<div class="alert alert-danger" role="alert">
			<strong>Erreur!</strong>
			<ul>
				<c:forEach items="${ listeCodesErreur }" var="code">
					<li><%=LecteurMessage.getMessageErreur((int) pageContext.getAttribute("code"))%></li>
				</c:forEach>
			</ul>
		</div>
	</c:if>

	<h1 align="center">Liste des enchères</h1>
	
	<h2 align="left">Filtres :</h2>
		<form action="./Accueil" method="post">
			<input type="text" placeholder="Le nom de l'article contient" name="nomRechercher">
			<label for="categorieChoisie">Categorie : </label>
			<select id="categorieChoisie" name="categorieChoisie" >
					<option selected="selected" hidden="true" value="">Choisir</option>
				<c:forEach items="${ categories }" var="categorie">
					<option value="${ categorie.noCategorie }">${ categorie.libelle }</option>
				</c:forEach>
			</select><br>
			<input type="submit" value="Rechercher">
		</form>
		
		
         
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
						<a href="<c:url value="/AfficherArticle"/>?id=${ article.noArticle }">${ article.nomArticle }</a><br>
						<c:choose>
							<c:when test="${ article.enchere.montantEnchere != null }">
								Prix actuel : ${ article.enchere.montantEnchere }<br>
							</c:when>
							<c:otherwise>
								Mise a prix : ${ article.miseAPrix }<br>
							</c:otherwise>
						</c:choose> Fin de l'enchere : 
						<fmt:parseDate value="${ article.dateFinEncheres }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
						<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${ parsedDateTime }" /><br>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:if>
	<p class="mt-5 mb-3 text-muted">© BollobArt</p>
</body>
</html>