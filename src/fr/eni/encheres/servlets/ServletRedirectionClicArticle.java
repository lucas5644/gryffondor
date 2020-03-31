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
		
		if(userConnected.getNoUtilisateur() == monArticleNo) {
			RequestDispatcher rd = request.getRequestDispatcher("UpdateVente");
			rd.forward(request, response);
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("A REMPLIR PAR LUCAS");
			rd.forward(request, response);
		}
		 
		
		
		
		
	
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/accueil.jsp");
		rd.forward(request, response);
		
		
		
		
		
		
	
		
		
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
