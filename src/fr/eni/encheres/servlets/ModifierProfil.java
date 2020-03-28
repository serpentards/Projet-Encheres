package fr.eni.encheres.servlets;

import java.io.IOException;

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
 * Servlet implementation class ModifierProfil
 */
@WebServlet("/ModifierProfil")
public class ModifierProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur u = null;
		//récuperation de l'utilisateur dans le contexte de session
		u = (Utilisateur) request.getSession().getAttribute("sessionUtilisateur");
		
		//récuperation de tout les champs modifiable depuis la jsp
		String nom=request.getParameter("nom");
		String prenom=request.getParameter("prenom");
		String email=request.getParameter("email");
		String telephone=request.getParameter("telephone");
		String rue=request.getParameter("rue");
		String codePostal=request.getParameter("cp");
		String ville=request.getParameter("ville");
		String motDePasse=request.getParameter("mdp");
		String mdpConf=request.getParameter("conf");
		
		/*
         * recuperation de l'email de l'utilisateur courant 
         * pour verifier dans UtilisateurManager si l'email à été modifié
         * si il a été modifié alors il faudra le valider sinon rien 
         */
		String emailCourant = u.getEmail();
		
		//mise à jour de l'utilisateur courant
		u.setNom(nom);
		u.setPrenom(prenom);
		u.setEmail(email);
		u.setTelephone(telephone);
	    u.setRue(rue);
		u.setCode_postal(codePostal);
		u.setVille(ville);
		u.setMot_de_passe(motDePasse);

		request.setCharacterEncoding("UTF-8");
		
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		
		try {
			utilisateurManager.updateUtilisateur(u,mdpConf,emailCourant);
			request.getSession().setAttribute("sessionUtilisateur", u);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			rd.forward(request, response);
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp");
			rd.forward(request, response);
		}
		
	
	}

}
