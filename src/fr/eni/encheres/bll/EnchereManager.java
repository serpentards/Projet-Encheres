package fr.eni.encheres.bll;

import java.time.LocalDateTime;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.exception.BusinessException;

public class EnchereManager {
	private EnchereDAO enchereDAO;
	
	public EnchereManager() {
		enchereDAO = DAOFactory.getEnchereDAO();
	}
	
	public Enchere ajouterUneEnchere(ArticleVendu article,int montantEnchere,LocalDateTime dateEnchere, Utilisateur acheteur) throws BusinessException{
		BusinessException be = new BusinessException();
		//valider les champs
		validerMontant(article, montantEnchere, be);
		validerCredit(acheteur, montantEnchere, be);
		
		Enchere enchere = null;
		if(!be.hasErreurs()) {
			enchere = new Enchere(dateEnchere, montantEnchere, acheteur);
			enchereDAO.insert(enchere, article);
		}else {
			throw be;
		}
		return enchere;
	}
	

	public Enchere modifierEnchere(ArticleVendu article,int montantEnchere,LocalDateTime dateEnchere, Utilisateur acheteur) throws BusinessException {
		BusinessException be = new BusinessException();
		//valider les champs
		validerMontant(article, montantEnchere, be);
		validerCredit(acheteur, montantEnchere, be);
		
		Enchere enchere = null;
		if(!be.hasErreurs()) {
			enchere = new Enchere(dateEnchere, montantEnchere, acheteur);
			enchereDAO.update(enchere, article);
		}else {
			throw be;
		}
		return enchere;
	}
	
	private void validerCredit(Utilisateur acheteur, int montantEnchere, BusinessException be) {
		if (acheteur != null) {
			if (acheteur.getCredit() != 0) {
				if (acheteur.getCredit() < montantEnchere) {
					be.ajouterErreur(CodesResultatBLL.CREDIT_INSUFFISANT);
				}
			}else {
				be.ajouterErreur(CodesResultatBLL.CREDIT_ZERO);
			}
		}else {
			be.ajouterErreur(CodesResultatBLL.ETAT_CONNECTER_ERREUR);
		}
	}

	private void validerMontant(ArticleVendu article, int montantEnchere, BusinessException be) {
		if (article.getEnchere() != null) {
			//Il y a une enchere pour cette article
			if (montantEnchere <= article.getEnchere().getMontantEnchere()) {
				//On regarde si le montant de l'enchere est superieur a la derniere enchere
				be.ajouterErreur(CodesResultatBLL.MONTANT_ENCHERE_ERREUR);
			}
		}else if (montantEnchere <= article.getMiseAPrix()) {
			//Il n'y a pas d'enchere
			//On regarde si le montant de l'enchere est superieur a la mise a prix
			be.ajouterErreur(CodesResultatBLL.MONTANT_ENCHERE_ERREUR);
		}
		
	}
}
