<!DOCTYPE html>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mon Compte</title>
<%@ include file="header.jsp" %>
</head>
<body>
		<label for="pseudo">Pseudo : </label> 
		<input type="text" id="pseudo" name="pseudo" value="${ sessionUtilisateur.pseudo }" disabled="disabled" /> <br />
		
		<label for="email">Email : </label> 
		<input type="text" id="email" name="email" value="${ sessionUtilisateur.email }" disabled="disabled" /> <br /> 
		
		<label for="nom">Nom : </label> 
		<input type="text" id="nom" name="nom" value="${ sessionUtilisateur.nom }" disabled="disabled"/> <br /> 
		
		<label for="prenom">Prénom : </label> 
		<input type="text" id="prenom" name="prenom" value="${ sessionUtilisateur.prenom }" disabled="disabled"/> <br /> 
		
		<label for="telephone">Telephone : </label> 
		<input type="tel" id="telephone" name="telephone" value="${ sessionUtilisateur.telephone }" disabled="disabled"/> <br /> 
		
		<label for="rue">Rue : </label> 
		<input type="text" id="rue" name="rue" value="${ sessionUtilisateur.rue }" disabled="disabled"/><br /> 
		
		<label for="ville">Ville : </label> 
		<input type="text" id="ville" name="ville" value="${ sessionUtilisateur.ville }" disabled="disabled"/> <br /> 
		
		<label for="cp">Code Postal : </label> 
		<input type="number" id="code_postal" name="cp" value="${ sessionUtilisateur.code_postal }" disabled="disabled"/> <br /> 
		
		<label for="credit">Crédit : </label> 
		<input type="number" id="credit" name="credit" value="${ sessionUtilisateur.credit }" disabled="disabled"/> <br /> 
		
		

	<div class="contenu">
		<a href="<c:url value="/ModifierProfil"/>"><input type="button" value="Modifier"></a>
	</div>
	<div class="contenu">
		<a href="<c:url value="/Accueil"/>" ><input type="button" value="Annuler"></a>
	</div> 
	<p class="mt-5 mb-3 text-muted">© BollobArt</p>
	
</body>
</html>