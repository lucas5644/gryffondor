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
		numeroArticle = Integer.parseInt(request.getParameter("numeroArticle"));
		System.out.println("Numéro de l'article" + numeroArticle);
		try {
			articleCourant = enchereManager.selectArticleById(numeroArticle);
			System.out.println(articleCourant);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
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

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/miserEnchere.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Integer> listeCodesErreur = new ArrayList<>();
		// récupérer l'article
		System.out.println(articleCourant);
		System.out.println(enchereCourante);
		// récupérer l'utilisateur s'il est connecté
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("userConnected");
		if (user == null) {
			listeCodesErreur.add(CodesResultatServlets.UTILISATEUR_DECONNECTE);
		} 
		
		// Réalisation du traitement
		if (listeCodesErreur.size() > 0) {
			// L'utilisateur n'est pas connecté, retour à la page d'accueil
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			rd.forward(request, response);
		} else {
			// récupérer le montant de l'enchère
			int montantEnchere = Integer.parseInt(request.getParameter("proposition"));
			if (user.getPseudo() == articleCourant.getVendeur().getPseudo()) {
				listeCodesErreur.add(CodesResultatServlets.ERREUR_UTILISATEUR);
			}
			if (listeCodesErreur.size() > 0) {
				// si erreurs, affichage sur la page des enchères
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/miserEnchere.jsp");
				rd.forward(request, response);
			}
			try {
				EncheresManagerTest encheresManagerTest = new EncheresManagerTest();
				//je check s'il y a déjà une enchère
				if (encheresManagerTest.checkEnchere(articleCourant.getNoArticle(),2)) {
					//l'utilisateur a déjà enchéri, on update l'enchère
					enchereManager.updateEnchere(user.getPseudo(), numeroArticle, montantEnchere);
				}
				else if (enchereCourante.getMontantEnchere() == 0) {
					//Pas encore d'enchère, j'insère la première offre
					enchereManager.insertEnchere(user.getPseudo(), numeroArticle, montantEnchere);
				}else {
					//Une enchère existe déjà, je mets à jour les données
					enchereManager.updateEnchere(user.getPseudo(), numeroArticle, montantEnchere);
				}
				
				
				if (enchereCourante.getMontantEnchere() == 0) {
					//Pas encore d'enchère, j'insère la première offre
					enchereManager.insertEnchere(user.getPseudo(), numeroArticle, montantEnchere);
				} else {
					//Une enchère existe déjà, je mets à jour les données
					enchereManager.updateEnchere(user.getPseudo(), numeroArticle, montantEnchere);
				}

			} catch (BusinessException e) {
				e.printStackTrace();
			}
			// si user connecté, renvoyer vers page d'accueil connecté
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueilConnecte.jsp");
			rd.forward(request, response);
		}
	}
}
