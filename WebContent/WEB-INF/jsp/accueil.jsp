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

<div class="btn-group" role="group" aria-label="Basic example">
	<a href="<c:url value="/MesEncheres"/>"><button type="button" class="btn btn-secondary" >Enchères</button></a>
	<a href="<c:url value="/AjoutVente"/>"><button type="button" class="btn btn-secondary" >Vendre un article</button></a>
	<a href="<c:url value="/AfficherProfil"/>"><button type="button" class="btn btn-secondary" >Mon profil</button></a>
	<a href="<c:url value="/Deconnexion"/>"><button type="button" class="btn btn-secondary" >Déconnexion</button></a>
</div>
	<c:choose>
		<c:when test="${ !empty sessionUtilisateur }">
		<p align="right">
			<a href="<c:url value="/MesEncheres"/>">Enchères</a>
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
		<p class="mt-5 mb-3 text-muted">© BollobArt</p>
</body>
</html>