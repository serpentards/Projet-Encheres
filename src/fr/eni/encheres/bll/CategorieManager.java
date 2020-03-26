package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exception.BusinessException;

public class CategorieManager {

	private CategorieDAO categorieDAO;

	public CategorieManager() {
		categorieDAO = DAOFactory.getCategorieDAO();
	}

	public List<Categorie> selectionnerToutesLesCategories() throws BusinessException {
		return categorieDAO.select();
	}
}
