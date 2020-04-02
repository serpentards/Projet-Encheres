package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.exception.BusinessException;

public class EnchereDAOJdbcImpl implements EnchereDAO {

	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_ENCHERE = "UPDATE ENCHERES SET no_utilisateur=?, date_enchere=?, montant_enchere=? WHERE no_article=?;";

	@Override
	public void insert(Enchere enchere, ArticleVendu article) throws BusinessException {

		BusinessException be = new BusinessException();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(INSERT_ENCHERE);
			){
			// Pour prendre la main sur la transaction
			
			psmt.setInt(1, enchere.getAcheteur().getNoUtilisateur());
			psmt.setInt(2, article.getNoArticle());
			psmt.setTimestamp(3, Timestamp.valueOf(enchere.getDateEnchere()));
			psmt.setInt(4, enchere.getMontantEnchere());
			
			int nombreEnregistrementInsere = psmt.executeUpdate();
			if (nombreEnregistrementInsere != 1) {
				be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
		}
	}
	
	@Override
	public void update(Enchere enchere, ArticleVendu article) throws BusinessException {
		BusinessException be = new BusinessException();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(UPDATE_ENCHERE);) {
			psmt.setInt(1, enchere.getAcheteur().getNoUtilisateur());
			psmt.setTimestamp(2, Timestamp.valueOf(enchere.getDateEnchere()));
			psmt.setInt(3, enchere.getMontantEnchere());
			psmt.setInt(4, article.getNoArticle());

			int nbEnr = psmt.executeUpdate();
			if (nbEnr != 1) {
				be.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
			}

		} catch (Exception e) {
			e.printStackTrace();
			be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);

		}
	}

}
