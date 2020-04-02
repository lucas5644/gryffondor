package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletUpdateArticle
 */
@WebServlet("/DeleteArticle")
public class ServletDeleteArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Integer> listeCodesErreur = new ArrayList<Integer>();
		LocalDate debutEnchere = null;
		LocalDate dateDuJour = null;
		EncheresManager encheresManager = new EncheresManager();
		String noArticle = request.getParameter("noArticle");
		int noArticle1 = Integer.parseInt(noArticle);
		Article art = new Article();
		try {
			art = encheresManager.selectArticleById(noArticle1);
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}

		debutEnchere = art.getDateDebutEncheres();
		try {
			dateDuJour = LocalDate.now();
			// comparaison de dates
			if (dateDuJour.equals(debutEnchere)||dateDuJour.isAfter(debutEnchere)) {
				listeCodesErreur.add(CodesResultatServlets.ERREUR_ENCHERE_EN_COURS_NON_MODIFIABLE);
			}
			
			// Réalisation du traitement
			if (listeCodesErreur.size() > 0) {
				// Je renvoie les codes d'erreurs
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/updateVente.jsp");
				rd.forward(request, response);
			} else {
				//si pas d'erreur je suprime l'article
				try {
					encheresManager.removeArticle(noArticle1);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueilConnecte.jsp");
					rd.forward(request, response);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch (DateTimeParseException e) {
			e.printStackTrace();
			return;
		}		
	}
}
