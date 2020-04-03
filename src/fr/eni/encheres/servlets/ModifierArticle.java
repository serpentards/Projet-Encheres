package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleVenduManager;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ModifierArticle
 */
@WebServlet("/ModifierArticle")
public class ModifierArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ArticleVendu article = (ArticleVendu) request.getSession().getAttribute("article");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierArticle.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/* Récupération des champs du formulaire. */
		request.setCharacterEncoding("UTF-8");
		String idArticle = request.getParameter("idArticle");
		int id = 0;
		if (idArticle != null) {
			id = Integer.parseInt(idArticle);
		}
		
		String nomArticle = request.getParameter("article");
		String description = request.getParameter("description");
		LocalDateTime dateDebutEncheres = null;
		LocalDateTime dateFinEncheres = null;
		int miseAPrix = Integer.parseInt(request.getParameter("prix"));
		int prixVente = 0;
		int etatVente = 1;

		// Association
		Enchere enchere = null;
		Categorie categorieArticle = new Categorie();
		Retrait lieuRetrait = null;
		Utilisateur vendeur = null;

		List<Integer> listeCodesErreur = new ArrayList<>();

		// CATEGORIE
		String[] champCategorie = request.getParameter("categorieChoisie").split(",");
		categorieArticle.setNoCategorie(Integer.parseInt(champCategorie[0]));
		categorieArticle.setLibelle(champCategorie[1]);

		// UTILISATEUR
		vendeur = (Utilisateur) request.getSession().getAttribute("sessionUtilisateur");

		// LIEU DE RETRAIT
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");

		// Remplissage automatique du lieu de retrait si aucun n'est specifier
		if (rue.isEmpty()) {
			rue = vendeur.getRue();
		}
		if (codePostal.isEmpty()) {
			codePostal = vendeur.getCode_postal();
		}
		if (ville.isEmpty()) {
			ville = vendeur.getVille();
		}

		lieuRetrait = new Retrait(rue, codePostal, ville);

		// Formatage des dates
		try {
			dateDebutEncheres = LocalDateTime.parse(request.getParameter("dateDebut"));
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.FORMAT_DATE_DEBUT_ERREUR);
		}
		try {
			dateFinEncheres = LocalDateTime.parse(request.getParameter("dateFin"));
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.FORMAT_DATE_FIN_ERREUR);
		}

		try {
			// Si il y a des erreurs dans la liste
			if (listeCodesErreur.size() > 0) {
				// Je renvoie les codes d'erreurs
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierArticle.jsp");
				rd.forward(request, response);
			} else {
				// Je modifie l'article
				ArticleVenduManager articleVenduManager = new ArticleVenduManager();
				ArticleVendu article = articleVenduManager.updateArticle(id, nomArticle, description, dateDebutEncheres,
						dateFinEncheres, miseAPrix, prixVente, etatVente, enchere, categorieArticle, lieuRetrait,
						vendeur);
				// Si tout se passe bien, je vais vers la page d'Acceuil:
				response.sendRedirect("./AfficherArticle?id=" + article.getNoArticle());
			}
		} catch (BusinessException e) {
			// Sinon je retourne à la page de modification de l'article pour indiquer les problèmes:
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierArticle.jsp");
			rd.forward(request, response);

		}
	}
}
