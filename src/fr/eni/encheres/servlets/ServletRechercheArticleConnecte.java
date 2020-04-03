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
import fr.eni.encheres.bo.Enchere;
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
					listeArticles = EM.selectAllArticles(nomCategorie, nomArticle);
				}
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			} 
		}
		System.out.println(listeArticles);
		
		List<Article> MaListe = new ArrayList<Article>();
		switch (request.getParameter("mode")) {
		
		case "modeAchat":
			
			String EnCours = "non";
			if(request.getParameter("achatEnCours") != null) {
				EnCours = request.getParameter("achatEnCours");
			}
			String AchatOuvert = "non";
			if(request.getParameter("achatOuvert")!= null) {
				AchatOuvert =request.getParameter("achatOuvert");
			}
			String AchatRemport = "non";
			if(request.getParameter("achatRemport")!= null) {
				AchatRemport = request.getParameter("achatRemport");
			}
			//On commence l'écrémage en fonction des parametres
			
			if(!AchatOuvert.equals("non")) {
				for (Article article : listeArticles) {
					if(article.getEtatVente().equals("En cours")  && article.getVendeur().getNoUtilisateur()!= userConnected.getNoUtilisateur()) {
						MaListe.add(article);
					}
				}
			}
			if(!EnCours.equals("non")) {
				for (Article article : listeArticles) {
					if(article.getEtatVente().equals("En cours") && article.getVendeur().getNoUtilisateur()!= userConnected.getNoUtilisateur()) {
						try {
							List<Article> checkList = EM.selectEncheresUtilisateur(userConnected.getNoUtilisateur());
							for (Article article2 : checkList) {
								MaListe.add(article2);
							}
						} catch (BusinessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}
			if(!AchatRemport.equals("non")) {
				for (Article article : listeArticles) {
					if(article.getEtatVente().equals("Enchères terminées")  && article.getVendeur().getNoUtilisateur()!= userConnected.getNoUtilisateur()) {
						try {
							Enchere testEnchere=EM.selectMeilleureEnchere(article.getNoArticle());
							if(testEnchere.getUtilisateur().getNoUtilisateur() == userConnected.getNoUtilisateur()) {
								MaListe.add(article);
							}
						} catch (BusinessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
									
					}
				}
			}
			break;
		case "modeVente":
			String VEnCours = "non";
			if(request.getParameter("venteEnCours")!= null) {
				VEnCours = request.getParameter("venteEnCours");
			}
			String VNonDebutees = "non"; 
			if(request.getParameter("venteNonDebutees")!= null) {
				VNonDebutees = request.getParameter("venteNonDebutees");
			}
			String venteTerminees = "non"; 
			if(request.getParameter("venteTerminees")!= null) {
				venteTerminees = request.getParameter("venteTerminees");
			}
			if(!VNonDebutees.equals("non")) {
				for (Article article : listeArticles) {
					if(article.getEtatVente().equals("Créée") && article.getVendeur().getNoUtilisateur() == userConnected.getNoUtilisateur() ) {
						MaListe.add(article);
					}
				}
			}
			if(!VEnCours.equals("non")) {
				for (Article article : listeArticles) {
					if(article.getEtatVente().equals("En cours")&& article.getVendeur().getNoUtilisateur() == userConnected.getNoUtilisateur()) {
						MaListe.add(article);
					}
				}
			}
			if(!venteTerminees.equals("non")) {
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
