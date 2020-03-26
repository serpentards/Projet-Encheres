package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BusinessException;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
	private static final String SELECT_ALL = "SELECT no_utilisateur,pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS;";
	private static final String SELECT_ID = "SELECT no_utilisateur,pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS u WHERE u.no_utilisateur=?;";
	private static final String SELECT_EMAIL = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE email=?;";
	private static final String SELECT_PSEUDO = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE pseudo=?;";
	private static final String DELETE_UTILISATEUR = "DELETE FROM UTILISATEUR WHERE no_utilisateur=?;";
	private static final String UPDATE = "UPDATE UTILISATEURS SET pseudo = ?,nom = ?,prenom = ?,email = ?,telephone = ?,rue = ?,code_postal = ?,ville = ?,mot_de_passe = ?,credit = ?,administrateur = ?WHERE no_utilisateur=?;";

	@Override
	public void insert(Utilisateur utilisateur) throws BusinessException {
		Connection cnx = null;
		BusinessException be = new BusinessException();
		try {
			cnx = ConnectionProvider.getConnection();
			// Pour prendre la main sur la transaction
			PreparedStatement psmt = cnx.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS);
			psmt.setString(1, utilisateur.getPseudo());
			psmt.setString(2, utilisateur.getNom());
			psmt.setString(3, utilisateur.getPrenom());
			psmt.setString(4, utilisateur.getEmail());
			psmt.setString(5, utilisateur.getTelephone());
			psmt.setString(6, utilisateur.getRue());
			psmt.setString(7, utilisateur.getCode_postal());
			psmt.setString(8, utilisateur.getVille());
			psmt.setString(9, utilisateur.getMot_de_passe());
			psmt.setInt(10, utilisateur.getCredit());
			psmt.setBoolean(11, utilisateur.isAdministrateur());
			int nombreEnregistrementInsere = psmt.executeUpdate();
			if (nombreEnregistrementInsere == 1) {
				ResultSet rs = psmt.getGeneratedKeys();
				if (rs.next()) {
					utilisateur.setNoUtilisateur(rs.getInt(1));
				}
				rs.close();

			} else {
				be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			}
			psmt.close();
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
	public List<Utilisateur> select() throws BusinessException {
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		try (Connection cnx = ConnectionProvider.getConnection(); Statement smt = cnx.createStatement();) {
			ResultSet rs = smt.executeQuery(SELECT_ALL);
			Utilisateur utilisateurCourant = new Utilisateur();
			while (rs.next()) {
				if (rs.getInt("no_utilisateur") != utilisateurCourant.getNoUtilisateur()) {
					utilisateurCourant = mappingUtilisateur(rs);
					listeUtilisateurs.add(utilisateurCourant);
				}
			}
			return listeUtilisateurs;
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_ALL_ECHEC);
			throw be;
		}
	}

	@Override
	public Utilisateur selectById(int id) throws BusinessException {
		Utilisateur utilisateurCourant = new Utilisateur();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement smt = cnx.prepareStatement(SELECT_ID);) {
			smt.setInt(1, id);
			ResultSet rs = smt.executeQuery();

			while (rs.next()) {
				if (rs.getInt("no_utilisateur") != utilisateurCourant.getNoUtilisateur()) {
					utilisateurCourant = mappingUtilisateur(rs);
				}
			}

			return utilisateurCourant;
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_ID_ECHEC);
			throw be;
		}
	}
	
	@Override
	public Utilisateur selectByEmail(String email) throws BusinessException {
		Utilisateur utilisateurCourant = null;
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement smt = cnx.prepareStatement(SELECT_EMAIL);) {
			smt.setString(1, email);
			ResultSet rs = smt.executeQuery();

			while (rs.next()) {
					utilisateurCourant = new Utilisateur();
					utilisateurCourant = mappingUtilisateur(rs);
			}

			return utilisateurCourant;
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_EMAIL_ECHEC);
			throw be;
		}
	}

	public Utilisateur selectByPseudo(String pseudo) throws BusinessException {
		Utilisateur utilisateurCourant = null;
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement smt = cnx.prepareStatement(SELECT_PSEUDO);) {
			smt.setString(1, pseudo);
			ResultSet rs = smt.executeQuery();

			while (rs.next()) {
					utilisateurCourant = new Utilisateur();
					utilisateurCourant = mappingUtilisateur(rs);
			}

			return utilisateurCourant;
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_PSEUDO_ECHEC);
			throw be;
		}
	}
	
	@Override
	public void update(Utilisateur utilisateur) throws BusinessException {
		BusinessException be = new BusinessException();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(UPDATE);) {
			psmt.setString(1, utilisateur.getPseudo());
			psmt.setString(2, utilisateur.getNom());
			psmt.setString(3, utilisateur.getPrenom());
			psmt.setString(4, utilisateur.getEmail());
			psmt.setString(5, utilisateur.getTelephone());
			psmt.setString(6, utilisateur.getRue());
			psmt.setString(7, utilisateur.getCode_postal());
			psmt.setString(8, utilisateur.getVille());
			psmt.setString(9, utilisateur.getMot_de_passe());
			psmt.setInt(10, utilisateur.getCredit());
			psmt.setBoolean(11, utilisateur.isAdministrateur());
			psmt.setInt(12, utilisateur.getNoUtilisateur());
			int nbEnr = psmt.executeUpdate();
			if (nbEnr != 1) {
				be.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
			}

		} catch (Exception e) {
			e.printStackTrace();
			be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);

		}
	}

	@Override
	public void delete(int id) throws BusinessException {
		BusinessException be = new BusinessException();
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement smt = cnx.prepareStatement(DELETE_UTILISATEUR);) {
			smt.setInt(1, id);
			int nbEnr = smt.executeUpdate();
			if (nbEnr != 1) {
				be.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
			}

		} catch (SQLException e) {
			be.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
		}
	}

	private Utilisateur mappingUtilisateur(ResultSet rs) throws SQLException {
		Utilisateur newUtilisateur = new Utilisateur();
		newUtilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
		newUtilisateur.setPseudo(rs.getString("pseudo"));
		newUtilisateur.setNom(rs.getString("nom"));
		newUtilisateur.setPrenom(rs.getString("prenom"));
		newUtilisateur.setEmail(rs.getString("email"));
		newUtilisateur.setTelephone(rs.getString("telephone"));
		newUtilisateur.setRue(rs.getString("rue"));
		newUtilisateur.setCode_postal(rs.getString("code_postal"));
		newUtilisateur.setVille(rs.getString("ville"));
		newUtilisateur.setMot_de_passe(rs.getString("mot_de_passe"));
		newUtilisateur.setCredit(rs.getInt("credit"));
		newUtilisateur.setAdministrateur(rs.getBoolean("administrateur"));
		return newUtilisateur;
	}

}
