package fr.eni.encheres.dal.jdbc;

import java.time.LocalDate;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.exception.BusinessException;
import sun.util.resources.LocaleData;

public class TestJdbc {

	public static void main(String[] args) throws BusinessException {

		Utilisateur user = new Utilisateur(1, "Victor35", "Bernard", "Victor", "v.bernard@gmail.com", "0611192669", "5 rue Lagrange", "35000", "Rennes", "vickorock", 100, 1);
		Categorie categorie = new Categorie(1, "Informatique");
		Article a1 = new Article();
		EnchereDAO enchereDAO = DAOFactory.getEnchereDAO();
		
		System.out.println(user);
		System.out.println(categorie);
		System.out.println("Rien");
		//AJOUT D'UN ARTICLE
		a1.setNomArticle("Ordi de Victor");
		a1.setDescription("Très bel objet, bon état");
		a1.setDateDebutEncheres(LocalDate.now());
		a1.setDateFinEncheres(LocalDate.of(2020, 05, 25));

		a1.setVendeur(user);
		a1.setCategorieArticle(categorie);
		System.out.println(a1);
		enchereDAO.insertArticle(a1);
		
	}

}
