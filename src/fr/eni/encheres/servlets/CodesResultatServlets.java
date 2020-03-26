package fr.eni.encheres.servlets;

/**
 * Les codes disponibles sont entre 30000 et 39999
 */
public abstract class CodesResultatServlets {
	
	/**
	 * Format UTILISATEUR.pseudo incorrect
	 */
	public static final int FORMAT_UTILISATEUR_PSEUDO_ERREUR=30000;
	/**
	 * Format UTILISATEUR.nom incorrect
	 */
	public static final int FORMAT_UTILISATEUR_NOM_ERREUR=30001;
	/**
	 * Format UTILISATEUR.prenom incorrect
	 */
	public static final int FORMAT_UTILISATEUR_PRENOM_ERREUR=30002;
	/**
	 * Format UTILISATEUR.email incorrect
	 */
	public static final int FORMAT_UTILISATEUR_EMAIL_ERREUR=30003;
	/**
	 * Format UTILISATEUR.telephone incorrect
	 */
	public static final int FORMAT_UTILISATEUR_TELEPHONE_ERREUR=30004;
	/**
	 * Format UTILISATEUR.rue incorrect
	 */
	public static final int FORMAT_UTILISATEUR_RUE_ERREUR=30005;
	/**
	 * Format UTILISATEUR.code_postal incorrect
	 */
	public static final int FORMAT_UTILISATEUR_CP_ERREUR=30006;
	/**
	 * Format UTILISATEUR.ville incorrect
	 */
	public static final int FORMAT_UTILISATEUR_VILLE_ERREUR=30007;
	/**
	 * Format UTILISATEUR.mot_de_passe incorrect
	 */
	public static final int FORMAT_UTILISATEUR_MDP_ERREUR=30008;
	/**
	 * Format UTILISATEUR.credit incorrect
	 */
	public static final int FORMAT_UTILISATEUR_CREDIT_ERREUR=30009;
	/**
	 * Format UTILISATEUR.administrateur incorrect
	 */
	public static final int FORMAT_UTILISATEUR_ADMINISTRATEUR_ERREUR=30010;
	/**
	 * Format UTILISATEUR.email est deja present en base avant l'enregistrement
	 */
	public static final int EMAIL_EXISTE_DEJA = 30011;
	/**
	 * Format UTILISATEUR.email n'est pas present en base
	 */
	public static final int EMAIL_NEXISTE_PAS = 30012;
	
	public static final int MDP_DIFFERENT = 30013;
	
	public static final int PSEUDO_EXISTE_DEJA = 30014;
	
	
}