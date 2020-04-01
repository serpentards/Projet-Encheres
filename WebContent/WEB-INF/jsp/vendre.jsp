<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="java.util.Date"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
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
	<form action="" method="post" >
		<label>Article : </label> <input type="text" id="article" name="article" required /><br>
		<label>Description : </label><textarea class="form-control" name="description" required></textarea><br>
		<label>Categorie : </label><select name="categorieChoisie">
			<c:forEach items="${ categories }" var="categorie">
				<option value="${ categorie }">${ categorie.libelle }</option>
			</c:forEach>
		</select><br>
		<label>Photo de l'article : </label><input type="file" id="image" name="image" accept="image/png, image/jpeg" ><br>
		<label>Mise a prix : </label><input type="number" id="prix" name="prix" required /><br>
		<label>Debut de l'enchere : </label><input type="datetime-local" id="dateDebutEnchere" name="dateDebut" required /><br>
		<label>Fin de l'enchere : </label><input type="datetime-local" id="dateFinEnchere" name="dateFin" required /><br>
		<fieldset>
			<legend>Retrait</legend>
			<label>Rue : </label><input type="text" id="rue" name="rue"  /><br>
			<label>Code Postal : </label><input type="text" id="codePostal" name="codePostal"  /><br>
			<label>Ville : </label><input type="text" id="ville" name="ville"  /><br>
		</fieldset>
		<input type="submit" value="Sauvegarder"/>
		<input type="reset" value="Annuler"/>
		<input type="button" value="Annuler la vente"/>
	</form>

</body>
</html>