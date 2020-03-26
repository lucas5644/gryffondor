package fr.eni.encheres.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletUtilisateur
 */
@WebServlet("/RechercheUtilisateur")
public class ServletUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// paramètres
		String pseudo;
		
		pseudo =request.getParameter("rechercheUtilisateur");
		EnchereManager em = new EnchereManager(); 
		
		try {
			Utilisateur user ;
			user=em.rechercheUtilisateur(pseudo);
			if(user == null) {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/RechercheUtilisateur.jsp");
				rd.forward(request, response);
			}
			
			request.setAttribute("utilisateur", user);
			
		} catch (BusinessException e) {
			e.printStackTrace();
			
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/AfficherUtilisateur.jsp");
				rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
