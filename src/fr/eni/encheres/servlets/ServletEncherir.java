package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bll.EncheresManagerTest;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletEncherir
 */
@WebServlet("/encherirArticle")
public class ServletEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Article articleCourant = null;
	private int numeroArticle;
	private Enchere enchereCourante = null;
	private String enchereVide;
	private EncheresManager enchereManager = new EncheresManager();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Je récupère le numéro de l'article
		numeroArticle = Integer.parseInt(request.getParameter("numeroArticle"));
		System.out.println("Numéro de l'article" + numeroArticle);
		// Je récupère l'article
		try {
			articleCourant = enchereManager.selectArticleById(numeroArticle);
			System.out.println(articleCourant);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		session.setAttribute("articleCourant", articleCourant);
		
		request.setAttribute("nomArticle", articleCourant.getNomArticle());
		request.setAttribute("description", articleCourant.getDescription());
		request.setAttribute("categorie", articleCourant.getCategorieArticle().getLibelle());
		try {
			enchereCourante = enchereManager.selectEnchere(numeroArticle);
			System.out.println(enchereCourante);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		if (enchereCourante.getMontantEnchere() == 0) {
			enchereVide = "Aucune enchère en cours";
			request.setAttribute("meilleureOffre", enchereVide);
		} else {
			request.setAttribute("meilleureOffre", enchereCourante.getMontantEnchere());
		}
		request.setAttribute("prixDepart", articleCourant.getMiseAPrix());
		request.setAttribute("dateFin", articleCourant.getDateFinEncheres());
		request.setAttribute("lieuRetrait", articleCourant.getLieuRetrait());
		request.setAttribute("vendeur", articleCourant.getVendeur().getPseudo());

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/afficherArticle.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Integer> listeCodesErreur = new ArrayList<>();
		// Je récupère l'article s'il n'existe pas
		if (articleCourant == null) {
			String noArticle = request.getParameter("noArticle");
			int numeroArticle = Integer.parseInt(noArticle);
			try {
				articleCourant = enchereManager.selectArticleById(numeroArticle);
				enchereCourante = enchereManager.selectEnchere(numeroArticle);
				enchereCourante.setArticle(articleCourant);
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}

		HttpSession session = request.getSession();
		session.setAttribute("articleCourant", articleCourant);

		System.out.println(articleCourant);
		System.out.println(enchereCourante);
		// récupérer l'utilisateur s'il est connecté

		Utilisateur user = (Utilisateur) session.getAttribute("userConnected");
		if (user == null) {
			listeCodesErreur.add(CodesResultatServlets.UTILISATEUR_DECONNECTE);
		}
		// récupérer le montant de l'enchère
		String enchere = request.getParameter("proposition");
		int montantEnchere = 0;
		if (enchere == null || enchere.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.ERREUR_PROPOSITION);
		} else {
			montantEnchere = Integer.parseInt(enchere);
		}

		// erreur si l'utilisateur pose une enchère sur son propre article
		if (user.getPseudo() == articleCourant.getVendeur().getPseudo()) {
			listeCodesErreur.add(CodesResultatServlets.ERREUR_UTILISATEUR);
		}

		// Réalisation du traitement
		if (listeCodesErreur.size() > 0) {
			// L'utilisateur n'est pas connecté, retour à la page d'accueil
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/miserEnchere.jsp");
			rd.forward(request, response);
		} else {
			try {
				boolean enchereEffectuee = false;
				// je vérifie s'il existe une enchère
				if (enchereCourante.getMontantEnchere() == 0) {
					// il n'y a pas d'enchère, je check la validité de la proposition
					BusinessException be = new BusinessException();
					enchereManager.checkValiditeEnchere(enchereCourante.getMontantEnchere(), montantEnchere,
							articleCourant.getDateFinEncheres(), articleCourant.getMiseAPrix(), user.getCredit(), be);
					if (!be.hasErreurs()) {
						Enchere meilleureEnchere = new Enchere();
						//Recherche du top 1 et update du crédit de l'ancien enchérisseur
						meilleureEnchere = enchereManager.selectMeilleureEnchere(articleCourant.getNoArticle());
						//update du crédit de l'ancien enchérisseur
						enchereManager.updateCreditAncienEncherisseur(meilleureEnchere.getUtilisateur().getPseudo(), meilleureEnchere.getMontantEnchere());
						// j'insère la première offre
						enchereManager.insertEnchere(user.getPseudo(), articleCourant.getNoArticle(), montantEnchere);
						enchereManager.updatePrixVente(articleCourant.getNoArticle(), montantEnchere);
						enchereManager.updateCreditNouveauEncherisseur(user.getPseudo(), montantEnchere);
					} else {
						throw be;
					}
				} else {
					// je check si l'utilisateur a déjà enchéri sur cette article
					if (enchereManager.checkEnchere(articleCourant.getNoArticle(), user.getNoUtilisateur()) == true) {
						// il a déjà enchéri, je check la validité de la proposition
						BusinessException be = new BusinessException();
						enchereManager.checkValiditeEnchere(enchereCourante.getMontantEnchere(), montantEnchere,
								articleCourant.getDateFinEncheres(), articleCourant.getMiseAPrix(), user.getCredit(), be);
					
						if (!be.hasErreurs()) {
							Enchere meilleureEnchere = new Enchere();
							//Recherche du top 1 et update du crédit de l'ancien enchérisseur
							meilleureEnchere = enchereManager.selectMeilleureEnchere(articleCourant.getNoArticle());
							//update du crédit de l'ancien enchérisseur
							enchereManager.updateCreditAncienEncherisseur(meilleureEnchere.getUtilisateur().getPseudo(), meilleureEnchere.getMontantEnchere());
							//Recherche du top 1 et update du crédit de l'ancien enchérisseur
							// on update l'enchère
							enchereManager.updateEnchere(user.getPseudo(), articleCourant.getNoArticle(),
									montantEnchere);
							enchereManager.updatePrixVente(articleCourant.getNoArticle(), montantEnchere);
							enchereManager.updateCreditNouveauEncherisseur(user.getPseudo(), montantEnchere);
						} else {
							throw be;
						}
					} else {
						// il n'a pas enchéri, je check la validité de sa proposition
						BusinessException be = new BusinessException();
						enchereManager.checkValiditeEnchere(enchereCourante.getMontantEnchere(), montantEnchere,
								articleCourant.getDateFinEncheres(), articleCourant.getMiseAPrix(), user.getCredit(), be);
						if (!be.hasErreurs()) {
							Enchere meilleureEnchere = new Enchere();
							//Recherche du top 1 et update du crédit de l'ancien enchérisseur
							meilleureEnchere = enchereManager.selectMeilleureEnchere(articleCourant.getNoArticle());
							//update du crédit de l'ancien enchérisseur
							enchereManager.updateCreditAncienEncherisseur(meilleureEnchere.getUtilisateur().getPseudo(), meilleureEnchere.getMontantEnchere());
							//Recherche du top 1 et update du crédit de l'ancien enchérisseur
							// j'insère son enchère
							enchereManager.insertEnchere(user.getPseudo(), articleCourant.getNoArticle(),
									montantEnchere);
							enchereManager.updatePrixVente(articleCourant.getNoArticle(), montantEnchere);
							enchereManager.updateCreditNouveauEncherisseur(user.getPseudo(), montantEnchere);
						} else {
							throw be;
						}
					}
				}
				// je renvoie vers la page d'accueil
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueilConnecte.jsp");
				rd.forward(request, response);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				if (enchereCourante.getMontantEnchere() == 0) {
					enchereVide = "Aucune enchère en cours";
					request.setAttribute("meilleureOffre", enchereVide);
				} else {
					request.setAttribute("meilleureOffre", enchereCourante.getMontantEnchere());
				}
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/miserEnchere.jsp");
				rd.forward(request, response);
			}
		}
	}
}
