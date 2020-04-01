package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Quand j'arrive sur la page je recupere les cookies
		Cookie[] cookies = request.getCookies();
		if (cookies.length > 1) {
			// Si j'ai recuperer des cookies
			// Je recupere les cookies d'identifiant et mot de passe
			String identifiantCookie = null;
			String mdpCookie = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("identifiant")) {
					identifiantCookie = cookie.getValue();
				} else if (cookie.getName().equals("mdp")) {
					mdpCookie = cookie.getValue();
				}
			}
			// Je recupere l'utilisateur avec ses identifiant et mot de passe
			Utilisateur utilisateur = connecterUtilisateur(identifiantCookie, mdpCookie, null);
			// Je connecte l'utilisateur
			/* Récupération de la session depuis la requête */
			request.getSession().setAttribute("sessionUtilisateur", utilisateur);
			// Si tout se passe bien, je vais vers la page de connection:
			RequestDispatcher rd = request.getRequestDispatcher("/Accueil");
			rd.forward(request, response);
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String mot_de_passe = request.getParameter("motdepasse");
		String checkbox = request.getParameter("souvenir");

		List<Integer> listeCodesErreur = new ArrayList<>();

		Utilisateur utilisateur = connecterUtilisateur(email, mot_de_passe, listeCodesErreur);

		// Si il y a des erreurs dans la liste
		if (listeCodesErreur.size() > 0) {
			// Je renvoie les codes d'erreurs
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			request.getSession().setAttribute("sessionUtilisateur", null);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			rd.forward(request, response);
		} else {
			// Si la case se souvenir de moi est cocher
			// Je créé un cookie avec ses identifiants
			if (checkbox != null) {
				Cookie cookieIdentifiant = new Cookie("identifiant", email);
				Cookie cookieMDP = new Cookie("mdp", mot_de_passe);
				cookieIdentifiant.setMaxAge(86400);
				cookieMDP.setMaxAge(86400);
				response.addCookie(cookieIdentifiant);
				response.addCookie(cookieMDP);
			}
			// Je connecte l'utilisateur
			/* Récupération de la session depuis la requête */
			request.getSession().setAttribute("sessionUtilisateur", utilisateur);
			// Si tout se passe bien, je vais vers la page d'accueil:
			response.sendRedirect("./Accueil");
		}

	}

	private Utilisateur connecterUtilisateur(String identifiant, String mot_de_passe, List<Integer> listeCodesErreur) {
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur = null;

		try {
			utilisateur = utilisateurManager.selectionnerUtilisateurAvecEmail(identifiant);
			if (utilisateur == null) {
				utilisateur = utilisateurManager.selectionnerUtilisateurAvecPseudo(identifiant);
				if (utilisateur == null) {
					listeCodesErreur.add(CodesResultatServlets.LOGIN_NEXISTE_PAS);
				} else if (!utilisateur.getMot_de_passe().equals(mot_de_passe)) {
					listeCodesErreur.add(CodesResultatServlets.MDP_DIFFERENT);
				}
			} else if (!utilisateur.getMot_de_passe().equals(mot_de_passe)) {
				listeCodesErreur.add(CodesResultatServlets.MDP_DIFFERENT);
			}

		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return utilisateur;

	}

}
