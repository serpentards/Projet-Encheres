<%@page import="fr.eni.encheres.messages.LecteurMessage"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	nom : ${ article.nomArticle }<br>
	description : ${ article.description }<br>
	dateDebutEncheres : 
	<fmt:parseDate value="${ article.dateDebutEncheres }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="date" />
	<fmt:formatDate pattern="dd/MM/yyyy" value="${ parsedDateTime }" /><br>
	dateFinEncheres : 
	<fmt:parseDate value="${ article.dateFinEncheres }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="date" />
	<fmt:formatDate pattern="dd/MM/yyyy" value="${ parsedDateTime }" /><br>
	miseAPrix : ${ article.miseAPrix }<br>
	montantEnchere : ${ article.enchere.montantEnchere }<br>
	vendeur : ${ article.vendeur.pseudo }<br>

	<!-- Lecture des messages d'erreur -->
	<c:if test="${ !empty listeCodesErreur }">
		<div class="alert alert-danger" role="alert">
			<strong>Erreur!</strong>
			<ul>
				<c:forEach items="${ listeCodesErreur }" var="code">
					<li><%=LecteurMessage.getMessageErreur((int) pageContext.getAttribute("code"))%></li>
				</c:forEach>
			</ul>
		</div>
	</c:if>

	<form action="<c:url value="/AfficherArticle"></c:url>" method="post">
		<input type="text" name="idArticle" hidden="true" value="${ article.noArticle }">
		<input type="number" id="montantEnchere" name="montantEnchere" min="${( article.enchere.montantEnchere != null) ? article.enchere.montantEnchere + 1 : article.miseAPrix + 1 }">
		<input type="submit" value="Encherir">
	</form>
</body>
</html>