package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dal.ArticleVenduDAO;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.exception.BusinessException;

public class ArticleVenduDAOJdbcImpl implements ArticleVenduDAO {

	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) VALUES(?,?,?,?,?,?,?,?)";

	@Override
	public void insert(ArticleVendu article) throws BusinessException {
		Connection cnx = null;
		BusinessException be = new BusinessException();
		try {
			cnx = ConnectionProvider.getConnection();
			//Pour prendre la main sur la transaction
			PreparedStatement psmt = cnx.prepareStatement(INSERT_ARTICLE , PreparedStatement.RETURN_GENERATED_KEYS);
			psmt.setString(1, article.getNomArticle());
			psmt.setString(2, article.getDescription());
			psmt.setDate(3, Date.valueOf(article.getDateDebutEncheres()));
			psmt.setDate(4, Date.valueOf(article.getDateFinEncheres()));
			psmt.setInt(5, article.getMiseAPrix());
			psmt.setInt(6, article.getPrixVente());
			psmt.setInt(7, article.getVendeur().getNoUtilisateur());
			psmt.setInt(8, article.getCategorieArticle().getNoCategorie());
			int nombreEnregistrementInsere = psmt.executeUpdate();
			if(nombreEnregistrementInsere == 1) {
				ResultSet rs = psmt.getGeneratedKeys();
				if(rs.next()) {
					article.setNoArticle((rs.getInt(1)));
				}
				rs.close();
				
			}else {
				be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			}
			psmt.close();
			cnx.close();
		}catch (Exception e) {
			e.printStackTrace();
			be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
		}finally {
			try {
				cnx.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(be.hasErreurs()) {
				throw be;
			}
		}

	}

}
