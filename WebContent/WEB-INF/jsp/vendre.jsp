<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="java.util.Date"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="header.jsp" %>
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
	<form action="" method="post">
		<label>Article : </label> <input type="text" id="article" name="article" value="<c:out value="${param.article}"/>" required /><br>
		<label>Description : </label><textarea class="form-control" id="description" name="description" required>${param.description}</textarea><br>
		<label>Categorie : </label><select name="categorieChoisie">
			<c:forEach items="${ categories }" var="categorie">
				<option value="${ categorie }">${ categorie.libelle }</option>
			</c:forEach>
		</select><br>
		<label>Photo de l'article : </label><input type="file" id="image" name="image" accept="image/png, image/jpeg" ><br>
		<label>Mise a prix : </label><input type="number" id="prix" name="prix" value="<c:out value="${param.prix}"/>" required /><br>
		<label>Date de début de l'enchere : </label><input type="date" id="dateDebutEnchere" name="dateDebut" required />
		<input type="time" id="dateDebutEnchere" name="heureDebut" required /><br>
		<label>Date de fin de l'enchere : </label><input type="date" id="dateFinEnchere" name="dateFin" required />
		<input type="time" id="dateFinEnchere" name="heureFin" required /><br>
		<fieldset>
			<legend>Retrait</legend>
			<label>Rue : </label><input type="text" id="rue" name="rue" value="<c:out value="${param.rue}"/>" /><br>
			<label>Code Postal : </label><input type="text" id="codePostal" name="codePostal" value="<c:out value="${param.codePostal}"/>" /><br>
			<label>Ville : </label><input type="text" id="ville" name="ville" value="<c:out value="${param.ville}"/>" /><br>
		</fieldset>
		<input type="submit" value="Sauvegarder"/>
		<input type="reset" value="Annuler"/>
		<input type="button" value="Annuler la vente"/>
	</form>

</body>
</html>