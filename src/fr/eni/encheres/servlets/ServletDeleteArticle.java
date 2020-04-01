package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.EncheresManager;
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
		
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			debutEnchere = LocalDate.parse(request.getParameter("dateDebut"), dtf);
			dateDuJour = LocalDate.now();
			if (dateDuJour.equals(debutEnchere)||dateDuJour.isAfter(debutEnchere)) {
				listeCodesErreur.add(CodesResultatServlets.FORMAT_DATE_ERREUR);
			}
			// Réalisation du traitement
			if (listeCodesErreur.size() > 0) {
				// Je renvoie les codes d'erreurs
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/updateVente.jsp");
				rd.forward(request, response);
			} else {
				//si pas d'erreur je suprime l'article
				EncheresManager encheresManager = new EncheresManager();
				int noArticle = Integer.parseInt(request.getParameter("noArticle"));
				try {
					encheresManager.removeArticle(noArticle);
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
