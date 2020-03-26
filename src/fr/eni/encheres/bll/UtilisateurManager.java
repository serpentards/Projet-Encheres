package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;
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
		validerTelephone(telephone, be);
		validerRue(rue, be);
		validerCP(code_postal, be);
		validerVille(ville, be);
		validerMDP(mot_de_passe, confirmation, be);
		Utilisateur utilisateur = null;
		if(!be.hasErreurs()) {
			utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur, null, null, null);
			utilisateurDAO.insert(utilisateur);
		}else {
			throw be;
		}
		return utilisateur;
	}
	
	public List<Utilisateur> selectionnerTousLesUtilisateurs() throws BusinessException{
		return utilisateurDAO.select();
	}
	
	public Utilisateur selectionnerUtilisateurAvecId(int id) throws BusinessException{
		return utilisateurDAO.selectById(id);
	}
	
	public Utilisateur selectionnerUtilisateurAvecEmail(String email) throws BusinessException{
		return utilisateurDAO.selectByEmail(email);
	}
	
	public Utilisateur selectionnerUtilisateurAvecPseudo(String pseudo) throws BusinessException {
		return utilisateurDAO.selectByPseudo(pseudo);
	}
	
	private void validerPseudo(String pseudo, BusinessException be) {
		if ( pseudo == null || pseudo.trim().length() < 3 ) {
			be.ajouterErreur(CodesResultatBLL.PSEUDO_TROP_COURT);
		}
		
	}

	private void validerNom(String nom, BusinessException be) {
		if ( nom == null || nom.trim().length() < 3 ) {
			be.ajouterErreur(CodesResultatBLL.NOM_TROP_COURT);
		}
		
	}
	
	private void validerPrenom(String prenom, BusinessException be) {
		if ( prenom == null || prenom.trim().length() < 3 ) {
			be.ajouterErreur(CodesResultatBLL.PRENOM_TROP_COURT);
		}
		
	}
	
	private void validerEmail( String email, BusinessException be ){
		if ( email == null || email.trim().length() < 3 ) {
			be.ajouterErreur(CodesResultatBLL.EMAIL_NON_SAISIE);
		}
    }
	
	private void validerTelephone(String telephone, BusinessException be) {
		if ( telephone == null || telephone.trim().length() != 10 ) {
			be.ajouterErreur(CodesResultatBLL.TELEPHONE_TROP_COURT);
		}else if (!telephone.matches("[0-9]{10}")) {
			be.ajouterErreur(CodesResultatBLL.TELEPHONE_NON_VALIDE);
		}
	}
	
	private void validerRue(String rue, BusinessException be) {
		if ( rue == null || rue.trim().length() < 3 ) {
			be.ajouterErreur(CodesResultatBLL.RUE_TROP_COURT);
		}
	}
	
	private void validerCP(String code_postal, BusinessException be) {
		if ( code_postal == null || code_postal.trim().length() != 5 ) {
			be.ajouterErreur(CodesResultatBLL.CP_NON_SAISIE);
		}else if (!code_postal.matches("[0-9]{5}")) {
			be.ajouterErreur(CodesResultatBLL.CP_NON_VALIDE);
		}
	}
	
	private void validerVille(String ville, BusinessException be) {
		if ( ville == null || ville.trim().length() < 3 ) {
			be.ajouterErreur(CodesResultatBLL.VILLE_TROP_COURT);
		}
	}
	
	private void validerMDP(String mot_de_passe, String confirmation, BusinessException be) {
		if (mot_de_passe != null && mot_de_passe.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
            if (!mot_de_passe.equals(confirmation)) {
            	be.ajouterErreur(CodesResultatBLL.MDP_CONFIRMATION_DIFFERENT);
            } else if (mot_de_passe.trim().length() < 3) {
            	be.ajouterErreur(CodesResultatBLL.MDP_TROP_COURT);
            }
        } else {
        	be.ajouterErreur(CodesResultatBLL.MDP_NON_RENSEIGNER);
        }
		
	}

}
