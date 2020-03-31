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

}