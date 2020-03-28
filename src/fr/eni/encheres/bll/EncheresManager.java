package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.exception.BusinessException;


public class EncheresManager {
	private EnchereDAO enchereDAO;
	
	public EncheresManager() {
		enchereDAO = DAOFactory.getEnchereDAO();
	}
	
	public Article ajouterArticle(Article article) throws BusinessException {
		BusinessException be = new BusinessException();
		//valider la date
		validerDate(article.getDateDebutEncheres(), article.getDateFinEncheres(), be);
		if (!be.hasErreurs()) {
			
			enchereDAO.insertArticle(article);
		}else {
			throw be;
		}
		return article;
	}
	
	public void validerDate(LocalDate debutEnchere, LocalDate finEnchere, BusinessException be) {
		// la date ne peut pas être nulle et antérieure à la date du jour
		LocalDate now = LocalDate.now();
		if (debutEnchere == null || debutEnchere.isBefore(now) || finEnchere.isBefore(now) || finEnchere.isBefore(debutEnchere)) {
			be.ajouterErreur(CodesResultatBLL.DATE_ERREUR);
		}
	}
	
	public List<Article> selectArticleDeconnecte(String nomCategorie, String nomArticle) throws BusinessException {
		return enchereDAO.selectArticleDeconnecte(nomCategorie, nomArticle);
	}
	
	public Utilisateur ajouterUser(Utilisateur user)throws BusinessException{
		BusinessException be = new BusinessException();
		//valider les champs
		checkUser(user, be);
		if(!be.hasErreurs()) {
			user.setAdministrateur(0);
			if(enchereDAO.checkPseudoMail(user.getPseudo(), user.getEmail())){
				user = enchereDAO.insert(user);
				return user;
			}
			return user;
			
		}else {
			throw be;
		}
	}
	
	public void checkUser(Utilisateur user, BusinessException be) {
		if(user.getCodePostal().trim().length() != 5) {
			be.ajouterErreur(10000);
			System.out.println("erreur cp");
		}
//		if(user.getPseudo().matches("[A-Za-z0-9]+")) {
//			be.ajouterErreur(10002);
//			System.out.println("erreur pseudo");
//		}
		if(user.getPseudo().trim().length() > 31 && user.getPseudo().trim().length()< 3) {
			be.ajouterErreur(10000);
			System.out.println("erreur pseudo long");
		}
		if(user.getPrenom().trim().length()> 31 && user.getPrenom().trim().length()< 2) {
			be.ajouterErreur(10000);
			System.out.println("erreur prenom");
		}
		if(user.getNom().trim().length()> 31 && user.getNom().trim().length()<2) {
				be.ajouterErreur(10000);
				System.out.println("erreur nom");
		}
		if(user.getEmail().trim().length() > 51 && user.getEmail().trim().length() < 5 && user.getEmail().contains("@")) {
			be.ajouterErreur(10000);
			System.out.println("erreur mail");
		}
		if(user.getVille().trim().length() >31 && user.getVille().trim().length() < 3) {
			be.ajouterErreur(10000);
			System.out.println("erreur ville");
		}
		if(user.getMotDePasse().trim().length() > 31 && user.getMotDePasse().trim().length() <= 5) {
			be.ajouterErreur(10000);
			System.out.println("erreur mdp");
		}
		if(user.getCredit() > 1000000) {
			be.ajouterErreur(10000);
		}
	}
	
	public Utilisateur checkConnexion(String tryPseudo, String tryMdp) throws BusinessException{
		
		Utilisateur tryUser = new Utilisateur();
			tryUser = enchereDAO.checkConnexion(tryPseudo, tryMdp);
		return tryUser;
		
	}

	public Utilisateur rechercheUtilisateur(String pseudo)throws BusinessException{
		Utilisateur user ; 
		user=enchereDAO.selectUtilisateur(pseudo);
		
		return user;
	
	}
	public boolean removeUtilisateur (int noUtilisateur) throws BusinessException{
		boolean verification; 
		verification = enchereDAO.delete(noUtilisateur);
	return verification;
	}

public Utilisateur updateUtilisateur (Utilisateur utilisateur)throws BusinessException{
	Utilisateur user ;
	user = enchereDAO.updateUtilisateur(utilisateur);
	return user;
	
}
}



