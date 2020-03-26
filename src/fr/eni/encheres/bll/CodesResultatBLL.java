package fr.eni.encheres.bll;

/**
 * Les codes disponibles sont entre 20000 et 29999
 */
public abstract class CodesResultatBLL {
	
	public static final int PSEUDO_EST_NULL = 20000;
	public static final int PSEUDO_TROP_COURT = 20001;
	
	public static final int NOM_TROP_COURT = 20002;

	public static final int PRENOM_TROP_COURT = 20003;

	public static final int EMAIL_NON_VALIDE = 20004;
	public static final int EMAIL_NON_SAISIE = 20005;

	public static final int TELEPHONE_TROP_COURT = 20006;
	public static final int TELEPHONE_NON_VALIDE = 20007;
	
	public static final int RUE_TROP_COURT = 20008;
	
	public static final int CP_NON_VALIDE = 20009;
	public static final int CP_NON_SAISIE = 20010;
	
	public static final int VILLE_TROP_COURT = 20011;

	public static final int MDP_CONFIRMATION_DIFFERENT = 20012;
	public static final int MDP_TROP_COURT = 20013;
	public static final int MDP_NON_RENSEIGNER = 20014;
	
}