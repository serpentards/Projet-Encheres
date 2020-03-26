package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.exception.BusinessException;

public interface CategorieDAO {
	
	List<Categorie> select() throws BusinessException;

}
