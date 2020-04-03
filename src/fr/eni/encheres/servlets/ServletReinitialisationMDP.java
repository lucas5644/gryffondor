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
 * Servlet implementation class ServletReinitialisationMDP
 */
@WebServlet("/ReinitialisationMDP")
public class ServletReinitialisationMDP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateur = new Utilisateur();
		Utilisateur user1;
		
		 String email=request.getParameter("email");
	   System.out.println("Email : "+ email);
		   utilisateur.setEmail(email);
		 String nouveauMotDePasse=request.getParameter("nouveauMotDePasse");
		 String cNouveauMotDePasse=request.getParameter("cNouveauMotDePasse");
		 if(nouveauMotDePasse.equals(cNouveauMotDePasse)){
			 utilisateur.setMotDePasse(cNouveauMotDePasse);
		 }
		
		 EncheresManager enchereManager = new EncheresManager();

			try {
				user1 = enchereManager.updateMDP(utilisateur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");

				rd.forward(request, response);

			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
				rd.forward(request, response);
			}
		}
		
}
