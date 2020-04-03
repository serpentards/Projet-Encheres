<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Acceuil</title>
<%@ include file="header.jsp" %>
</head>
<body>
<div class="container">
	
	<!-- Lecture des messages d'erreur -->
	<c:if test="${ !empty listeCodesErreur }">
	<div class="row">
		<div class="alert alert-danger" role="alert">
			<strong>Erreur!</strong>
			<ul>
				<c:forEach items="${ listeCodesErreur }" var="code">
					<li><%=LecteurMessage.getMessageErreur((int) pageContext.getAttribute("code"))%></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	</c:if>
	
	<div class="row justify-content-md-center">
		<h1>Liste des enchères</h1>
	</div>
	
	<div class="row">
		<h2>Filtres :</h2>
	</div>
	
	<div class="row">
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
	</div>
	

	<div class="row">
	<!-- Lecture de la liste d'article -->
	<c:if test="${ !empty listArticle }">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">#</th>
					<th scope="col">Nom de l'article</th>
					<th scope="col">Prix</th>
					<th scope="col">Date de fin d'enchere</th>
					<th scope="col">Vendeur</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ listArticle }" var="article" varStatus="index">
					<tr>
						<th scope="row">${ index.index }</th>
						<td><a href="<c:url value="/AfficherArticle"/>?id=${ article.noArticle }">${ article.nomArticle }</a></td>
						<td>
							<c:choose>
								<c:when test="${ article.enchere.montantEnchere != null }">
									${ article.enchere.montantEnchere }
								</c:when>
								<c:otherwise>
									${ article.miseAPrix }
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<fmt:parseDate value="${ article.dateFinEncheres }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
							<fmt:formatDate pattern="dd/mm/yyyy HH:mm" value="${ parsedDateTime }" />
						<td><a href="<c:url value="/AfficherProfil"/>?id=${ article.vendeur.noUtilisateur }">${ article.vendeur.pseudo }</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	</div>
	
	<div class="row">
		© BollobArt
	</div>
</div>
</body>
</html>