<!DOCTYPE html>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mon Compte</title>
<%
	Utilisateur u = (Utilisateur) session.getAttribute("sessionUtilisateur");
%>
</head>
<body>
		<label for="pseudo">Pseudo : </label> 
		<input type="text" id="pseudo" name="pseudo" value="<%=u.getPseudo() %>" disabled="disabled" /> <br />
		
		<label for="email">Email : </label> 
		<input type="text" id="email" name="email" value="<%=u.getEmail()%>" disabled="disabled" /> <br /> 
		
		<label for="nom">Nom : </label> 
		<input type="text" id="nom" name="nom" value="<%=u.getNom()%>" disabled="disabled"/> <br /> 
		
		<label for="prenom">Prénom : </label> 
		<input type="text" id="prenom" name="prenom" value="<%=u.getPrenom() %>" disabled="disabled"/> <br /> 
		
		<label for="telephone">Telephone : </label> 
		<input type="tel" id="telephone" name="telephone" value="<%=u.getTelephone()%>" disabled="disabled"/> <br /> 
		
		<label for="rue">Rue : </label> 
		<input type="text" id="rue" name="rue" value="<%=u.getRue()%>" disabled="disabled"/><br /> 
		
		<label for="ville">Ville : </label> 
		<input type="text" id="ville" name="ville" value="<%=u.getVille()%>" disabled="disabled"/> <br /> 
		
		<label for="cp">Code Postal : </label> 
		<input type="number" id="code_postal" name="cp" value="<%=u.getCode_postal() %>" disabled="disabled"/> <br /> 
		
		<label for="credit">Crédit : </label> 
		<input type="number" id="credit" name="credit" value="<%=u.getCredit() %>" disabled="disabled"/> <br /> 
		
		

	<div class="contenu">
		<a href="<%=request.getContextPath()%>/ModifierProfil"> 
		<input type="button" class="btn btn-primary" value="Modifier" />
		</a>
	</div>
	<div class="contenu">
		<a href="<%=request.getContextPath()%>/Accueil"> <input
			type="button" class="btn btn-primary" value="Annuler" /></a>
	</div> 
	<p class="mt-5 mb-3 text-muted">© BollobArt</p>
	
</body>
</html>