package fr.eni.encheres.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EncheresManager;

/**
 * Servlet implementation class ServletDeleteUser
 */
@WebServlet("/ServletDeleteUser")
public class ServletDeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String noUtilisateur = request.getParameter("noUtilisateur");
		EnchereManager enchereManager
		
		//TODO à parir du numeroUtilisateur je récupere un objet Utilisateur.
		//Appel du ManagerUtilisateur qui en lui passant l'id de  l utilisateur retourne un objet utilisateur.
		//Le manager Utilisateur lui applle la DAO Utilisateur
		//Appel du managerUtilisateur avec la fonction delete qui prend un idUtilisateur en parametre.
		///une fois supprimé redirection vers la page d'accueil.
		this.getServletContext().getRequestDispatcher("/WEB-INF/profilCompte.jsp").forward(request, response);
	}

}
