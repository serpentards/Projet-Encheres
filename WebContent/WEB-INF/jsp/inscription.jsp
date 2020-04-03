<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Inscription</title>
<%@ include file="header.jsp" %>
</head>
<body>
	<h1 align="center">Mon profil</h1>
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
	
	<!-- Formulaire d'inscription -->
	<form method="post" action="<%=request.getContextPath()%>/Inscription">

		<label for="pseudo">Pseudo : </label> 
		<input type="text" id="pseudo" name="pseudo" value="<c:out value="${param.pseudo}"/>" maxlength="30" required/> <br />
		
		<label for="motdepasse">Mot	de passe : </label> 
		<input type="password" id="motdepasse" name="motdepasse" maxlength="30" required/><br /> 
		
		<label for="confirmation">Confirmation du mot de passe : </label>
		<input type="password" id="confirmation" name="confirmation" maxlength="30" required/> <br />
		
		<label for="email">Email : </label> 
		<input type="text" id="email" name="email" value="<c:out value="${param.email}"/>" maxlength="50" required/> <br /> 
		
		<label for="nom">Nom : </label> 
		<input type="text" id="nom" name="nom" value="<c:out value="${param.nom}"/>" maxlength="30" required/> <br /> 
		
		<label for="prenom">Prénom : </label> 
		<input type="text" id="prenom" name="prenom" value="<c:out value="${param.prenom}"/>" maxlength="30" required/> <br /> 
		
		<label for="telephone">Téléphone : </label> 
		<input type="tel" id="telephone" name="telephone" value="<c:out value="${param.telephone}"/>" maxlength="15" required/> <br /> 
		
		<label for="rue">Rue : </label> 
		<input type="text" id="rue" name="rue" value="<c:out value="${param.rue}"/>" maxlength="30" required/><br /> 
		
		<label for="code_postal">Code Postal : </label> 
		<input type="number" id="code_postal" name="code_postal" value="<c:out value="${param.code_postal}"/>" maxlength="10" required/> <br /> 
		
		<label for="ville">Ville : </label>
		<input type="text" id="ville" name="ville" value="<c:out value="${param.ville}"/>" maxlength="30" required/> <br />
		 
		<input type="submit" value="Créer" /> 
		<input type="button" value="Annuler" onclick="location.href='<%=request.getContextPath()%>/Accueil'">
	</form>
<%@ include file="footer.jsp" %>
</body>
</html>