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
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletDeleteUserAdmin
 */
@WebServlet("/DeleteUserAdmin")
public class ServletDeleteUserAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresManager enchereManager = new EncheresManager();
		List<Utilisateur>listeUtilisateur = null;
		try {
			System.out.println("coucou");
			listeUtilisateur = enchereManager.selectUtilisateurPourAdmin();
			request.setAttribute("listeUtilisateur", listeUtilisateur);
			System.out.println("liste "+listeUtilisateur);
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/listeUtilisateurAdmin.jsp")	;
		rd.forward(request, response);
				
		List<Integer> listeCodesErreur = new ArrayList<Integer>();
		request.setAttribute("listeCodesErreur",listeCodesErreur);
		
		String noUtilisateur = request.getParameter("noUtilisateur");
		System.out.println("no Utilisateur " + noUtilisateur);
		int noUtilisaateur = Integer.parseInt(noUtilisateur);
		boolean verification;
		try {
			
			verification = enchereManager.removeUtilisateur(noUtilisaateur);
			
			if(verification == true) {
			
				 rd = request.getRequestDispatcher("/listeUtilisateurAdmin");
				
				rd.forward(request, response);		
			}
			else {
				request.setAttribute("echec", "Erreur, le compte n'a pas été suprimé");
				 rd = request.getRequestDispatcher("/listeUtilisateurAdmin");
				rd.forward(request, response);
			}
			
		} catch (BusinessException e) {
			request.setAttribute("echec", "Erreur, le compte n'a pas été suprimé");
		 rd = request.getRequestDispatcher("/listeUtilisateurAdmin");
			rd.forward(request, response);			
			e.printStackTrace();
		}
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	}
