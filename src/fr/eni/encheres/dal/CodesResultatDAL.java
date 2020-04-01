package fr.eni.encheres.dal;

/**
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesResultatDAL {

	/**
	 * Echec général quand erreur à l'insertion
	 */
	public static final int INSERT_OBJET_ECHEC = 10000;
	public static final int SELECT_ARTICLE_DECONNECTE=10001;
	public static final int SELECT_ARTICLES =10003;
	public static final int DELETE_OBJET_ECHEC = 10002;
	public static final int UPDATE_ENCHERE = 10004;
	public static final int INSERT_ENCHERE = 10005;
	public static final int SELECT_ENCHERE = 10006;
	public static final int CHECK_ENCHERE = 10007;

}