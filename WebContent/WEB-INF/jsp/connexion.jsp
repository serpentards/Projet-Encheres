<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.messages.LecteurMessage"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Connexion</title>
<%@ include file="header.jsp" %>
</head>
<body>
<div class="container">
		<div class="row justify-content-center">
			<h1>Connectez-vous</h1>
		</div>
		
		<!-- Lecture des messages d'erreur -->
		<c:if test="${ !empty listeCodesErreur }">
		<div class="row justify-content-center">
			<div class="alert alert-danger" role="alert">
				<strong>Erreur!</strong>
				<ul>
					<c:forEach items="${ listeCodesErreur }" var="code">
						<li><%=LecteurMessage.getMessageErreur((int)pageContext.getAttribute("code"))%></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		</c:if>
	
		<div class="container">
			<form method="post" action="<%=request.getContextPath()%>/Connexion">
				<div class="row justify-content-center">
					<div class="col-sm-auto">
						<label for="email">Adresse Email ou Pseudo : </label>
					</div>
					<div class="col-sm-auto">
						<input type="text" id="email" name="email" placeholder="Adresse Email ou Pseudo"/> <br /> 
					</div>
				</div>
				<div class="row justify-content-center">
					<div class="col-sm-auto">
						<label for="motdepasse">Mot	de passe : </label>
					</div>
					<div class="col-sm-auto">
						<input type="password" id="motdepasse" name="motdepasse" placeholder="Mot De Passe"/> <br />
					</div>
				</div>
				<div class="row justify-content-center">
					<div class="col-sm-auto">
						<input type="submit" value="Connexion"/>
					</div>
					<div class="col-sm-auto">
						<input type="checkbox" name="souvenir">Se souvenir de moi<br>
					</div>
				</div>
			</form>
			<div class="row justify-content-center">
				<a href="">Mot de passe oublié</a>
			</div>
			
	    	<div class="row justify-content-center">
	        	<button type="button" class="btn btn-secondary" onClick="javascript:location.href='<c:url value="/MesEncheres"/>'">Crée un compte</button>
	    	</div>
	    </div>
    <div class="row">
	    © BollobArt
    </div>
</div>
</body>
</html>