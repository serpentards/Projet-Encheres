<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Inscription</title>
</head>
<body>
	<h1 align="center">Mon profil</h1>
	<%
		if (request.getAttribute("listeCodesErreur") != null) {
			List<Integer> listeCodesErreur = (List<Integer>) request.getAttribute("listeCodesErreur");
	%>
	<div class="alert alert-danger" role="alert">
		<strong>Erreur!</strong>
		<ul>
			<%
				for (Integer code : listeCodesErreur) {
			%>

			<li><%=LecteurMessage.getMessageErreur(code)%></li>
			<%
				}
			%>
		</ul>
	</div>
	<%
		}
	%>
	<form method="post" action="<%=request.getContextPath()%>/Inscription">

		<label for="pseudo">Pseudo : </label> 
		<input type="text" id="pseudo" name="pseudo" value="" maxlength="30" /> <br />
		<label for="prenom">Prénom : </label> 
		<input type="text" id="prenom" name="prenom" value="" maxlength="30" /> <br /> 
		<label for="telephone">Telephone : </label> 
		<input type="tel" id="telephone" name="telephone" value="" maxlength="15" /> <br /> 
		<label for="code_postal">Code Postal : </label> 
		<input type="number" id="code_postal" name="code_postal" value="" maxlength="10" /> <br /> 
		<label for="motdepasse">Mot	de passe : </label> 
		<input type="password" id="motdepasse" name="motdepasse" value="" maxlength="30" /><br /> 
		<label for="nom">Nom : </label> 
		<input type="text" id="nom" name="nom" value="" maxlength="30" /> <br /> 
		<label for="email">Email : </label> 
		<input type="text" id="email" name="email" value="" maxlength="20" /> <br /> 
		<label for="rue">Rue : </label> 
		<input type="text" id="rue" name="rue" value="" maxlength="30" /><br /> 
		<label for="ville">Ville : </label> <input type="text" id="ville" name="ville" value="" maxlength="30" /> <br /> 
		<label for="confirmation">Confirmation : </label>
		<input type="password" id="confirmation" name="confirmation" value="" size="20"	maxlength="20" /> <br /> 
		<input type="submit" value="Créer" /> 
		<input type="button" value="Annuler" onclick="location.href='/Accueil'">
	</form>
</body>
</html>