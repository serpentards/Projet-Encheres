package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
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
	private static final String SELECT_BY_ID = "SELECT a.no_article,a.nom_article,a.description,a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur AS vendeur,a.no_categorie,e.date_enchere,e.montant_enchere,e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e on a.no_article=e.no_article WHERE a.no_article = ?;";
	private static final String SELECT_BY_ID_CATEGORIE = "SELECT a.no_article,a.nom_article,a.description,a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur AS vendeur,a.no_categorie,e.date_enchere,e.montant_enchere,e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e on a.no_article=e.no_article WHERE a.no_categorie = ?;";
	private static final String SELECT_BY_NOM_DESCRIPTION = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur AS vendeur, a.no_categorie, e.date_enchere, e.montant_enchere, e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON a.no_article = e.no_article WHERE nom_article LIKE ? OR description LIKE ?;";
	private static final String SELECT_BY_NOM_DESCRIPTION_ID_CATEGORIE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur AS vendeur, a.no_categorie, e.date_enchere, e.montant_enchere, e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON a.no_article = e.no_article WHERE (nom_article LIKE ? OR description LIKE ?) AND no_categorie = ?;";
	private static final String SELECT_ENCHERE_EN_COURS = "SELECT a.no_article,a.nom_article,a.description,a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur AS vendeur,a.no_categorie,e.date_enchere,e.montant_enchere, e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e on a.no_article=e.no_article  WHERE a.date_debut_encheres < GETDATE() AND a.date_fin_encheres > GETDATE();";
	private static final String SELECT_ENCHERE_PARTICIPE = "SELECT a.no_article,a.nom_article,a.description,a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur AS vendeur,a.no_categorie,e.date_enchere,e.montant_enchere,e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e on a.no_article=e.no_article WHERE a.date_debut_encheres < GETDATE() AND a.date_fin_encheres > GETDATE() AND e.no_utilisateur = ?;";
	private static final String SELECT_ENCHERE_GAGNE = "SELECT a.no_article,a.nom_article,a.description,a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur AS vendeur,a.no_categorie,e.date_enchere,e.montant_enchere,e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e on a.no_article=e.no_article WHERE a.date_fin_encheres < GETDATE() AND e.no_utilisateur = ?;";
	private static final String SELECT_MES_VENTES = "SELECT a.no_article,a.nom_article,a.description,a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur AS vendeur,a.no_categorie,e.date_enchere,e.montant_enchere,e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e on a.no_article=e.no_article WHERE a.no_utilisateur = ? ;";
	private static final String SELECT_MES_VENTES_NON_COMMENCER = "SELECT a.no_article,a.nom_article,a.description,a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur AS vendeur,a.no_categorie,e.date_enchere,e.montant_enchere,e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e on a.no_article=e.no_article WHERE a.no_utilisateur = ? AND a.date_debut_encheres > GETDATE();";
	private static final String SELECT_MES_VENTES_EN_COURS = "SELECT a.no_article,a.nom_article,a.description,a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur AS vendeur,a.no_categorie,e.date_enchere,e.montant_enchere,e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e on a.no_article=e.no_article WHERE a.date_debut_encheres < GETDATE() AND a.date_fin_encheres > GETDATE() AND a.no_utilisateur = ?;";
	private static final String SELECT_MES_VENTES_TERMINEES = "SELECT a.no_article,a.nom_article,a.description,a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur AS vendeur,a.no_categorie,e.date_enchere,e.montant_enchere,e.no_utilisateur AS acheteur FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e on a.no_article=e.no_article WHERE a.no_utilisateur = ? AND a.date_fin_encheres < GETDATE();";
	private static final String UPDATE_ARTICLE = "UPDATE ARTICLES_VENDUS SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, no_categorie = ? WHERE no_article = ?;";
	private static final String UPDATE_RETRAIT = "UPDATE RETRAITS SET rue = ?, code_postal= ?, ville= ? WHERE no_article = ?;";

	
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
	public ArticleVendu update(ArticleVendu article) throws BusinessException {
		Connection cnx = null;
		BusinessException be = new BusinessException();
		
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement psmt = cnx.prepareStatement(UPDATE_ARTICLE);
			cnx.setAutoCommit(false);

			psmt.setString(1, article.getNomArticle());
			psmt.setString(2, article.getDescription());
			psmt.setTimestamp(3, Timestamp.valueOf(article.getDateDebutEncheres()));
			psmt.setTimestamp(4, Timestamp.valueOf(article.getDateFinEncheres()));
			psmt.setInt(5, article.getMiseAPrix());
			psmt.setInt(6, article.getCategorieArticle().getNoCategorie());
			psmt.setInt(7, article.getNoArticle());
			int nbEnr = psmt.executeUpdate();
			
			if (nbEnr != 1) {
				be.ajouterErreur(CodesResultatDAL.UPDATE_ARTICLE_ECHEC);
			}
			psmt.close();
			psmt = cnx.prepareStatement(UPDATE_RETRAIT);

			psmt.setString(1, article.getLieuRetrait().getRue());
			psmt.setString(2, article.getLieuRetrait().getCode_postal());
			psmt.setString(3, article.getLieuRetrait().getVille());
			psmt.setInt(4, article.getNoArticle());
			nbEnr = psmt.executeUpdate();
			
			if (nbEnr != 1) {
				be.ajouterErreur(CodesResultatDAL.UPDATE_RETRAIT_ECHEC);
			}
			psmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			be.ajouterErreur(CodesResultatDAL.UPDATE_ARTICLE_ET_RETRAIT_ECHEC);

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

		return article;
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

	@Override
	public ArticleVendu selectById(int id) throws BusinessException {
		ArticleVendu articleCourant = new ArticleVendu();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(SELECT_BY_ID);) {
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();

			while (rs.next()) {
				if (rs.getInt("no_article") != articleCourant.getNoArticle()) {
					articleCourant = mappingArticleVendu(rs);
				}
			}

			return articleCourant;
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_ID_ECHEC);
			throw be;
		}
	}
	
	@Override
	public List<ArticleVendu> selectByCategorie(int idCategorie) throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(SELECT_BY_ID_CATEGORIE);) {
			psmt.setInt(1, idCategorie);
			ResultSet rs = psmt.executeQuery();
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
			be.ajouterErreur(CodesResultatDAL.SELECT_BY_ID_CATEGORIE_ERREUR);
			throw be;
		}
	}
	
	@Override
	public List<ArticleVendu> selectByNomDescription(String string) throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(SELECT_BY_NOM_DESCRIPTION);) {
			psmt.setString(1, "%" + string + "%");
			psmt.setString(2, "%" + string + "%");
			ResultSet rs = psmt.executeQuery();
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
			be.ajouterErreur(CodesResultatDAL.SELECT_BY_ID_CATEGORIE_ERREUR);
			throw be;
		}
	}
	
	@Override
	public List<ArticleVendu> selectByNomDescriptionCategorie(String string, int idCategorie) throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(SELECT_BY_NOM_DESCRIPTION_ID_CATEGORIE);) {
			psmt.setString(1, "%" + string + "%");
			psmt.setString(2, "%" + string + "%");
			psmt.setInt(3, idCategorie);
			ResultSet rs = psmt.executeQuery();
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
			be.ajouterErreur(CodesResultatDAL.SELECT_BY_ID_CATEGORIE_ERREUR);
			throw be;
		}
	}
	
	
	@Override
	public List<ArticleVendu> selectEncheresEnCours() throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection(); Statement smt = cnx.createStatement();) {
			ResultSet rs = smt.executeQuery(SELECT_ENCHERE_EN_COURS);
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
			be.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE_EN_COURS_ECHEC);
			throw be;
		}
	}
	
	
	@Override
	public List<ArticleVendu> selectEncheresParticipe(Utilisateur utilisateur) throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement psmt = cnx.prepareStatement(SELECT_ENCHERE_PARTICIPE);) {
			psmt.setInt(1, utilisateur.getNoUtilisateur());
			ResultSet rs = psmt.executeQuery();

			while (rs.next()) {
				listeArticles.add(mappingArticleVendu(rs));
			}

			return listeArticles;
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE_PARTICIPE_ECHEC);
			throw be;
		}
	}
	
	
	@Override
	public List<ArticleVendu> selectEncheresGagne(Utilisateur utilisateur) throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement psmt = cnx.prepareStatement(SELECT_ENCHERE_GAGNE);) {
			psmt.setInt(1, utilisateur.getNoUtilisateur());
			ResultSet rs = psmt.executeQuery();

			while (rs.next()) {
				listeArticles.add(mappingArticleVendu(rs));
			}

			return listeArticles;
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE_GAGNE_ECHEC);
			throw be;
		}
	}
	@Override
	public List<ArticleVendu> selectVentes(Utilisateur utilisateur) throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(SELECT_MES_VENTES);) {
			psmt.setInt(1, utilisateur.getNoUtilisateur());
			ResultSet rs = psmt.executeQuery();
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
			be.ajouterErreur(CodesResultatDAL.SELECT_MES_VENTES_ERREUR);
			throw be;
		}
	}

	@Override
	public List<ArticleVendu> selectVentesNonCommencees(Utilisateur utilisateur) throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(SELECT_MES_VENTES_NON_COMMENCER);) {
			psmt.setInt(1, utilisateur.getNoUtilisateur());
			ResultSet rs = psmt.executeQuery();
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
			be.ajouterErreur(CodesResultatDAL.SELECT_VENTES_NON_COMMENCER_ERREUR);
			throw be;
		}
	}

	@Override
	public List<ArticleVendu> selectVentesEnCours(Utilisateur utilisateur) throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(SELECT_MES_VENTES_EN_COURS);) {
			psmt.setInt(1, utilisateur.getNoUtilisateur());
			ResultSet rs = psmt.executeQuery();
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
			be.ajouterErreur(CodesResultatDAL.SELECT_MES_VENTES_EN_COURS_ERREUR);
			throw be;
		}
	}

	@Override
	public List<ArticleVendu> selectVenteTerminees(Utilisateur utilisateur) throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(SELECT_MES_VENTES_TERMINEES);) {
			psmt.setInt(1, utilisateur.getNoUtilisateur());
			ResultSet rs = psmt.executeQuery();
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
			be.ajouterErreur(CodesResultatDAL.SELECT_MES_VENTES_TERMINEES_ERREUR);
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
