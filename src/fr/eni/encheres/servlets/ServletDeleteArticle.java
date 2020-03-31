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
     * @see HttpServlet#HttpServlet()
     */
    public ServletDeleteArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Integer> listeCodesErreur = new ArrayList<Integer>();
		request.setAttribute("listeCodesErreur",listeCodesErreur);
		
		
		
		LocalDate debutEnchere = null;
		LocalDate dateDuJour = null;
		
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			debutEnchere = LocalDate.parse(request.getParameter("dateDebut"), dtf);
			dateDuJour = LocalDate.now();
		}
		catch (DateTimeParseException e) {
			e.printStackTrace();
			return;
		 
		}
		String noArticle = request.getParameter("noArticle");
		EncheresManager enchereManager = new EncheresManager();
		int noArticlee = Integer.parseInt(noArticle);
		boolean verification;
		try {
			
			verification = enchereManager.removeArticle(noArticlee);
			
			if(verification == true) {
				HttpSession session = request.getSession();
				session.invalidate();
				RequestDispatcher rd = request.getRequestDispatcher("/accueil");
				
				rd.forward(request, response);		
			}
			else {
				request.setAttribute("echec", "Erreur, le compte n'a pas été suprimé");
				RequestDispatcher rd = request.getRequestDispatcher("/profilCompte");
				rd.forward(request, response);
			}
			
		} catch (BusinessException e) {
			request.setAttribute("echec", "Erreur, le compte n'a pas été suprimé");
			RequestDispatcher rd = request.getRequestDispatcher("/accueil");
			rd.forward(request, response);			
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
