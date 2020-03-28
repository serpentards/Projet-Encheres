package fr.eni.encheres.bll;

import java.time.LocalDate;
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
	
	public ArticleVendu ajouterArticle(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres,
			int miseAPrix, int prixVente, int etatVente, List<Enchere> listEncheres, Categorie categorieArticle,
			Retrait lieuRetrait, Utilisateur vendeur)throws BusinessException{
		BusinessException be = new BusinessException();
		//valider les champs
		validerNom(nomArticle, be);
		validerDescription(description, be);
		validerDates(dateDebutEncheres, dateFinEncheres, be);
		validerMiseAPrix(miseAPrix, be);
		
		ArticleVendu article = null;
		if(!be.hasErreurs()) {
			article = new ArticleVendu(nomArticle, description, dateDebutEncheres, dateFinEncheres, miseAPrix, prixVente, etatVente, listEncheres, categorieArticle, lieuRetrait, vendeur);
			articleVenduDAO.insert(article);
		}else {
			throw be;
		}
		return article;
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
	
	private void validerDates(LocalDate dateDebutEncheres, LocalDate dateFinEncheres, BusinessException be) {
		LocalDate now = LocalDate.now();
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
