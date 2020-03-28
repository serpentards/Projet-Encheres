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
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String mot_de_passe = request.getParameter("motdepasse");
		
		List<Integer> listeCodesErreur=new ArrayList<>();
		
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		
		try {
			Utilisateur utilisateur = utilisateurManager.selectionnerUtilisateurAvecEmail(email);
			
			if (utilisateur == null) {
				utilisateur = utilisateurManager.selectionnerUtilisateurAvecPseudo(email);
				if (utilisateur == null) {
					listeCodesErreur.add(CodesResultatServlets.LOGIN_NEXISTE_PAS);
				}else if (!utilisateur.getMot_de_passe().equals(mot_de_passe)) {
					listeCodesErreur.add(CodesResultatServlets.MDP_DIFFERENT);
				}
			}else if (!utilisateur.getMot_de_passe().equals(mot_de_passe)) {
				
				listeCodesErreur.add(CodesResultatServlets.MDP_DIFFERENT);
			}
			
			//Si il y a des erreurs dans la liste
			if(listeCodesErreur.size()>0)
			{
				//Je renvoie les codes d'erreurs
				request.setAttribute("listeCodesErreur",listeCodesErreur);
				request.getSession().setAttribute( "sessionUtilisateur", null );
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
				rd.forward(request, response);
			}
			else {
				 //Je connecte l'utilisateur
				/* Récupération de la session depuis la requête */
		        request.getSession().setAttribute( "sessionUtilisateur", utilisateur );
				//Si tout se passe bien, je vais vers la page de connection:
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
				rd.forward(request, response);
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
