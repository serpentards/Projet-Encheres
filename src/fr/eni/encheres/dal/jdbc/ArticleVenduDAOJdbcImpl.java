package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleVenduDAO;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.exception.BusinessException;

public class ArticleVenduDAOJdbcImpl implements ArticleVenduDAO {

	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) VALUES(?,?,?,?,?,?,?,?)";
	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS (no_article,rue,code_postal,ville) VALUES (? ,? ,? ,?)";
	private static final String SELECT_ALL = "SELECT a.no_article,a.nom_article,a.description,a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur AS vendeur,a.no_categorie,e.date_enchere,e.montant_enchere,e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e on a.no_article=e.no_article;";

	@Override
	public void insert(ArticleVendu article) throws BusinessException {

		Connection cnx = null;
		BusinessException be = new BusinessException();
		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);
			// Pour prendre la main sur la transaction
			PreparedStatement psmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
			psmt.setString(1, article.getNomArticle());
			psmt.setString(2, article.getDescription());
			psmt.setTimestamp(3, Timestamp.valueOf(article.getDateDebutEncheres()));
			psmt.setTimestamp(4, Timestamp.valueOf(article.getDateFinEncheres()));
			psmt.setInt(5, article.getMiseAPrix());
			psmt.setInt(6, article.getPrixVente());
			psmt.setInt(7, article.getVendeur().getNoUtilisateur());
			psmt.setInt(8, article.getCategorieArticle().getNoCategorie());
			int nombreEnregistrementInsere = psmt.executeUpdate();
			if (nombreEnregistrementInsere == 1) {
				ResultSet rs = psmt.getGeneratedKeys();
				if (rs.next()) {
					article.setNoArticle(rs.getInt(1));
				}
				rs.close();

			} else {
				be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			}
			psmt.close();

			psmt = cnx.prepareStatement(INSERT_RETRAIT, PreparedStatement.RETURN_GENERATED_KEYS);
			Retrait retrait = article.getLieuRetrait();
			psmt.setInt(1, article.getNoArticle());
			psmt.setString(2, retrait.getRue());
			psmt.setString(3, retrait.getCode_postal());
			psmt.setString(4, retrait.getVille());
			nombreEnregistrementInsere = psmt.executeUpdate();
			if (nombreEnregistrementInsere != 1) {
				be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			}

			psmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			try {
				cnx.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				cnx.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (be.hasErreurs()) {
				throw be;
			}
		}
	}

	@Override
	public List<ArticleVendu> select() throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection(); Statement smt = cnx.createStatement();) {
			ResultSet rs = smt.executeQuery(SELECT_ALL);
			ArticleVendu articleCourant = new ArticleVendu();
			while (rs.next()) {
				if (rs.getInt("no_article") != articleCourant.getNoArticle()) {
					articleCourant = mappingArticleVendu(rs);
					listeArticles.add(articleCourant);
				}
			}

			return listeArticles;
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_ALL_ARTICLE_ECHEC);
			throw be;
		}
	}

	private ArticleVendu mappingArticleVendu(ResultSet rs) throws BusinessException, SQLException {
		ArticleVendu newArticleVendu = new ArticleVendu();
		UtilisateurManager u1 = new UtilisateurManager();
		CategorieManager categorieManager = new CategorieManager();
		newArticleVendu.setNoArticle(rs.getInt("no_article"));
		newArticleVendu.setNomArticle(rs.getString("nom_article"));
		newArticleVendu.setDescription(rs.getString("description"));
		newArticleVendu.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
		newArticleVendu.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
		newArticleVendu.setMiseAPrix(rs.getInt("prix_initial"));
		newArticleVendu.setPrixVente(rs.getInt("prix_vente"));
		newArticleVendu.setVendeur(u1.selectionnerUtilisateurAvecId(rs.getInt("vendeur")));
		newArticleVendu.setCategorieArticle(categorieManager.selectionnerCategorieParId(rs.getInt("no_categorie")));

		if (rs.getTimestamp("date_enchere") != null) {
			LocalDateTime date = rs.getTimestamp("date_enchere").toLocalDateTime();
			int montant = rs.getInt("montant_enchere");
			Utilisateur utilisateur = u1.selectionnerUtilisateurAvecId(rs.getInt("acheteur"));
			Enchere enchere = new Enchere(date, montant, utilisateur);
			newArticleVendu.setEnchere(enchere);
		}else {
			newArticleVendu.setEnchere(null);
		}
		
		return newArticleVendu;
	}

}
