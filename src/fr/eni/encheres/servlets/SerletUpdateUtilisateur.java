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
import javax.servlet.http.HttpSession;

import org.apache.catalina.startup.UserConfig;

import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class SerletUpdateUtilisateur
 */
@WebServlet("/SerletUpdateUtilisateur")
public class SerletUpdateUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Integer> listeCodesErreur = new ArrayList<>();
		
		
		EncheresManager enchereManager = new EncheresManager();
		Utilisateur updateUtilisateur = new Utilisateur();
		Utilisateur user; 
		BusinessException be = new BusinessException();
		HttpSession session = request.getSession(); 
		
		
		try {
			
		 user=(Utilisateur) session.getAttribute("userConnected");	
		 System.out.println(user);
		 String pseudo = request.getParameter("pseudo");
		 String nom = request.getParameter("nom");
		 String prenom = request.getParameter("prenom");
		 String email = request.getParameter("email");
		 String telephone = "non renseigne";
		 if(!request.getParameter("telephone").equals(null)) {	 
			  telephone = request.getParameter("telephone");
		 }
		 String rue = request.getParameter("rue");
		 String code_postal = request.getParameter("codePostal");
		 String ville = request.getParameter("ville");
		 String motDePasse = request.getParameter("motDePasse");
		 if(request.getParameter("motDePasse").equals("")) {
			 motDePasse=user.getMotDePasse();
		 }
		
		 String nouveauMotDePasse=request.getParameter("nouveauMotDePasse");
		 String cNouveauMotDePasse=request.getParameter("cNouveauMotDePasse");
		 if(nouveauMotDePasse.equals(cNouveauMotDePasse) && nouveauMotDePasse!=motDePasse && nouveauMotDePasse!= "") {
			 motDePasse=nouveauMotDePasse;
		 }
		 
			// Réalisation du traitement
			if (listeCodesErreur.size() > 0) {
				// Je renvoie les codes d'erreurs
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp");
				rd.forward(request, response);
			} else {
			}
		 updateUtilisateur.setNoUtilisateur(user.getNoUtilisateur());
		 updateUtilisateur.setPseudo(pseudo);
		 updateUtilisateur.setNom(nom);
		 updateUtilisateur.setPrenom(prenom);
		 updateUtilisateur.setEmail(email);
		 updateUtilisateur.setTelephone(telephone);
		 updateUtilisateur.setRue(rue);
		 updateUtilisateur.setCodePostal(code_postal);
		 updateUtilisateur.setVille(ville);
		 updateUtilisateur.setMotDePasse(motDePasse);
		
		 
		enchereManager.checkUser(updateUtilisateur, be);
		
		user=enchereManager.updateUtilisateur(updateUtilisateur);
		
		session.setAttribute("userConnected", updateUtilisateur);
		
		System.out.println(user);
		} catch (Exception e) {
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
