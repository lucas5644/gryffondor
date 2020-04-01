package fr.eni.encheres.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

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
		
		PrintWriter out = response.getWriter();

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
		Utilisateur user = new Utilisateur();
		HttpSession session = request.getSession();
		System.out.println(session);
		user = (Utilisateur) session.getAttribute("userConnected");
		request.setCharacterEncoding("UTF-8");
		
		
//		if(!ServletFileUpload.isMultipartContent(request)) {
//			out.println("Pas d'image");
//			return;
//		}
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
			listeCodesErreur.add(CodesResultatServlets.FORMAT_PRIX_ERREUR);
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
			// j'ajoute l'article
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
				encheresManager.ajouterArticle(newArticle, newLieuDeRetrait);
				FileItemFactory itemFactory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(itemFactory);
//				try {
//					List<FileItem> items = upload.parseRequest((RequestContext) request);
//					for(FileItem item : items) {
//						
//						String contentType = item.getContentType();
//						if(!contentType.equals("image/png")) {
//							out.println("Seul les images png sont reçues");
//							continue;
//						}
//						File uploadDir = new File("C:\\uploadTP");
//						File file = File.createTempFile("img", ".png", uploadDir);
//						item.write(file);
//						
//						out.println("Fichier saved");
//					}
//				}catch (Exception e) {
//					
//				}
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
