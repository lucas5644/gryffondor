package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
		String pseudo = request.getParameter("pseudo");
		String seSouvenirDeMoi = request.getParameter("seSouvenirDeMoi");
		System.out.println("seSouvenirDeMoi :" +seSouvenirDeMoi);
		if(seSouvenirDeMoi != null) {
			Cookie cookie = new Cookie("pseudo", pseudo);
			cookie.setMaxAge(60*60*24);
			response.addCookie(cookie);
		}
		
		
		
		
		
		
		Cookie [] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie1 : cookies) {
				if(cookie1.getName().equals("pseudo")) {
					request.setAttribute("pseudo", cookie1.getValue());
				}
			}
		}
		
		
		String tryPseudo = request.getParameter("pseudo");
		String tryMdp = request.getParameter("mdp");
		EncheresManager encheresManager = new EncheresManager();
		Utilisateur tryUser = new Utilisateur();
		
		try {
			encheresManager.updateEtatVentes();
			tryUser = encheresManager.checkConnexion(tryPseudo, tryMdp);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if(tryUser.getNom().equals("non")) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			request.setAttribute("connexionRefused", "La connexion est Refus�e");
			rd.forward(request, response);
		}
			if(tryUser.getAdministrateur()==1) {
				
				HttpSession sessionUtilisateur = request.getSession();
				sessionUtilisateur.setAttribute("userConnected", tryUser);
				RequestDispatcher rd = request.getRequestDispatcher("/accueilAdmin");
				rd.forward(request, response);			
			
			
		}else {
			HttpSession sessionUtilisateur = request.getSession();
			sessionUtilisateur.setAttribute("userConnected", tryUser);
			RequestDispatcher rd = request.getRequestDispatcher("/AccueilUtilisateur");
			rd.forward(request, response);			
		}
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		doGet(request, response);
	}

}
