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
		
	}
//	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresManager enchereManager = new EncheresManager();
		List<Integer> listeCodesErreur = new ArrayList<Integer>();
		request.setAttribute("listeCodesErreur",listeCodesErreur);
		
		
		
		String noUtilisateur = request.getParameter("noUtilisateur");
		System.out.println("noUtilisateur " + noUtilisateur);
		int noUtilisateur1 = Integer.parseInt(noUtilisateur);
		System.out.println("no Utilisateur " + noUtilisateur);
		
		System.out.println("no Utilisateur 1 :" + noUtilisateur1);
		boolean verification;
		try {
			
			verification = enchereManager.removeUtilisateur(noUtilisateur1);
			
			if(verification == true) {
			
				RequestDispatcher rd = request.getRequestDispatcher("/listeUtilisateurAdmin");
				
				rd.forward(request, response);		
			}
			else {
				request.setAttribute("echec", "Erreur, le compte n'a pas été suprimé");
				RequestDispatcher	 rd = request.getRequestDispatcher("/listeUtilisateurAdmin");
				rd.forward(request, response);
			}
			
		} catch (BusinessException e) {
			request.setAttribute("echec", "Erreur, le compte n'a pas été suprimé");
			RequestDispatcher rd = request.getRequestDispatcher("/listeUtilisateurAdmin");
			rd.forward(request, response);			
			e.printStackTrace();
		}
		
	
	

		doGet(request, response);
	
	}
}
