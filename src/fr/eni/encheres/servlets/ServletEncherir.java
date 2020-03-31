package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.media.jfxmedia.events.NewFrameEvent;

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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		numeroArticle = Integer.parseInt(request.getParameter("numeroArticle"));
		System.out.println("Numéro de l'article" + numeroArticle);
		try {
			articleCourant = enchereManager.selectArticleById(numeroArticle);
			System.out.println(articleCourant);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		request.setAttribute("nomArticle", articleCourant.getNomArticle());
		request.setAttribute("description",articleCourant.getDescription());
		request.setAttribute("categorie", articleCourant.getCategorieArticle().getLibelle());
		EncheresManagerTest enchereManagerTest = new EncheresManagerTest();
		try {
			enchereCourante = enchereManagerTest.selectEnchere(numeroArticle);
			System.out.println(enchereCourante);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		if (enchereCourante.getMontantEnchere() == 0) {
			enchereVide = "Aucune enchère en cours";
			request.setAttribute("meilleureOffre", enchereVide);
		}else {
			request.setAttribute("meilleureOffre", enchereCourante.getMontantEnchere());
		}
		request.setAttribute("prixDepart", articleCourant.getMiseAPrix());
		request.setAttribute("finEnchere", articleCourant.getDateFinEncheres());
		request.setAttribute("lieuRetrait", articleCourant.getLieuRetrait());
		request.setAttribute("vendeur", articleCourant.getVendeur().getPseudo());
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/miserEnchere.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//récupérer l'article
		System.out.println(articleCourant);	
		System.out.println(enchereCourante);
		//récupérer l'utilisateur s'il est connecté
		HttpSession session = request.getSession();
		Utilisateur user = new Utilisateur();
		user = (Utilisateur) session.getAttribute("userConnected");
		//récupérer le montant de l'enchère
		int montantEnchere = Integer.parseInt(request.getParameter("proposition"));
		//modifier prix de l'enchère
		try {
			if (enchereCourante.getMontantEnchere() == 0) {
				enchereManager.insertEnchere("Corentin35", numeroArticle, montantEnchere);
			}else {
				enchereManager.updateEnchere("Corentin35", numeroArticle, montantEnchere);
			}
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		//si user déconnecté, afficher message d'erreur
		
		//si user connecté, renvoyer vers page d'accueil connecté
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueilConnecte.jsp");
		rd.forward(request, response);
	}
}
