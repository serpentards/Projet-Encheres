package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletPourAjouterUnArticleAVendre
 */
@WebServlet("/AjoutVente")
public class ServletPourAjouterUnArticleAVendre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	public void init() throws ServletException {
		
		List<Categorie> listCategories = new ArrayList<Categorie>();
		
		try {
			CategorieManager categorieManager = new CategorieManager();
			listCategories = categorieManager.selectionnerToutesLesCategories();
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		this.getServletContext().setAttribute("categories", listCategories);
		super.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("sessionUtilisateur") != null) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/vendre.jsp");
			rd.forward(request, response);
		}else {
			response.sendRedirect("./Connexion");
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 /* Récupération des champs du formulaire. */
    	request.setCharacterEncoding("UTF-8");
    	String nomArticle = request.getParameter("article");
    	String description = request.getParameter("description");
    	LocalDateTime dateDebutEncheres = null;
    	LocalDateTime dateFinEncheres = null;
    	int miseAPrix = Integer.parseInt(request.getParameter("prix"));
    	int prixVente = 0;
    	int etatVente = 1;
    	
    	//Association
    	Enchere enchere = null;
    	Categorie categorieArticle = new Categorie();
    	Retrait lieuRetrait = null;
    	Utilisateur vendeur = null;
        
        List<Integer> listeCodesErreur=new ArrayList<>();
        
       // CATEGORIE
        String[] champCategorie = request.getParameter("categorieChoisie").split(",");
        categorieArticle.setNoCategorie(Integer.parseInt(champCategorie[0]));
        categorieArticle.setLibelle(champCategorie[1]);
        
        //UTILISATEUR
        vendeur = (Utilisateur) request.getSession().getAttribute("sessionUtilisateur");
        
        //LIEU DE RETRAIT
        String rue = request.getParameter("rue");            
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        
        //Remplissage automatique du lieu de retrait si aucun n'est specifier
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
        
        //Formatage des dates
        try
        {
        	String dateDebut = request.getParameter("dateDebut");
        	String heureDebut = request.getParameter("heureDebut");
        	dateDebutEncheres = LocalDateTime.parse(dateDebut+"T"+heureDebut);
        }
        catch(DateTimeParseException e)
        {
        	e.printStackTrace();
        	listeCodesErreur.add(CodesResultatServlets.FORMAT_DATE_DEBUT_ERREUR);
        }
        try
        {
        	String dateFin = request.getParameter("dateFin");
        	String heureFin = request.getParameter("heureFin");
        	dateFinEncheres = LocalDateTime.parse(dateFin+"T"+heureFin);
        }
        catch(DateTimeParseException e)
        {
        	e.printStackTrace();
        	listeCodesErreur.add(CodesResultatServlets.FORMAT_DATE_FIN_ERREUR);
        }
        
		try {
			//Si il y a des erreurs dans la liste
			if(listeCodesErreur.size()>0)
			{
				//Je renvoie les codes d'erreurs
				request.setAttribute("listeCodesErreur",listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/vendre.jsp");
				rd.forward(request, response);
			}
			else {
				 //J'ajoute l'article
				ArticleVenduManager articleVenduManager = new ArticleVenduManager();
				articleVenduManager.ajouterArticle(nomArticle, description, dateDebutEncheres, dateFinEncheres, miseAPrix, prixVente, etatVente, enchere, categorieArticle, lieuRetrait, vendeur);
				//Si tout se passe bien, je vais vers la page d'Acceuil:
				response.sendRedirect("./Accueil");
			}
		} catch (BusinessException e) {
			//Sinon je retourne à la page d'inscription pour indiquer les problèmes:
			e.printStackTrace();
			request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/vendre.jsp");
			rd.forward(request, response);
			
		}
	}

}
