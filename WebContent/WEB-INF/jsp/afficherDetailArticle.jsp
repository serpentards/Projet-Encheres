<%@page import="fr.eni.encheres.messages.LecteurMessage"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="header.jsp" %>
</head>
<body>
	${ article.nomArticle }<br>
	Description de l'article : ${ article.description }<br>
	Date de fin de l'ench�re : 
	<fmt:parseDate value="${ article.dateFinEncheres }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="date" />
	<fmt:formatDate pattern="dd/MM/yyyy" value="${ parsedDateTime }" /><br>
	Prix de d�part : ${ article.miseAPrix }<br>
	Montant de l'ench�re : ${ article.enchere.montantEnchere }<br>
	Vendeur : <a href="<c:url value="/AfficherProfil"/>?id=${ article.vendeur.noUtilisateur }">${ article.vendeur.pseudo }</a><br>

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
<%@ include file="footer.jsp" %>
</body>
</html>