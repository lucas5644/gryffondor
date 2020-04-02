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
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletModifierVente
 */
@WebServlet("/ModifierVente")
public class ServletModifierVente extends HttpServlet {
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
		Article art;
		String noArticle = request.getParameter("noArticle");
		int noArticle1 = Integer.parseInt(noArticle);
		System.out.println("Teste : "+noArticle1);
		String nomArticle = null;
		String description = null;
		String categorie;
		int prixDepart = 0;
		LocalDate debutEnchere = null;
		LocalDate finEnchere = null;
		String etatVente = null;
		String rue = null;
		String codePostal = null;
		String ville = null;
		LocalDate dateDuJour = null;
		Utilisateur user = new Utilisateur();
		HttpSession session = request.getSession();
		
	
		System.out.println("C'est :"+session);
		user = (Utilisateur) session.getAttribute("userConnected");
		System.out.println("Teste : "+user);
		
		request.setCharacterEncoding("UTF-8");
		List<Integer> listeCodesErreur = new ArrayList<>();
		// lecture article
		nomArticle = request.getParameter("nomArticle");
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
			listeCodesErreur.add(CodesResultatServlets.FORMAT_PRIX_ERREUR);
		}

		// lecture date
		try {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			debutEnchere = LocalDate.parse(request.getParameter("dateDebut"), dtf);
			dateDuJour = LocalDate.now();
			if (dateDuJour.equals(debutEnchere)||dateDuJour.isAfter(debutEnchere)) {
				listeCodesErreur.add(CodesResultatServlets.FORMAT_DATE_ERREUR);
			}
			finEnchere = LocalDate.parse(request.getParameter("dateFin"), dtf);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.FORMAT_DATE_ERREUR);
		}
		// lecture de la rue
		rue = request.getParameter("rue");
		if (rue == null || rue.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_RUE_ERREUR);
		}
		// lecture du code postal
		codePostal = request.getParameter("codePostal");
		if (codePostal == null || codePostal.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_CODE_POSTAL_ERREUR);
		}
		// lecture de la ville
		ville = request.getParameter("ville");
		if (ville == null || ville.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_VILLE_ERREUR);
		}

		// Réalisation du traitement
		if (listeCodesErreur.size() > 0) {
			// Je renvoie les codes d'erreurs
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajouterArticle.jsp");
			rd.forward(request, response);
		} else {
			// je mets à jour l'article  j'ajoute l'article
			EncheresManager encheresManager = new EncheresManager();
			try {
				Article newArticle = new Article();
				Categorie newCategorie = new Categorie();
				LocalDate now = LocalDate.now();
				Retrait newLieuDeRetrait = new Retrait(rue, codePostal, ville);

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
				if (debutEnchere.isAfter(now)) {
					etatVente = "Créée";
				}
				if (debutEnchere.isEqual(now)) {
					etatVente = "En cours";
				}
				if (debutEnchere.isEqual(finEnchere)) {
					etatVente = "Enchères terminées";
				}
				newArticle.setEtatVente(etatVente);
				newArticle.setVendeur(user);
				newArticle.setLieuRetrait(newLieuDeRetrait);
				newArticle.setNoArticle(noArticle1);
				art=encheresManager.updateArticle(newArticle, newLieuDeRetrait);
				
				System.out.println(newArticle);
				
				System.out.println();
				System.out.println("Fin"+newArticle);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueilConnecte.jsp");
				rd.forward(request, response);

			} catch (BusinessException e) {
				// Sinon je retourne à la page d'ajout pour indiquer les problèmes:
				// Modifier vers la jsp  
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/UpdateVente.jsp");
				rd.forward(request, response);
			}
		}
	}
}
		
		
	
		
