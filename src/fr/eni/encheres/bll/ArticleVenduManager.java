package fr.eni.encheres.bll;

import java.time.LocalDateTime;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleVenduDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exception.BusinessException;

public class ArticleVenduManager {
	private ArticleVenduDAO articleVenduDAO;
	
	public ArticleVenduManager() {
		articleVenduDAO = DAOFactory.getArticleVenduDAO();
	}
	
	public ArticleVendu ajouterArticle(String nomArticle, String description, LocalDateTime dateDebutEncheres, LocalDateTime dateFinEncheres,
			int miseAPrix, int prixVente, int etatVente, Enchere enchere, Categorie categorieArticle,
			Retrait lieuRetrait, Utilisateur vendeur)throws BusinessException{
		BusinessException be = new BusinessException();
		//valider les champs
		validerNom(nomArticle, be);
		validerDescription(description, be);
		validerDates(dateDebutEncheres, dateFinEncheres, be);
		validerMiseAPrix(miseAPrix, be);
		
		ArticleVendu article = null;
		if(!be.hasErreurs()) {
			article = new ArticleVendu(nomArticle, description, dateDebutEncheres, dateFinEncheres, miseAPrix, prixVente, etatVente, enchere, categorieArticle, lieuRetrait, vendeur);
			articleVenduDAO.insert(article);
		}else {
			throw be;
		}
		return article;
	}
	
	public List<ArticleVendu> selectionnerTousLesArticles() throws BusinessException {
		return articleVenduDAO.select();
	}
	
	public ArticleVendu selectionnerArticleParId(int idArticle) throws BusinessException {
		return articleVenduDAO.selectById(idArticle);
	}
	
	public List<ArticleVendu> selectionnerArticleParCategorie(int idCategorie) throws BusinessException {
		return articleVenduDAO.selectByCategorie(idCategorie);
	}
	
	public List<ArticleVendu> selectionnerArticleParNomEtDescription(String string) throws BusinessException {
		return articleVenduDAO.selectByNomDescription(string);
	}
	
	public List<ArticleVendu> selectionnerArticleParNomDescriptionEtCategorie(String string, int idCategorie) throws BusinessException {
		return articleVenduDAO.selectByNomDescriptionCategorie(string, idCategorie);
	}
	
	public List<ArticleVendu> selectionnerEncheresEnCours() throws BusinessException {
		return articleVenduDAO.selectEncheresEnCours();
	}
	
	public List<ArticleVendu> selectionnerEncheresParticipe(Utilisateur utilisateur) throws BusinessException {
		return articleVenduDAO.selectEncheresParticipe(utilisateur);
	}
	
	public List<ArticleVendu> selectionnerEncheresGagne(Utilisateur utilisateur) throws BusinessException {
		return articleVenduDAO.selectEncheresGagne(utilisateur);
	}
	
	public List<ArticleVendu> selectionnerVentes(Utilisateur utilisateur) throws BusinessException {
		return articleVenduDAO.selectVentes(utilisateur);
	}
    
	public List<ArticleVendu> selectionnerVentesNonCommencees(Utilisateur utilisateur) throws BusinessException {
		return articleVenduDAO.selectVentesNonCommencees(utilisateur);
	}
    
	public List<ArticleVendu> selectionnerVentesEnCours(Utilisateur utilisateur) throws BusinessException {
		return articleVenduDAO.selectVentesEnCours(utilisateur);
	}
    
	public List<ArticleVendu> selectionnerVenteTerminees(Utilisateur utilisateur) throws BusinessException {
		return articleVenduDAO.selectVenteTerminees(utilisateur);
	}
	
	private void validerNom(String nomArticle, BusinessException be) {
		if ( nomArticle == null || nomArticle.trim().length() < 3 ) {
			be.ajouterErreur(CodesResultatBLL.NOM_ARTICLE_ERREUR);
		}
	}
	
	private void validerDescription(String description, BusinessException be) {
		if ( description == null || description.trim().length() < 30 ) {
			be.ajouterErreur(CodesResultatBLL.DESCRIPTION_ARTICLE_ERREUR);
		}
	}
	
	private void validerDates(LocalDateTime dateDebutEncheres, LocalDateTime dateFinEncheres, BusinessException be) {
		LocalDateTime now = LocalDateTime.now().minusMinutes(1);
		if(dateDebutEncheres == null || dateFinEncheres == null || dateDebutEncheres.isBefore(now)|| dateFinEncheres.isEqual(now) || dateFinEncheres.isBefore(now) || dateDebutEncheres.isAfter(dateFinEncheres)) {
			be.ajouterErreur(CodesResultatBLL.DATE_ERREUR);
		}
	}
	
	private void validerMiseAPrix(int miseAPrix, BusinessException be) {
		if ( miseAPrix <= 0 ) {
			be.ajouterErreur(CodesResultatBLL.PRIX_ARTICLE_INCORRECT);
		}
	}
}
