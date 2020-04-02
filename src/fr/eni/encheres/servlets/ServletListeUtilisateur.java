package fr.eni.encheres.servlets;

import java.io.IOException;
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
 * Servlet implementation class ServletListeUtilisateur
 */
@WebServlet("/ListeUtilisateur")
public class ServletListeUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       Utilisateur user;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresManager enchereManager = new EncheresManager();
	//	request.setAttribute("noUtilisateur", user.getNoUtilisateur());
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
