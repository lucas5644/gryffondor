package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.exception.BusinessException;


public class EncheresManager {
	private EnchereDAO enchereDAO;
	
	public EncheresManager() {
		enchereDAO = DAOFactory.getEnchereDAO();
	}
	
	public boolean checkEnchere(int numeroArticle, int numeroUtilisateur) throws BusinessException {
		return enchereDAO.checkEnchere(numeroArticle, numeroUtilisateur);
	}
	
	public void checkValiditeEnchere(int meilleureOffre, int newEnchere, LocalDate finEnchere, int prixDepart, BusinessException be) {
		LocalDate now = LocalDate.now();
		if (meilleureOffre >= newEnchere || newEnchere == 0) {
			be.ajouterErreur(CodesResultatBLL.ENCHERE_ERREUR);
		}
		if (newEnchere <= prixDepart) {
			be.ajouterErreur(CodesResultatBLL.ENCHERE_ERREUR_2);
		}
		if (now.isAfter(finEnchere)) {
			be.ajouterErreur(CodesResultatBLL.DATE_ERREUR_2);
		}
	}	
	
	public Enchere selectEnchere(int numeroArticle) throws BusinessException {
		return enchereDAO.selectEnchere(numeroArticle);
	}
	
	public Enchere updateEnchere(String pseudoUser, int numeroArticle, int montantEnchere) throws BusinessException {
		Enchere newEnchere = new Enchere();
		newEnchere = enchereDAO.updateEnchere(pseudoUser, numeroArticle, montantEnchere);
		return newEnchere;
	}
	
	public Enchere insertEnchere(String pseudoUser, int numeroArticle, int montantEnchere) throws BusinessException {
		Enchere newEnchere = new Enchere();
		newEnchere = enchereDAO.insertEnchere(pseudoUser, numeroArticle, montantEnchere);
		return newEnchere;
	}
	
	public void updateEtatVentes() {
		try {
			enchereDAO.updateEtatVente();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int ajouterArticle(Article article, Retrait lieuRetrait) throws BusinessException {
		BusinessException be = new BusinessException();
		int idArt = -1;
		//valider la date
		validerDate(article.getDateDebutEncheres(), article.getDateFinEncheres(), be);
		if (!be.hasErreurs()) {
			idArt = enchereDAO.insertArticle(article, lieuRetrait);
		}else {
			throw be;
		}
		return idArt;
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

	public Utilisateur ajouterUser(Utilisateur user) throws BusinessException {
		BusinessException be = new BusinessException();
		// valider les champs
		checkUser(user, be);
		if (!be.hasErreurs()) {
			user.setAdministrateur(0);
			if (enchereDAO.checkPseudoMail(user.getPseudo(), user.getEmail())) {
				user = enchereDAO.insert(user);
				return user;
			}
			return user;

		} else {
			throw be;
		}
	}

	public void checkUser(Utilisateur user, BusinessException be) {
		if (user.getCodePostal().trim().length() != 5) {
			be.ajouterErreur(10000);
			System.out.println("erreur cp");
		}
		// if(user.getPseudo().matches("[A-Za-z0-9]+")) {
		// be.ajouterErreur(10002);
		// System.out.println("erreur pseudo");
		// }
		if (user.getPseudo().trim().length() > 31 && user.getPseudo().trim().length() < 3) {
			be.ajouterErreur(10000);
			System.out.println("erreur pseudo long");
		}
		if (user.getPrenom().trim().length() > 31 && user.getPrenom().trim().length() < 2) {
			be.ajouterErreur(10000);
			System.out.println("erreur prenom");
		}
		if (user.getNom().trim().length() > 31 && user.getNom().trim().length() < 2) {
			be.ajouterErreur(10000);
			System.out.println("erreur nom");
		}
		if (user.getEmail().trim().length() > 51 && user.getEmail().trim().length() < 5
				&& user.getEmail().contains("@")) {
			be.ajouterErreur(10000);
			System.out.println("erreur mail");
		}
		if (user.getVille().trim().length() > 31 && user.getVille().trim().length() < 3) {
			be.ajouterErreur(10000);
			System.out.println("erreur ville");
		}
		if (user.getMotDePasse().trim().length() > 31 && user.getMotDePasse().trim().length() <= 5) {
			be.ajouterErreur(10000);
			System.out.println("erreur mdp");
		}
		if (user.getCredit() > 1000000) {
			be.ajouterErreur(10000);
		}
	}

	public Utilisateur checkConnexion(String tryPseudo, String tryMdp) throws BusinessException {

		Utilisateur tryUser = new Utilisateur();
		tryUser = enchereDAO.checkConnexion(tryPseudo, tryMdp);
		return tryUser;

	}

	public Utilisateur rechercheUtilisateur(String pseudo) throws BusinessException {
		Utilisateur user;
		user = enchereDAO.selectUtilisateur(pseudo);

		return user;

	}

	public boolean removeUtilisateur(int noUtilisateur) throws BusinessException {
		boolean verification;
		verification = enchereDAO.delete(noUtilisateur);
		return verification;
	}

	public Utilisateur updateUtilisateur(Utilisateur utilisateur) throws BusinessException {
		Utilisateur user;
		user = enchereDAO.updateUtilisateur(utilisateur);
		return user;

	}

	public List<Article> selectAllArticles(String nomCategorie, String nomArticle) throws BusinessException {
		List<Article> listArt;
		listArt = enchereDAO.selectAllArticles(nomCategorie, nomArticle);
		return listArt;
	}

	public boolean removeArticle(int noArticle) throws BusinessException {
		boolean verification;
		verification = enchereDAO.delete(noArticle);
		return verification;
	}

	public Article selectArticleById(int numeroArticle) throws BusinessException {
		Article articleCourant = new Article();
		articleCourant = enchereDAO.selectArticleById(numeroArticle);
		return articleCourant;
	}

	public Article updateArticle(Article art, Retrait retrait) throws BusinessException {
		Article article;
		article = enchereDAO.updateArticle(art, retrait);
		return article;
	}
	
	public List<Utilisateur>selectUtilisateurPourAdmin()throws BusinessException{
		 List<Utilisateur> listeUtilisateur;
		listeUtilisateur = enchereDAO.selectUtilisateurPourAdmin();
		 return listeUtilisateur;
		
	}

	public List<Article> selectEncheresUtilisateur(int noUtilisateur)throws BusinessException{
		
		List<Enchere> listEncher;
		List<Article> maListe = new ArrayList<Article>();
		listEncher = enchereDAO.selectEncheresUtilisateur(noUtilisateur);
		for (Enchere enchere : listEncher) {
			maListe.add(enchere.getArticle());
		}
		return maListe;
		
	}

}
