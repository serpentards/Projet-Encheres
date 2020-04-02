package fr.eni.encheres.dal;

/**
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesResultatDAL {

	/**
	 * Echec général quand erreur à l'insertion
	 */
	public static final int INSERT_OBJET_ECHEC = 10000;
	
	public static final int UPDATE_OBJET_ECHEC  = 10001;
	
	public static final int DELETE_OBJET_ECHEC = 10002;
	
	public static final int SELECT_ID_ECHEC = 10003;
	
	public static final int SELECT_EMAIL_ECHEC = 10004;

	public static final int SELECT_PSEUDO_ECHEC = 10005;

	public static final int SELECT_ALL_CATEGORIE_ECHEC = 10006;

	public static final int SELECT_ALL_ARTICLE_ECHEC = 10007;

	public static final int SELECT_BY_ID_CATEGORIE_ERREUR = 10008;

	public static final int SELECT_ENCHERE_EN_COURS_ECHEC = 10009;
	
	public static final int SELECT_ENCHERE_PARTICIPE_ECHEC = 10010;

	public static final int SELECT_ENCHERE_GAGNE_ECHEC = 10011;

	public static final int SELECT_MES_VENTES_ERREUR = 10012;

	public static final int SELECT_VENTES_NON_COMMENCER_ERREUR = 10013;

	public static final int SELECT_MES_VENTES_EN_COURS_ERREUR = 10014;

	public static final int SELECT_MES_VENTES_TERMINEES_ERREUR = 10015;






}