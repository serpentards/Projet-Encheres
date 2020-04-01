package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.exception.BusinessException;

public interface ArticleVenduDAO {
	
	void insert(ArticleVendu article) throws BusinessException;

	List<ArticleVendu> select() throws BusinessException;

	ArticleVendu selectById(int idArticle) throws BusinessException;

	List<ArticleVendu> selectByCategorie(int idCategorie) throws BusinessException;

}
