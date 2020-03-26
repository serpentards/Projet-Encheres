<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<title>Acceuil</title>
</head>
<body>
	<%
		Utilisateur utilisateurCourant = (Utilisateur) session.getAttribute("sessionUtilisateur");
		if (utilisateurCourant != null) {
	%>
	<p align="right">
		<a href="<%=request.getContextPath()%>/Deconnexion">Enchères</a> <a
			href="<%=request.getContextPath()%>/Vendre">Vendre un article</a> <a
			href="<%=request.getContextPath()%>/Deconnexion">Mon profil</a> <a
			href="<%=request.getContextPath()%>/Deconnexion">Déconnexion</a>
	</p>
	<%
		} else {
	%>
	<p align="right">
		<a href="<%=request.getContextPath()%>/Connexion">S'inscrire - Se
			connecter</a>
	</p>
	<%
		}
	%>
	<h1 align="center">Liste des enchères</h1>

	<h2 align="left">Filtres :</h2>

	<p>Choisissez votre Catégorie
	<p>

		<!-- Liste des couleurs disponibles -->
		<%
			List<Categorie> categories = (List<Categorie>) application.getAttribute("categories");
		%>
		<select name="categorieChoisie">

			<%
				for (Categorie categorieDisponible : categories) {
			%>
			<option value="<%=categorieDisponible.getLibelle()%>"><%=categorieDisponible.getLibelle()%></option>
			<%
				}
			%>
		</select>
		<!-- Bootstrap core JavaScript -->
		<script src="vendor/jquery/jquery.min.js"></script>
		<script src="vendor/jquery/popper.min.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>