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

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Inscription.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        /* Récupération des champs du formulaire. */
    	
    	String pseudo = request.getParameter("pseudo");
    	String prenom = request.getParameter("prenom");    
    	String telephone = request.getParameter("telephone");
    	String code_postal = request.getParameter("code_postal");
    	String mot_de_passe = request.getParameter("motdepasse");
    	
        String nom = request.getParameter("nom");            
        String email = request.getParameter("email");
        String rue = request.getParameter("rue");
        String ville = request.getParameter("ville");
        String confirmation = request.getParameter("confirmation");
        int credit = 0;
        boolean administrateur = false;
        List<Enchere> listEnchere = null;
        List<ArticleVendu> listAchat = null; 
        List<ArticleVendu> listVente = null; 
                                      
        
        List<Integer> listeCodesErreur=new ArrayList<>();
        
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		try {
			if (utilisateurManager.selectionnerUtilisateurAvecEmail(email) != null) {
				listeCodesErreur.add(CodesResultatServlets.EMAIL_EXISTE_DEJA);
			}
			if (utilisateurManager.selectionnerUtilisateurAvecPseudo(pseudo) != null) {
				listeCodesErreur.add(CodesResultatServlets.PSEUDO_EXISTE_DEJA);
			}
			//Si il y a des erreurs dans la liste
			if(listeCodesErreur.size()>0)
			{
				//Je renvoie les codes d'erreurs
				request.setAttribute("listeCodesErreur",listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Inscription.jsp");
				rd.forward(request, response);
			}
			else {
				 //J'ajoute l'utilisateur
				utilisateurManager.ajouterUtilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, confirmation, credit, administrateur);
				//Et je le connecte
				request.getSession().setAttribute( "sessionUtilisateur", new Utilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur, listEnchere, listAchat, listVente) );
				//Si tout se passe bien, je vais vers la page d'Acceuil:
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Acceuil.jsp");
				rd.forward(request, response);
			}
		} catch (BusinessException e) {
			//Sinon je retourne à la page d'inscription pour indiquer les problèmes:
			e.printStackTrace();
			request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Inscription.jsp");
			rd.forward(request, response);
			
		}
    }
}
