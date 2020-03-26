<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Connexion</title>
</head>
<body>
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
	<form method="post" action="<%=request.getContextPath()%>/Connexion">

		<label for="nom">Identifiant : </label>
		<input type="text" id="email" name="email" value="" /> <br /> 
		<label for="motdepasse">Mot	de passe : </label>
		<input type="password" id="motdepasse" name="motdepasse" value="" /> <br />
		<input type="submit" value="Connexion"/> <br />
		<input type="checkbox" name="souvenir">Se souvenir de moi<br>
		<a href="">Mot de passe oublié</a>
	</form>
	
	<div class="contenu">
        <a href="<%=request.getContextPath()%>/Inscription"><input
            type="button" class="btn btn-primary"
            value="Inscription" /></a>
    </div>
</body>
</html>