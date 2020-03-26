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
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class connexion
 */
@WebServlet("/tryConnexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tryPseudo = request.getParameter("pseudo");
		String tryMdp = request.getParameter("mdp");
		EncheresManager encheresManager = new EncheresManager();
		Utilisateur tryUser = new Utilisateur();
		
		try {
			tryUser = encheresManager.checkConnexion(tryPseudo, tryMdp);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if(tryUser.getNom().equals("non")) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			request.setAttribute("connexionRefused", "La connexion est Refusée");
			rd.forward(request, response);
		}else {
			HttpSession sessionUtilisateur = request.getSession();
			sessionUtilisateur.setAttribute("userConnected", tryUser);
			RequestDispatcher rd = request.getRequestDispatcher("/Accueil/Utilisateur");
			rd.forward(request, response);			
		}
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
