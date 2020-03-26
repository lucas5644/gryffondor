package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

public interface EnchereDAO {
	//Ajouter un article
	void insertArticle(Article data) throws BusinessException;
	//Afficher les articles sur l'écran déconnecté
	List<Article> selectArticleDeconnecte(String nomCategorie, String nomArticle) throws BusinessException;
	//Ajouter un utilisateur
	Utilisateur insert(Utilisateur user) throws BusinessException;
	//Vérifier le mail et le pseudo
	boolean checkPseudoMail(String pseudo, String mail) throws BusinessException;
	//Vérifier
	public Utilisateur checkConnexion(String tryPseudo, String tryMdp) throws BusinessException;
}