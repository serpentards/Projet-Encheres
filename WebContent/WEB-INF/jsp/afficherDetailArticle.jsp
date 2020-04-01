<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	nom : ${ article.nomArticle }<br>
	description : ${ article.description }<br>
	dateDebutEncheres : ${ article.dateDebutEncheres }<br>
	dateFinEncheres : ${ article.dateFinEncheres }<br>
	miseAPrix : ${ article.miseAPrix }<br>
	montantEnchere : ${ article.enchere.montantEnchere }<br>
	vendeur : ${ article.vendeur.pseudo }<br>
</body>
</html>