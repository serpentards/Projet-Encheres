<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/scss/cssVendre.css">
<title>Vendre un article</title>
</head>
<body>
	
	
	<div class="container" align="center">
	
	<!-- page heading -->
	
		<h1 align="center">Nouvelle vente</h1>
	<!-- <p>My mother has <span style="color:blue">blue</span> eyes.</p> -->
		<div>
	
	
	<!-- image de l'article uploadée -->
	
	
	
	
			
			
	<div class="col-lg-12 col-md-12 col-sm-6">
		<div class="card h-100">	
	
	<!-- Nom de l'article -->
					
			<form name="form-compte" action="<%=request.getContextPath()%>/AjouterUnArticleAVendre" method="get">
                	<div class="input-group p-3 mb-5" id="article">
                        <div class="input-group-prepend">
                        	<span class="input-group-text">Article :</span>
                        </div>
                        <input type="text" id="article" name="article" required />
                    </div>   
                        
                        
    <!-- description de l'article mis en vente  -->

					<div class="input-group p-3 mb-5 ">
  						<div class="input-group-prepend">
    						<span class="input-group-text">Description : </span>
  						</div>
  							<textarea class="form-control" aria-label="Description" required></textarea>
					</div>
    
                       
    <!-- choix de la catégorie de l'article avec "choisir catégorie" par défaut -->
                    
                    <div class="input-group p-3 mb-5">
                       <label class="input-group-text"  for="categorieArticle">Catégories : </label>
                       		
                       		<!-- Liste des couleurs disponibles -->
							
							<%
							List<Categorie> categories = (List<Categorie>) application.getAttribute("categories");
							%>
						<select name="categorieChoisie">
							<option disabled="disabled" selected="selected" hidden="true">choisir...</option>
								<%
									for (Categorie categorieDisponible : categories) {
								%>
							<option value="<%=categorieDisponible.getLibelle()%>"><%=categorieDisponible.getLibelle()%></option>
								<% 
									}
								%>
						</select>
                    </div>
	
	
	
	<!-- bouton pour uploader l'image de l'article -->
	
					<div class="form-group row p-3 mb-5">
    					<label for="uploadFile">Photo de l'article : </label>
    						<input type="file" class="form-control-file" id="inlineFormControlFile1" required>
  					</div>
	
	
	<!--  mise à prix de l'article -->
	
					<div class="form-group row p-3 mb-5">
						
					
					</div>
	
	
	<!-- date de début et fin de l'enchère -->
	                    
                    <div class="input-group p-3 mb-5">   
                        <label for="dateDebutEnchere">Date de début de l'enchère : </label>
                        <input id="dateDebutEnchere" type="date" required />
                    </div>
                    
                   	<div class="input-group p-3 mb-5">
                   		<label for="dateFinEnchere">Date de fin de l'enchère : </label>
                        <input id="dateFinEnchere" type="date" required />
                   	</div>
    
	
	
	<!-- zone d'information du retrait de l'objet une fois vendu, adresse de l'utilisateur vendeur par défaut -->
	
	<div>
	<fieldset >
	
	<legend>Retrait</legend>
	
	<!-- rue -->
	 				<div class="input-group p-3 mb-5">
                        <div class="input-group-prepend">
                        	<span class="input-group-text">Rue :</span>
                        </div>
                        	<input type="text" id="rue" name="rue" required />
                    </div>
	
	<!-- code postal -->
 					<div class="input-group p-3 mb-5">
                        <div class="input-group-prepend">
                        	<span class="input-group-text">Code Postal :</span>
                        </div>
                        	<input type="text" id="cp" name="cp" required />
                    </div>
	
	<!-- ville -->	
					<div class="input-group p-3 mb-5">
                        <div class="input-group-prepend">
                        	<span class="input-group-text">Ville :</span>
                        </div>
                        	<input type="text" id="ville" name="ville" required />
                    </div>
	</fieldset>
	</div>
	<!-- Boutons Sauvegarder / Annuler -->
	
				<div class="p-3 mb-5" id="boutons">
                    <input type="submit" name="btSauvegarder" value="Sauvegarder" title="Sauvegarder"/>
                    <input type="reset" name="btAnnuler" value="Annuler" title="Annuler" />
                </div>
			</form>
	
				</div>
			</div>
		</div>
		<!-- /.row -->
	
	</div>
	<!-- /.container -->
	
	
	<!-- Bootstrap core JavaScript -->
	<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>

 
</body>
</html>

