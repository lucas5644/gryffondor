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
import javax.websocket.Session;

import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;
import javafx.application.Application;

/**
 * Servlet implementation class ServletAjoutRepas
 */
@WebServlet("/ajoutArticle")
public class ServletAjoutArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajouterArticle.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nomArticle = null;
		String description = null;
		String categorie;
		int prixDepart = 0;
		LocalDate debutEnchere = null;
		LocalDate finEnchere = null;
		Utilisateur user = new Utilisateur();
		HttpSession session = request.getSession();
		System.out.println(session);
		user = (Utilisateur) session.getAttribute("userConnected");
		request.setCharacterEncoding("UTF-8");
		List<Integer> listeCodesErreur = new ArrayList<>();
		// lecture article
		nomArticle = request.getParameter("article");
		if (nomArticle == null || nomArticle.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_ARTICLE_ERREUR);
		}
		// lecture description
		description = request.getParameter("description");
		if (description == null || description.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_DESCRIPTION_ERREUR);
		}
		// lecture categorie
		categorie = request.getParameter("categorie");
		// lecture prix
		try {
			prixDepart = Integer.parseInt(request.getParameter("prixDepart"));
		} catch (Exception e) {
			System.out.println("ERREUR DE MERDE");
		}

		// lecture date
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			debutEnchere = LocalDate.parse(request.getParameter("dateDebut"), dtf);
			finEnchere = LocalDate.parse(request.getParameter("dateFin"), dtf);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.FORMAT_DATE_ERREUR);
		}

		// Réalisation du traitement
		if (listeCodesErreur.size() > 0) {
			// Je renvoie les codes d'erreurs
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajouterArticle.jsp");
			rd.forward(request, response);
		} else {
			// j'ajoute l'article
			EncheresManager encheresManager = new EncheresManager();
			try {
				Article newArticle = new Article();
				Categorie newCategorie = new Categorie();
				
				switch (categorie) {
				case "1":
					newCategorie.setNoCategorie(1);
					newCategorie.setLibelle("Informatique");
					break;
				case "2":
					newCategorie.setNoCategorie(2);
					newCategorie.setLibelle("Ameublement");
					break;
				case "3":
					newCategorie.setNoCategorie(3);
					newCategorie.setLibelle("Vêtement");
					break;
				case "4":
 					newCategorie.setNoCategorie(4);
					newCategorie.setLibelle("Sport et loisir");
					break;
				}
				newArticle.setNomArticle(nomArticle);
				newArticle.setDescription(description);
				newArticle.setMiseAPrix(prixDepart);
				newArticle.setCategorieArticle(newCategorie);
				newArticle.setDateDebutEncheres(debutEnchere);
				newArticle.setDateFinEncheres(finEnchere);
				newArticle.setVendeur(user);
				encheresManager.ajouterArticle(newArticle);
				System.out.println(newArticle);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueilConnecte.jsp");
				rd.forward(request, response);

			} catch (BusinessException e) {
				// Sinon je retourne à la page d'ajout pour indiquer les problèmes:
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajouterArticle.jsp");
				rd.forward(request, response);
			}
		}
	}
}
