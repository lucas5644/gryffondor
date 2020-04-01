package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletRedirectionClicArticle
 */
@WebServlet("/RedirectionClicArticle")
public class ServletRedirectionClicArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int numeroArticle;
	private Article articleCourant = null;
	private EncheresManager enchereManager = new EncheresManager(); 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		numeroArticle = Integer.parseInt(request.getParameter("numeroArticle"));
		System.out.println("Numéro de l'article" + numeroArticle);
		HttpSession session = request.getSession();
		Utilisateur userConnected = (Utilisateur) session.getAttribute("userConnected");
		String monArticle = request.getParameter("article");
		int monArticleNo = Integer.parseInt(monArticle);
		try {
			articleCourant = enchereManager.selectArticleById(numeroArticle);
			System.out.println(articleCourant);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		request.setAttribute("nomArticle", articleCourant.getNomArticle());
		request.setAttribute("description", articleCourant.getDescription());
		request.setAttribute("categorie", articleCourant.getCategorieArticle().getLibelle());
		request.setAttribute("prixDepart", articleCourant.getMiseAPrix());
		request.setAttribute("dateDebut", articleCourant.getDateDebutEncheres());
		request.setAttribute("dateFin", articleCourant.getDateFinEncheres());
		
		//si l'utilisateur connecté est différent du vendeur
		// je renvoie sur une page d'affichage d'article sans possibilité de le modifier
		if (userConnected.getNoUtilisateur() != articleCourant.getVendeur().getNoUtilisateur()) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/miserEnchere.jsp");
			rd.forward(request, response);
		}else {
			//si l'utilisateur connecté = vendeur
			//je renvoie sur la JSP "updateVente"
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/updateVente.jsp");
			rd.forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
