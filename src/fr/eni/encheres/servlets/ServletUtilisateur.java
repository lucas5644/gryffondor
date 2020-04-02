package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import fr.eni.encheres.bll.EncheresManager;
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
		
		pseudo =request.getParameter("pseudo");
		EncheresManager em = new EncheresManager(); 
		
		try {
			Utilisateur user ;
			user=em.rechercheUtilisateur(pseudo);
			System.out.println(user);			
			request.setAttribute("utilisateur", user);
			
		} catch (BusinessException e) {
			e.printStackTrace();
			
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/afficherUtilisateur.jsp");
				rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
