<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.messages.LecteurMessage"%>

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
	<h1 class="h3 mb-3 font-weight-normal">Connectez-vous</h1>
	<form method="post" action="<%=request.getContextPath()%>/Connexion">

		<label for="email">Adresse Email ou Pseudo : </label>
		<input type="text" id="email" name="email" placeholder="Adresse Email ou Pseudo"/> <br /> 
		<label for="motdepasse">Mot	de passe : </label>
		<input type="password" id="motdepasse" name="motdepasse" placeholder="Mot De Passe"/> <br />
		<input type="submit" value="Connexion"/> <br />
		<input type="checkbox" name="souvenir">Se souvenir de moi<br>
		<a href="">Mot de passe oublié</a>
	</form>
	
	<div class="contenu">
        <a href="<%=request.getContextPath()%>/Inscription"><input
            type="button" class="btn btn-primary"
            value="Créer un Compte" /></a>
    </div>
    <p class="mt-5 mb-3 text-muted">© BollobArt</p>
</body>
</html>