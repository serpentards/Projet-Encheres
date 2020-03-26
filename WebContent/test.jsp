<%@page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@page import="fr.eni.encheres.exception.BusinessException"%>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test</title>
</head>
<body>
	<h1>L'utilisateur :</h1>
	<ul>
	<%
		if(request.getAttribute("reponseSelectEmail") != null){
			Utilisateur lstRepas01Janvier = (Utilisateur) request.getAttribute("reponseSelectEmail");
			out.println(lstRepas01Janvier);
		}	
	%>
	</ul>
	
	<h1>Exceptions :</h1>
	<%if(request.getAttribute("BusinessException") != null){
		BusinessException be = (BusinessException) request.getAttribute("BusinessException");
		List<Integer> lstErreur = be.getListeCodesErreur();
		for(Integer i : lstErreur){
			out.write(LecteurMessage.getMessageErreur(i));
		}
	}	%>
	
	
</body>
</html>