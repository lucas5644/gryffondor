package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

public interface EnchereDAO {
	// Ajouter un article
	int insertArticle(Article data, Retrait lieuRetrait) throws BusinessException;

	// Afficher les articles sur l'écran déconnecté
	List<Article> selectArticleDeconnecte(String nomCategorie, String nomArticle) throws BusinessException;

	// Ajouter un utilisateur
	Utilisateur insert(Utilisateur user) throws BusinessException;

	// Vérifier le mail et le pseudo
	boolean checkPseudoMail(String pseudo, String mail) throws BusinessException;

	// Vérifier
	public Utilisateur checkConnexion(String tryPseudo, String tryMdp) throws BusinessException;

	// teste
	public Utilisateur selectUtilisateur(String pseudo) throws BusinessException;

	// Suprimer l'utilsateur
	boolean delete(int noUtilisateur) throws BusinessException;

	public Utilisateur updateUtilisateur(Utilisateur utilisateur) throws BusinessException;

	public List<Article> selectAllArticles(String nomCategorie, String nomArticle) throws BusinessException;

	// Supprimer un article
	public boolean delete_article(int noArticle) throws BusinessException;

	// Select article by id
	public Article selectArticleById(int numeroArticle) throws BusinessException;

	public Article updateArticle(Article art, Retrait retrait) throws BusinessException;

	public void updateEtatVente() throws BusinessException;

	// Première enchère
	public Enchere insertEnchere(String pseudoUser, int numeroArticle, int montantEnchere) throws BusinessException;

	// Mise à jour enchère
	public Enchere updateEnchere(String pseudoUser, int numeroArticle, int montantEnchere) throws BusinessException;

	// Select enchere
	public Enchere selectEnchere(int numeroArticle) throws BusinessException;

	// check enchere
	public boolean checkEnchere(int numeroArticle, int numeroUtilisateur) throws BusinessException;

	public List<Utilisateur> selectUtilisateurPourAdmin() throws BusinessException;

	public List<Enchere> selectEncheresUtilisateur(int noUtilisateur) throws BusinessException;

	public Utilisateur selectUtilisateurById(int id) throws BusinessException;
	
	//Modifier le prix de vente d'un article
	public void updatePrixVente(int numeroArticle, int montantEnchere) throws BusinessException;

	public Utilisateur updateMDP(Utilisateur user)throws BusinessException;
	
	//mise à jour du crédit pendant l'enchère
	public void updateCreditEnchere(String pseudo, int montantEnchere) throws BusinessException;
}
