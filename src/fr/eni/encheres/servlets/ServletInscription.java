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
 * Servlet implementation class ServletTestConnexion
 */
@WebServlet("/inscriptionUser")
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			EncheresManager encheresManager = new EncheresManager();
			Utilisateur user = new Utilisateur();
			
			String cp = request.getParameter("cp");
			int credit = 0;
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String rue = request.getParameter("rue");
			String pseudo = request.getParameter("pseudo");
			String email = request.getParameter("email");
			String tel = "non renseigné";
			if(!request.getParameter("telephone").equals(null)) {
				tel = request.getParameter("telephone");
			}
			String ville = request.getParameter("ville");
			String mdp = request.getParameter("mdp");
			
			System.out.println(cp);
			System.out.println(mdp);
			
			user.setCodePostal(cp);
			user.setCredit(credit);
			user.setNom(nom);
			user.setPrenom(prenom);
			user.setRue(rue);
			user.setPseudo(pseudo);	
			user.setEmail(email);
			user.setTelephone(email);
			user.setVille(ville);
			user.setMotDePasse(mdp);
			user.setTelephone(tel);
			encheresManager.ajouterUser(user);
			if(user.getNoUtilisateur()!= -1)
			{
				try {
					encheresManager.updateEtatVentes();
					Utilisateur tryUser = new Utilisateur();
					tryUser = encheresManager.checkConnexion(user.getPseudo(), user.getMotDePasse());
					HttpSession session = request.getSession();
					session.setAttribute("userConnected", tryUser);
					RequestDispatcher rd = request.getRequestDispatcher("/AccueilUtilisateur");
					rd.forward(request, response);
					
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp");
			rd.forward(request, response);
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
