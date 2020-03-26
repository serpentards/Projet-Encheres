package fr.eni.encheres.dal.jdbc;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BusinessException;

@WebServlet("/testJDBC")
public class TestJDBC extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String email = "salut";
		try {
			UtilisateurDAO dao = DAOFactory.getUtilisateurDAO();
			Utilisateur reponseSelectEmail = dao.selectByEmail(email);
			if (reponseSelectEmail != null) {
				req.setAttribute("reponseSelectEmail", reponseSelectEmail);
			}
			
		} catch (BusinessException e) {
			e.printStackTrace();
			req.setAttribute("BusinessException", e);
		}
		RequestDispatcher rd = req.getRequestDispatcher("test.jsp");
		rd.forward(req, resp);
	}
}
