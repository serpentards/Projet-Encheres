<%@page import="fr.eni.encheres.messages.LecteurMessage"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modifier mon article</title>
</head>
<body>
<body>
		<!-- Lecture des messages d'erreur -->
	<c:if test="${ !empty listeCodesErreur }">
		<div class="alert alert-danger" role="alert">
			<strong>Erreur!</strong>
			<ul>
				<c:forEach items="${ listeCodesErreur }" var="code">
					<li><%=LecteurMessage.getMessageErreur((int)pageContext.getAttribute("code"))%></li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
	<form action="<%=request.getContextPath()%>/ModifierArticle" method="post">
		<label>Article : </label> <input type="text" id="article" name="article" value="${ article.nomArticle }"  /><br>
		<label>Description : </label><textarea class="form-control" name="description" >${ article.description }</textarea><br>
		<label>Categorie : </label><select name="categorieChoisie">
			<option selected="selected" hidden="true" value="${ article.categorieArticle }">${ article.categorieArticle.libelle }</option>
			<c:forEach items="${ categories }" var="categorie">
				<option value="${ categorie }">${ categorie.libelle }</option>
			</c:forEach>
		</select><br>
		<label>Photo de l'article : </label><input type="file" id="image" name="image" accept="image/png, image/jpeg" ><br>
		<label>Mise a prix : </label><input type="number" id="prix" name="prix" value="${ article.miseAPrix }" /><br>
		<label>Debut de l'enchere : </label><input type="datetime-local" id="dateDebutEnchere" name="dateDebut" value="${ article.dateDebutEncheres }" /><br>
		<label>Fin de l'enchere : </label><input type="datetime-local" id="dateFinEnchere" name="dateFin" value="${ article.dateFinEncheres }" /><br>
		<fieldset>
			<legend>Retrait</legend>
			<label>Rue : </label><input type="text" id="rue" name="rue" value="${ article.vendeur.rue }" /><br>
			<label>Code Postal : </label><input type="text" id="codePostal" name="codePostal" value="${ article.vendeur.code_postal }" /><br>
			<label>Ville : </label><input type="text" id="ville" name="ville" value="${ article.vendeur.ville }" /><br>
		</fieldset>
		<input type="submit" value="Sauvegarder"/>
		<input type="reset" value="Annuler"/>
		<input type="button" value="Annuler la vente"/>
		
		<input type="text" name="idArticle" hidden="true" value="${ article.noArticle }">
	</form>
</body>
</html>