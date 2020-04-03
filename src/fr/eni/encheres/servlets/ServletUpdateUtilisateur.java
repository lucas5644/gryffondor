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
@WebServlet("/updateUtilisateur")
public class ServletUpdateUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Integer> listeCodesErreur = new ArrayList<>();

		Utilisateur user = new Utilisateur();
		Utilisateur user1;
		BusinessException be = new BusinessException();
		HttpSession session = request.getSession();

		try {

			user = (Utilisateur) session.getAttribute("userConnected");
			System.out.println(user);
			String pseudo = request.getParameter("pseudo");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String telephone = "non renseigne";
			if (!request.getParameter("telephone").equals(null)) {
				telephone = request.getParameter("telephone");
			}
			String rue = request.getParameter("rue");
			String code_postal = request.getParameter("codePostal");
			String ville = request.getParameter("ville");

			String motDePasse = request.getParameter("motDePasse");
			if (!request.getParameter("motDePasse").equals(user.getMotDePasse())) {
				listeCodesErreur.add(CodesResultatServlets.ERREUR_MOT_DE_PASSE);
			}

			String nouveauMotDePasse = request.getParameter("nouveauMotDePasse");
			String cNouveauMotDePasse = request.getParameter("cNouveauMotDePasse");
			if (nouveauMotDePasse.equals(cNouveauMotDePasse) && nouveauMotDePasse != motDePasse
					&& nouveauMotDePasse != "") {
				motDePasse = nouveauMotDePasse;
			}

			user.setNoUtilisateur(user.getNoUtilisateur());
			user.setPseudo(pseudo);
			user.setNom(nom);
			user.setPrenom(prenom);
			user.setEmail(email);
			user.setTelephone(telephone);
			user.setRue(rue);
			user.setCodePostal(code_postal);
			user.setVille(ville);
			user.setMotDePasse(motDePasse);
		} catch (Exception e) {
			e.printStackTrace();
			// listeCodesErreur
		}
		// Réalisation du traitement
		if (listeCodesErreur.size() > 0) {
			// Je renvoie les codes d'erreurs
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp");
			rd.forward(request, response);
		} else {
			EncheresManager enchereManager = new EncheresManager();

			try {
				user1 = enchereManager.updateUtilisateur(user);

				session.setAttribute("userConnected", user1);

				System.out.println(user);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/utilisateurBtnModifier.jsp");

				rd.forward(request, response);

			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp");
				rd.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
