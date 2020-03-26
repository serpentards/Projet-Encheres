package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.exception.BusinessException;

public class CategorieDAOJdbcImpl implements CategorieDAO {

	private static final String SELECT_ALL = "SELECT no_categorie,libelle FROM CATEGORIES;";

	@Override
	public List<Categorie> select() throws BusinessException {
		List<Categorie> listeCategories = new ArrayList<Categorie>();
		try (Connection cnx = ConnectionProvider.getConnection(); Statement smt = cnx.createStatement();) {
			ResultSet rs = smt.executeQuery(SELECT_ALL);
			Categorie categorieCourante = new Categorie();
			while (rs.next()) {
				if (rs.getInt("no_categorie") != categorieCourante.getNoCategorie()) {
					categorieCourante = mappingCategorie(rs);
					listeCategories.add(categorieCourante);
				}
			}
			return listeCategories;
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_ALL_ECHEC);
			throw be;
		}
	}

	private Categorie mappingCategorie(ResultSet rs) throws SQLException {
		Categorie newCategorie = new Categorie();
		newCategorie.setNoCategorie(rs.getInt("no_categorie"));
		newCategorie.setLibelle(rs.getString("libelle"));

		return newCategorie;
	}

}
