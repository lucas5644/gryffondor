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

import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletAffichageArticleDeconnecte
 */
@WebServlet("/AffichageArticleDeconnecte")
public class ServletAffichageArticleDeconnecte extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomCategorie = null;
		String nomArticle = null;
		List<Integer> listeCodesErreur = new ArrayList<>();
		//Lecture nom de la catégorie et nom de l'article
		nomCategorie = request.getParameter("nomCategorie");
		nomArticle = request.getParameter("nomArticle");
		
		if (listeCodesErreur.size()>0) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		}else {
			try {
				//Recherche des articles
				EncheresManager encheresManager = new EncheresManager();
				List<Article> listeArticles = null;
				if (nomArticle != null || nomCategorie != null) {
					listeArticles = encheresManager.selectArticleDeconnecte(nomCategorie, nomArticle);
				}
				request.setAttribute("listeArticle", listeArticles);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			} 
		}
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/accueil.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
