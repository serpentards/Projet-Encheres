package fr.eni.encheres.filter;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FiltreRestriction
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
		DispatcherType.ERROR }, servletNames = { "AfficherArticle" }, urlPatterns = { "/AfficherArticle" })
public class FiltreRestriction implements Filter {

	private static final String PAGES_RESTREINTES_SESSION_UTILISATEUR = "sessionUtilisateur";
	private static final String ACCUEIL_PAGE_PUBLIC = "/Accueil";

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// System.out.println("passage dans le filtre");
		// HttpServletRequest requestHTTP = (HttpServletRequest) request;
		//
		// System.out.println("Accès depuis"+ request.getRemoteAddr()+ "sur l'URL" +
		// request.getServletContext().getContextPath());

		// Récupération de la session de la requete en cours
		HttpSession session = ((HttpServletRequest) request).getSession();

		/**
		 * Si l'objet utilisateur n'existe pas dans la session en cours, alors
		 * l'utilisateur n'est pas connecté.
		 */
		if (session.getAttribute(PAGES_RESTREINTES_SESSION_UTILISATEUR) == null) {
			/* Redirection vers la page publique accueil */
			((HttpServletResponse) response)
					.sendRedirect(((HttpServletRequest) request).getContextPath() + ACCUEIL_PAGE_PUBLIC);
		} else {

			// si connecté alors la requete suit son cours et l'on affiche à l'utilisateur
			// la ressource demandée

			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

}
