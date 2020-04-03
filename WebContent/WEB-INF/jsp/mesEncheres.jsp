<!DOCTYPE html >
<html>
<head>
<link href="css/_list-group.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mes enchères</title>
<%@ include file="header.jsp" %>
</head>
<body>
<div class="row">
  <div class="col-4">
    <div class="list-group" id="list-tab" role="tablist">
      <a class="list-group-item list-group-item-action active" id="list-encheres-list" data-toggle="list" href="#list-encheres" role="tab" aria-controls="encheres">Enchères</a>
      <a class="list-group-item list-group-item-action" id="list-encheres-participe-list" data-toggle="list" href="#list-encheres-participe" role="tab" aria-controls="encheres-participe">Enchères auxquelles je participe</a>
      <a class="list-group-item list-group-item-action" id="list-encheres-remporte-list" data-toggle="list" href="#list-encheres-remporte" role="tab" aria-controls="encheres-remporte">Enchères que j'ai remporter</a>
      <a class="list-group-item list-group-item-action" id="list-ventes-list" data-toggle="list" href="#list-ventes" role="tab" aria-controls="ventes">Ventes</a>
      <a class="list-group-item list-group-item-action" id="list-ventes-en-cours-list" data-toggle="list" href="#list-ventes-en-cours" role="tab" aria-controls="ventes-en-cours">Ventes en cours</a>
      <a class="list-group-item list-group-item-action" id="list-ventes-non-commencees-list" data-toggle="list" href="#list-ventes-non-commencees" role="tab" aria-controls="ventes-non-commencees">Ventes non commencées</a>
      <a class="list-group-item list-group-item-action" id="list-ventes-terminer-list" data-toggle="list" href="#list-ventes-terminees" role="tab" aria-controls="ventes-terminees">Ventes terminées</a>
    </div>
  </div>
  <div class="col-8">
    <div class="tab-content" id="nav-tabContent">
      <div class="tab-pane fade show active" id="list-encheres" role="tabpanel" aria-labelledby="list-encheres-list">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">#</th>
					<th scope="col">Nom de l'article</th>
					<th scope="col">Prix</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ lstAllEncheresEnCours }" var="article" varStatus="index">
					<tr>
						<th scope="row">${ index.index }</th>
						<td><a href="<c:url value="/AfficherArticle"/>?id=${ article.noArticle }">${ article.nomArticle }</a></td>
						<td>
							<c:choose>
								<c:when test="${ article.enchere.montantEnchere != null }">
									${ article.enchere.montantEnchere }
								</c:when>
								<c:otherwise>
									${ article.miseAPrix }
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
      </div>
      <div class="tab-pane fade" id="list-encheres-participe" role="tabpanel" aria-labelledby="list-encheres-participe-list">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">#</th>
					<th scope="col">Nom de l'article</th>
					<th scope="col">Prix</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ lstAllEncheresParticipe }" var="article" varStatus="index">
					<tr>
						<th scope="row">${ index.index }</th>
						<td><a href="<c:url value="/AfficherArticle"/>?id=${ article.noArticle }">${ article.nomArticle }</a></td>
						<td>
							<c:choose>
								<c:when test="${ article.enchere.montantEnchere != null }">
									${ article.enchere.montantEnchere }
								</c:when>
								<c:otherwise>
									${ article.miseAPrix }
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	  </div>
      <div class="tab-pane fade" id="list-encheres-remporte" role="tabpanel" aria-labelledby="list-encheres-remporte-list">
        <table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">#</th>
					<th scope="col">Nom de l'article</th>
					<th scope="col">Prix</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ lstAllEncheresGagne }" var="article" varStatus="index">
					<tr>
						<th scope="row">${ index.index }</th>
						<td><a href="<c:url value="/AfficherArticle"/>?id=${ article.noArticle }">${ article.nomArticle }</a></td>
						<td>
							<c:choose>
								<c:when test="${ article.enchere.montantEnchere != null }">
									${ article.enchere.montantEnchere }
								</c:when>
								<c:otherwise>
									${ article.miseAPrix }
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
      </div>
      <div class="tab-pane fade" id="list-ventes" role="tabpanel" aria-labelledby="list-ventes-list">
      	<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">#</th>
					<th scope="col">Nom de l'article</th>
					<th scope="col">Prix</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ lstAllVentes }" var="article" varStatus="index">
					<tr>
						<th scope="row">${ index.index }</th>
						<td><a href="<c:url value="/AfficherArticle"/>?id=${ article.noArticle }">${ article.nomArticle }</a></td>
						<td>
							<c:choose>
								<c:when test="${ article.enchere.montantEnchere != null }">
									${ article.enchere.montantEnchere }
								</c:when>
								<c:otherwise>
									${ article.miseAPrix }
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
      </div>
      <div class="tab-pane fade" id="list-ventes-en-cours" role="tabpanel" aria-labelledby="list-ventes-en-cours-list">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">#</th>
					<th scope="col">Nom de l'article</th>
					<th scope="col">Prix</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ lstAllVentesEnCours }" var="article" varStatus="index">
					<tr>
						<th scope="row">${ index.index }</th>
						<td><a href="<c:url value="/AfficherArticle"/>?id=${ article.noArticle }">${ article.nomArticle }</a></td>
						<td>
							<c:choose>
								<c:when test="${ article.enchere.montantEnchere != null }">
									${ article.enchere.montantEnchere }
								</c:when>
								<c:otherwise>
									${ article.miseAPrix }
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	  </div>
      <div class="tab-pane fade" id="list-ventes-non-commencees" role="tabpanel" aria-labelledby="list-ventes-non-commencees-list">
        <table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">#</th>
					<th scope="col">Nom de l'article</th>
					<th scope="col">Prix</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ lstAllVentesNonCommencees }" var="article" varStatus="index">
					<tr>
						<th scope="row">${ index.index }</th>
						<td><a href="<c:url value="/AfficherArticle"/>?id=${ article.noArticle }">${ article.nomArticle }</a></td>
						<td>
							<c:choose>
								<c:when test="${ article.enchere.montantEnchere != null }">
									${ article.enchere.montantEnchere }
								</c:when>
								<c:otherwise>
									${ article.miseAPrix }
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
      </div>
      <div class="tab-pane fade" id="list-ventes-terminees" role="tabpanel" aria-labelledby="list-ventes-terminer-list">
        <table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">#</th>
					<th scope="col">Nom de l'article</th>
					<th scope="col">Prix</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ lstAllVentesTerminees }" var="article" varStatus="index">
					<tr>
						<th scope="row">${ index.index }</th>
						<td><a href="<c:url value="/AfficherArticle"/>?id=${ article.noArticle }">${ article.nomArticle }</a></td>
						<td>
							<c:choose>
								<c:when test="${ article.enchere.montantEnchere != null }">
									${ article.enchere.montantEnchere }
								</c:when>
								<c:otherwise>
									${ article.miseAPrix }
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
      </div>
    </div>
  </div>
</div>
<br>
<br>
<br>

</body>
</html>