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

import fr.eni.encheres.bll.EncheresManager;
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
	HttpSession session = null;
	Article articleCourant = null;
	Utilisateur user = null;
	EncheresManager enchereManager = new EncheresManager();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int numeroArticle = Integer.parseInt(request.getParameter("numeroArticle"));
		System.out.println("Numéro de l'article" + numeroArticle);
		try {
			articleCourant = enchereManager.selectArticleById(numeroArticle);
			System.out.println(articleCourant);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		request.setAttribute("nomArticle", articleCourant.getNomArticle());
		request.setAttribute("description",articleCourant.getDescription());
		request.setAttribute("categorie", articleCourant.getCategorieArticle().getLibelle());
//		request.setAttribute("meilleureOffre", );
		request.setAttribute("prixDepart", articleCourant.getMiseAPrix());
		request.setAttribute("finEnchere", articleCourant.getDateFinEncheres());
		request.setAttribute("lieuRetrait", articleCourant.getLieuRetrait());
		request.setAttribute("vendeur", articleCourant.getVendeur().getPseudo());
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/miserEnchere.jsp");
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
