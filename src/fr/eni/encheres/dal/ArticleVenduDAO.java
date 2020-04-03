package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

public interface ArticleVenduDAO {
	
	void insert(ArticleVendu article) throws BusinessException;
	
	ArticleVendu update(ArticleVendu articleVendu) throws BusinessException;

	List<ArticleVendu> select() throws BusinessException;

	ArticleVendu selectById(int idArticle) throws BusinessException;

	List<ArticleVendu> selectByCategorie(int idCategorie) throws BusinessException;
	
	List<ArticleVendu> selectByNomDescription(String string) throws BusinessException;
	
	List<ArticleVendu> selectByNomDescriptionCategorie(String string, int idCategorie) throws BusinessException;
	
	List<ArticleVendu> selectEncheresEnCours() throws BusinessException;
	
	List<ArticleVendu> selectEncheresParticipe(Utilisateur utilisateur) throws BusinessException;
	
	List<ArticleVendu> selectEncheresGagne(Utilisateur utilisateur) throws BusinessException;
	
	List<ArticleVendu> selectVentes(Utilisateur utilisateur) throws BusinessException;
    
	List<ArticleVendu> selectVentesNonCommencees(Utilisateur utilisateur) throws BusinessException;
    
	List<ArticleVendu> selectVentesEnCours(Utilisateur utilisateur) throws BusinessException;
    
	List<ArticleVendu> selectVenteTerminees(Utilisateur utilisateur) throws BusinessException;

}
