<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mon Compte</title>
<%
	Utilisateur u = (Utilisateur) session.getAttribute("sessionUtilisateur");
%>
</head>
<body>
<h1 align="center">Mon compte</h1>
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
	<form method="post" action="<%=request.getContextPath()%>/ModifierProfil">
		<label for="pseudo">Pseudo : </label> 
		<input type="text" id="pseudo" name="pseudo" value="<%=u.getPseudo() %>" disabled="disabled" /> <br />
		
		<label for="email">Email : </label> 
		<input type="text" id="email" name="email" value="<%=u.getEmail()%>" maxlength="20" /> <br /> 
		
		<label for="nom">Nom : </label> 
		<input type="text" id="nom" name="nom" value="<%=u.getNom()%>" maxlength="30" /> <br /> 
		
		<label for="prenom">Prénom : </label> 
		<input type="text" id="prenom" name="prenom" value="<%=u.getPrenom() %>" maxlength="30" /> <br /> 
		
		<label for="telephone">Telephone : </label> 
		<input type="tel" id="telephone" name="telephone" value="<%=u.getTelephone()%>" maxlength="15" /> <br /> 
		
		<label for="rue">Rue : </label> 
		<input type="text" id="rue" name="rue" value="<%=u.getRue()%>" maxlength="30" /><br /> 
		
		<label for="ville">Ville : </label> 
		<input type="text" id="ville" name="ville" value="<%=u.getVille()%>" maxlength="30" /> <br /> 
		
		<label for="cp">Code Postal : </label> 
		<input type="number" id="code_postal" name="cp" value="<%=u.getCode_postal() %>" maxlength="10" /> <br /> 
		
		<label for="mdpActuel">Mot de passe actuel: </label> 
		<input type="password" id="motdepasseActuel" name="mdpActuel" value="<%=u.getMot_de_passe() %>" disabled="disabled" /><br /> 
		
		<label for="mdp">Nouveau mot de passe : </label> 
		<input type="password" id="motdepasse" name="mdp" value="<%=u.getMot_de_passe() %>" maxlength="30" /><br /> 
		
		<label for="conf">Confirmation du mot de passe : </label>
		<input type="password" id="confirmation" name="conf" value="<%=u.getMot_de_passe() %>" size="20"	maxlength="20" /> <br /> 
		
		<input type="submit" value="Enregistrer" /> 
		
		<input type="button" value="Annuler" onclick="location.href='<%=request.getContextPath()%>/Accueil'"/>
 	</form>
	<div class="contenu">
		<a href="<%=request.getContextPath()%>/Supprimer"> <input
			type="button" class="btn btn-primary" value="Supprimer compte" onclick="return confirm('Etes-vous sûr de vouloir supprimer votre compte ?')"/></a>
	</div>
	<p class="mt-5 mb-3 text-muted">© BollobArt</p>
	
</body>
</html>