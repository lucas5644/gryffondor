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

@WebServlet("/RechercheArticleConnecte")
public class ServletRechercheArticleConnecte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		EncheresManager EM = new EncheresManager();
		
		
		switch (request.getParameter("mode")) {
		case "modeAchat":
			
			EM.
			
			
			
			
			
			
			break;
			
		case "modeVente":
			
			
			
			
		
			break;

		default:
			break;
		}
		
		
		
		
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/AccueilUtilisateur");
		rd.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
