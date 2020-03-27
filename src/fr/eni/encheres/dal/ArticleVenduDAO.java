package fr.eni.encheres.dal;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.exception.BusinessException;

public interface ArticleVenduDAO {
	
	void insert(ArticleVendu article) throws BusinessException;

}
