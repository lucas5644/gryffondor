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

import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

@WebServlet("/RechercheArticleConnecte")
public class ServletRechercheArticleConnecte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Utilisateur userConnected = (Utilisateur) session.getAttribute("userConnected");
		EncheresManager EM = new EncheresManager();
		
		String nomCategorie = request.getParameter("nomCategorie");
		String nomArticle = request.getParameter("nomArticle");
		List<Integer> listeCodesErreur = new ArrayList<>();
		List<Article> listeArticles = null;

		if (listeCodesErreur.size()>0) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		}else {
			try {
				
				if (nomArticle != null || nomCategorie != null) {
					listeArticles = EM.selectArticleDeconnecte(nomCategorie, nomArticle);
				}
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			} 
		}
		
		List<Article> MaListe = new ArrayList<Article>();
		MaListe = null;
		if(request.getParameter("mode").isEmpty()) {
			request.setAttribute("erreur", "Il faut choisir Achat ou Vente");
		}
		switch (request.getParameter("mode")) {
		
		case "modeAchat":
			
			String EnCours = request.getParameter("achatEnCours");
			String AchatOuvert = request.getParameter("achatOuvert");
			String AchatRemport = request.getParameter("achatRemport");
			//On commence l'écrémage en fonction des parametres
			if(!AchatOuvert.equals(null)) {
				for (Article article : listeArticles) {
					if(article.getEtatVente().equals("Créée")) {
						MaListe.add(article);
					}
				}
			}
			if(!EnCours.equals(null)) {
				for (Article article : listeArticles) {
					if(article.getEtatVente().equals("En cours")) {
						List<Utilisateur> checkList = article.getListeAcheteurs();
						for (Utilisateur user : checkList) {
							if(user.getNoUtilisateur() == userConnected.getNoUtilisateur()) {
								MaListe.add(article);
							}
						}
					}
				}
			}
			if(!AchatRemport.equals(null)) {
				for (Article article : listeArticles) {
					if(article.getEtatVente().equals("Enchères terminées")) {
						List<Utilisateur> checkList = article.getListeAcheteurs();
						for (Utilisateur user : checkList) {
							if(user.getNoUtilisateur() == userConnected.getNoUtilisateur()) {
								MaListe.add(article);
							}
						}
					}
				}
			}
			break;
		case "modeVente":
			String VEnCours = request.getParameter("venteEnCours");
			String VNonDebutees = request.getParameter("venteNonDebutees");
			String venteTerminees = request.getParameter("venteTerminees");
			if(!VNonDebutees.equals(null)) {
				for (Article article : listeArticles) {
					if(article.getEtatVente().equals("Créée") && article.getVendeur().getNoUtilisateur() == userConnected.getNoUtilisateur() ) {
						MaListe.add(article);
					}
				}
			}
			if(!VEnCours.equals(null)) {
				for (Article article : listeArticles) {
					if(article.getEtatVente().equals("En cours")&& article.getVendeur().getNoUtilisateur() == userConnected.getNoUtilisateur()) {
						MaListe.add(article);
					}
				}
			}
			if(!venteTerminees.equals(null)) {
				for (Article article : listeArticles) {
					if(article.getEtatVente().equals("Enchères terminées") && article.getVendeur().getNoUtilisateur() == userConnected.getNoUtilisateur()) {
						MaListe.add(article);
					}
				}
			}
			break;
		default:
			break;
		}
		
		request.setAttribute("ListeResultat", MaListe);
		
		RequestDispatcher rd = request.getRequestDispatcher("/AccueilUtilisateur");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
