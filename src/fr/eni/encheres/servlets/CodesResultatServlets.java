package fr.eni.encheres.servlets;

/**
 * Les codes disponibles sont entre 30000 et 39999
 */
public abstract class CodesResultatServlets {
	
	/**
	 * Format article incorrect
	 */
	public static final int FORMAT_ARTICLE_ERREUR=30000;
	/**
	 * Format date incorrect
	 */
	public static final int FORMAT_DATE_ERREUR=30001;
	/**
	 * Format description incorrect
	 */
	public static final int FORMAT_DESCRIPTION_ERREUR=30002;
	/**
	 * Format prix incorrect
	 */
	public static final int FORMAT_PRIX_ERREUR=30003;
	/**
	 * Format rue incorrect
	 */
	public static final int FORMAT_RUE_ERREUR=30004;
	/**
	 * Format code postal incorrect
	 */
	public static final int FORMAT_CODE_POSTAL_ERREUR=30005;
	/**
	 * Format ville incorrect
	 */
	public static final int FORMAT_VILLE_ERREUR=30006;
	/**
	 * Utilisateur déconnecté
	 */
	public static final int UTILISATEUR_DECONNECTE=30007;
	/**
	 * Vendeur et enchéreur similaire
	 */
	public static final int ERREUR_UTILISATEUR = 30008;
	
	public static final int ERREUR_UPLOAD = 30009;
	/**
	 * Proposition vide
	 */
	public static final int ERREUR_PROPOSITION = 30010;
	
	
	public static final int ERREUR_MOT_DE_PASSE = 30011;
	
}