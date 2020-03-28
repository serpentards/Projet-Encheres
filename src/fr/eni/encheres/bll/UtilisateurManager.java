package fr.eni.encheres.bll;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BusinessException;

public class UtilisateurManager {
	private UtilisateurDAO utilisateurDAO;
	
	public UtilisateurManager() {
		utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}
	
	public Utilisateur ajouterUtilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String code_postal, String ville, String mot_de_passe, String confirmation, int credit, boolean administrateur)throws BusinessException{
		BusinessException be = new BusinessException();
		//valider les champs
		validerPseudo(pseudo, be);
		validerNom(nom, be);
		validerPrenom(prenom, be);
		validerEmail(email, be);
		validerTel(telephone, be);
		validerRue(rue, be);
		validerCp(code_postal, be);
		validerVille(ville, be);
		validerMdp(mot_de_passe, confirmation, be);
		Utilisateur utilisateur = null;
		if(!be.hasErreurs()) {
			utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur, null, null, null);
			utilisateurDAO.insert(utilisateur);
		}else {
			throw be;
		}
		return utilisateur;
	}
	
	public void updateUtilisateur (Utilisateur u, String conf, String email)throws BusinessException {
		BusinessException be = new BusinessException();
		
		//valider les champs
		validerNom(u.getNom(), be);
		validerPrenom(u.getPrenom(), be);
		if (!u.getEmail().equalsIgnoreCase(email)) {
			validerEmail(u.getEmail(), be);
		}
		validerTel(u.getTelephone(), be);
		validerRue(u.getRue(), be);
		validerCp(u.getCode_postal(), be);
		validerVille(u.getVille(), be);
		validerMdp(u.getMot_de_passe(), conf, be);
		
		if(!be.hasErreurs()) {
			utilisateurDAO.update(u);
		}else {
			throw be;
		}
	}
	
	public void deleteUtilisateur(int id) throws BusinessException{
		BusinessException be = new BusinessException();
		try {
			utilisateurDAO.delete(id);
		} catch (Exception e) {
			be.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
			throw be;
		}
	}
//	public List<Utilisateur> selectionnerTousLesUtilisateurs() throws BusinessException{
//		return utilisateurDAO.select();
//	}
	
	public Utilisateur selectionnerUtilisateurAvecId(int id) throws BusinessException{
		return utilisateurDAO.selectById(id);
	}
	
	public Utilisateur selectionnerUtilisateurAvecEmail(String email) throws BusinessException{
		return utilisateurDAO.selectByEmail(email);
	}
	
	public Utilisateur selectionnerUtilisateurAvecPseudo(String pseudo) throws BusinessException {
		return utilisateurDAO.selectByPseudo(pseudo);
	}

	public void validerMdp (String mdp, String conf, BusinessException be) {
		if (mdp== null || mdp.length()>30 || mdp.trim().isEmpty()){
			be.ajouterErreur(CodesResultatBLL.MPD_ERREUR);
		}else if (conf== null || conf.length()>30 || conf.trim().isEmpty()) {
			be.ajouterErreur(CodesResultatBLL.CONFIRMATION_ERREUR);
		}else if (!mdp.equals(conf)) {
			be.ajouterErreur(CodesResultatBLL.MDP_CONFIRMATION_ERREUR);
		}
	}
	
	public void validerPseudo (String s, BusinessException be) {
		String regex = "^[a-zA-Z0-9]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		if (s==null || s.length()>30 || s.trim().isEmpty() ||!matcher.matches()) {
				be.ajouterErreur(CodesResultatBLL.PSEUDO_ERREUR);
		}else {
			try {
				Utilisateur utilisateur = utilisateurDAO.selectByPseudo(s);
				
				if (utilisateur != null) {
					be.ajouterErreur(CodesResultatBLL.PSEUDO_UNIQUE_ERREUR);
				}
			} catch (BusinessException e1) {
				be.ajouterErreur(CodesResultatDAL.SELECT_PSEUDO_ECHEC);
			}
		}
	}
	
	public void validerNom (String s, BusinessException be) {
		if (s==null || s.length()>30 || s.trim().length()==0) {
			be.ajouterErreur(CodesResultatBLL.NOM_ERREUR);
		}
	}
	
	public void validerPrenom (String s, BusinessException be) {
		if (s==null || s.length()>30 || s.trim().isEmpty()) {
			be.ajouterErreur(CodesResultatBLL.PRENOM_ERREUR);
		}
	}
	
	public void validerEmail (String s, BusinessException be) {
		String regex ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		if (s==null || s.length()>30 || s.trim().isEmpty()||!matcher.matches()) {
			be.ajouterErreur(CodesResultatBLL.EMAIL_ERREUR);
		}else {
			try {
				Utilisateur utilisateur = utilisateurDAO.selectByEmail(s);

				if (utilisateur != null) {
					be.ajouterErreur(CodesResultatBLL.EMAIL_UNIQUE_ERREUR);
				}
			} catch (BusinessException e1) {
				be.ajouterErreur(CodesResultatDAL.SELECT_EMAIL_ECHEC);
			}
		}
	}
	
	public void validerTel (String s, BusinessException be) {
		String regex = "^[0-9]{10,15}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		if (s==null || s.length()>30 || s.trim().isEmpty() ||!matcher.matches()) {
			be.ajouterErreur(CodesResultatBLL.TEL_ERREUR);
		}
	}
	
	public void validerRue (String s, BusinessException be) {
		if (s==null || s.length()>30 || s.trim().isEmpty()) {
			be.ajouterErreur(CodesResultatBLL.RUE_ERREUR);
		}
	}
	
	public void validerCp (String s, BusinessException be) {
		String regex = "^[0-9]{5,10}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		if (s==null || s.length()>30 || s.trim().isEmpty() ||!matcher.matches()) {
			be.ajouterErreur(CodesResultatBLL.CP_ERREUR);
		}
	}
	
	public void validerVille (String s, BusinessException be) {
		if (s==null || s.length()>30 || s.trim().isEmpty()) {
			be.ajouterErreur(CodesResultatBLL.VILLE_ERREUR);
		}
	}



}
