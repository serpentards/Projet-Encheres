package fr.eni.encheres.dal;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.exception.BusinessException;

public interface EnchereDAO {
	
	void insert(Enchere enchere, ArticleVendu article) throws BusinessException;
	
	void update(Enchere enchere, ArticleVendu article) throws BusinessException;

}
