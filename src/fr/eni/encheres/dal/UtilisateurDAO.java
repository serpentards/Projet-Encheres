package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

public interface UtilisateurDAO {
	void insert(Utilisateur utilisateur) throws BusinessException;
	
	void update(Utilisateur utilisateur) throws BusinessException;
	
	void delete(int id) throws BusinessException;

	List<Utilisateur> select() throws BusinessException;

	Utilisateur selectById(int id) throws BusinessException;
	
	Utilisateur selectByEmail(String email) throws BusinessException;

	Utilisateur selectByPseudo(String pseudo) throws BusinessException;
}
