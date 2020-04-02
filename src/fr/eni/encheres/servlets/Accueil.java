package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleVenduManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class Acceuil
 */
@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		List<Categorie> listCategories = new ArrayList<Categorie>();
		
		try {
			CategorieManager categorieManager = new CategorieManager();
			listCategories = categorieManager.selectionnerToutesLesCategories();
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		this.getServletContext().setAttribute("categories", listCategories);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleVenduManager articleVenduManager = new ArticleVenduManager();
		
		try {
			List<ArticleVendu> listArticle = articleVenduManager.selectionnerTousLesArticles();
			request.getSession().setAttribute("listArticle", listArticle);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomRechercher = request.getParameter("nomRechercher");
		if (!request.getParameter("categorieChoisie").isEmpty()) {
			int idCategorie = Integer.parseInt(request.getParameter("categorieChoisie"));
			
			ArticleVenduManager articleVenduManager = new ArticleVenduManager();
			
			try {
				List<ArticleVendu> listArticle = articleVenduManager.selectionnerArticleParCategorie(idCategorie);
				request.setAttribute("listArticle", listArticle);
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			rd.forward(request, response);
		}else {
			doGet(request, response);
		}
		
		
	}

}
