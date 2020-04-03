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
						<div class="d-flex justify-content-md-center">
						<div class="col-md-1 ">
						<div class="card" style="width: 18rem;">
  							<img src="..." class="card-img-top" alt="...">
  								<div class="card-body">
    								<h5 class="card-title">${ article.nomArticle }</h5>
    									<p class="card-text">
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
										Vendeur : <a href="<c:url value="/AfficherProfil"/>?id=${ article.vendeur.noUtilisateur }">${ article.vendeur.pseudo }</a>
    									</p>
									<a class="btn btn-dark" href="<c:url value="/AfficherArticle"/>?id=${ article.noArticle }">Détails de l'article</a>
  								</div>
						</div>
						</div>
						</div>
						
						</td>
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