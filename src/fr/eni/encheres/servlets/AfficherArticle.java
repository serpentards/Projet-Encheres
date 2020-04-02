package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleVenduManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class AfficherArticle
 */
@WebServlet("/AfficherArticle")
public class AfficherArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idArticle = Integer.parseInt(request.getParameter("id"));
		ArticleVenduManager articleVenduManager = new ArticleVenduManager();
		try {
			ArticleVendu article = articleVenduManager.selectionnerArticleParId(idArticle);
			request.setAttribute("article", article);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/jsp/afficherDetailArticle.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		
		LocalDateTime dateEnchere = LocalDateTime.now();
		ArticleVendu article = null;
		int montantEnchere = Integer.parseInt(request.getParameter("montantEnchere"));
		int idArticle = Integer.parseInt(request.getParameter("idArticle"));
		Utilisateur acheteur = (Utilisateur) request.getSession().getAttribute("sessionUtilisateur");
		
		try {
			ArticleVenduManager articleVenduManager = new ArticleVenduManager();
			article = articleVenduManager.selectionnerArticleParId(idArticle);
			
			EnchereManager enchereManager = new EnchereManager();
			if (article.getEnchere() == null) {
				// J'ajoute l'enchere
				Enchere enchere = enchereManager.ajouterUneEnchere(article, montantEnchere, dateEnchere, acheteur);
				article.setEnchere(enchere);
			}else {
				// Je modifie l'enchere
				Enchere enchere = enchereManager.modifierEnchere(article, montantEnchere, dateEnchere, acheteur);
				article.setEnchere(enchere);
			}
			// Si tout se passe bien, je vais sur la page de l'article encherie:
			response.sendRedirect("./AfficherArticle?id=" + idArticle);

		} catch (BusinessException e) {
			// Sinon je retourne à la page de l'article pour indiquer les problèmes:
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			response.sendRedirect("./AfficherArticle?id=" + idArticle);
		}
	}

}
