package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleVenduManager;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class MesEncheres
 */
@WebServlet("/MesEncheres")
public class MesEncheres extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("sessionUtilisateur");
		
		ArticleVenduManager articleVenduManager = new ArticleVenduManager();
		
		List<ArticleVendu> lstAllEncheresEnCours = null;
		List<ArticleVendu> lstAllEncheresParticipe = null;
		List<ArticleVendu> lstAllEncheresGagne = null;
		List<ArticleVendu> lstAllVentes = null;
		List<ArticleVendu> lstAllVentesEnCours= null;
		List<ArticleVendu> lstAllVentesNonCommencees= null;
		List<ArticleVendu> lstAllVentesTerminees= null;
		
		try {
			lstAllEncheresEnCours = articleVenduManager.selectionnerEncheresEnCours();
			lstAllEncheresParticipe = articleVenduManager.selectionnerEncheresParticipe(utilisateur);
			lstAllEncheresGagne = articleVenduManager.selectionnerEncheresGagne(utilisateur);
			lstAllVentes = articleVenduManager.selectionnerVentes(utilisateur);
			lstAllVentesEnCours = articleVenduManager.selectionnerVentesEnCours(utilisateur);
			lstAllVentesNonCommencees = articleVenduManager.selectionnerVentesNonCommencees(utilisateur);
			lstAllVentesTerminees = articleVenduManager.selectionnerVenteTerminees(utilisateur);
			
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			response.sendRedirect("./MesEncheres");
		}
         request.setAttribute("lstAllEncheresEnCours", lstAllEncheresEnCours);
         request.setAttribute("lstAllEncheresParticipe", lstAllEncheresParticipe);
         request.setAttribute("lstAllEncheresGagne", lstAllEncheresGagne);
         request.setAttribute("lstAllVentes", lstAllVentes);
         request.setAttribute("lstAllVentesEnCours", lstAllVentesEnCours);
         request.setAttribute("lstAllVentesNonCommencees", lstAllVentesNonCommencees);
         request.setAttribute("lstAllVentesTerminees", lstAllVentesTerminees);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/mesEncheres.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
