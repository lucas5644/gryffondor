package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletRedirectionClicArticle
 */
@WebServlet("/RedirectionClicArticle")
public class ServletRedirectionClicArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Utilisateur userConnected = (Utilisateur) session.getAttribute("userConnected");
		String monArticle = request.getParameter("article");
		int monArticleNo = Integer.parseInt(monArticle);
		
		
		String url;
		if(userConnected.getNoUtilisateur() == monArticleNo) {
			url = "WEB-INF/jsp/updateVente.jsp";
			
			
		}else {
			url = "WEB-INF/jsp/accueil.jsp";
			
			
		}
		 
		RequestDispatcher rd = request.getRequestDispatcher(url);
		
		
		
		rd.forward(request, response);
		
		
		
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
