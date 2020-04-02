package fr.eni.encheres.bll;

/**
 * Les codes disponibles sont entre 20000 et 29999
 */
public abstract class CodesResultatBLL {
	
	public static final int DATE_ERREUR = 20000;
	public static final int ENCHERE_ERREUR=20001;
	public static final int ENCHERE_ERREUR_2 = 20002;
	public static final int DATE_ERREUR_2=20003;
	
	/**
	 * Erreur du check user
	 */
	public static final int FORMAT_CODE_POSTAL_ERREUR=20004;
	public static final int FORMAT_VILLE_ERREUR=20005;
	public static final int FORMAT_NOM_ERREUR=20006;
	public static final int FORMAT_PRENOM_ERREUR=20007;	
	public static final int FORMAT_EMAIL_ERREUR=20008;
	public static final int FORMAT_MOT_DE_PASSE_ERREUR=20009;
	public static final int FORMAT_PSEUDO_ERREUR=20010;
	public static final int MONTANT_ERREUR = 20011;


}