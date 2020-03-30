package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.exception.BusinessException;
import microsoft.sql.DateTimeOffset;


public class EnchereDAOJdbcImpl implements EnchereDAO {
	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES(nom_article,description,prix_initial,date_debut_encheres,date_fin_encheres,no_utilisateur,no_categorie,etat_vente) VALUES(?,?,?,?,?,?,?,?);";
	private static final String INSERT_LIEU_RETRAIT="INSERT INTO RETRAITS(no_article,rue,code_postal,ville) VALUES (?,?,?,?);";
	private static final String SELECT_ARTICLE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur,a.no_categorie,a.etat_vente,c.libelle, u.pseudo,u.nom,u.prenom,u.email,u.telephone,u.rue,u.code_postal,u.ville,u.mot_de_passe,u.credit,u.administrateur,e.date_enchere,e.montant_enchere FROM ARTICLES a LEFT JOIN CATEGORIES c on c.no_categorie = a.no_categorie LEFT JOIN UTILISATEURS u on u.no_utilisateur = a.no_utilisateur LEFT JOIN RETRAITS r on r.no_article = a.no_article LEFT JOIN ENCHERES e on e.no_article = a.no_article";

	private static final String SELECT_ARTICLE_DECONNECTE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur,a.no_categorie,a.etat_vente,c.libelle, u.pseudo,u.nom,u.prenom,u.email,u.telephone,u.rue,u.code_postal,u.ville,u.mot_de_passe,u.credit,u.administrateur,e.date_enchere,e.montant_enchere FROM ARTICLES a LEFT JOIN CATEGORIES c on c.no_categorie = a.no_categorie LEFT JOIN UTILISATEURS u on u.no_utilisateur = a.no_utilisateur LEFT JOIN RETRAITS r on r.no_article = a.no_article LEFT JOIN ENCHERES e on e.no_article = a.no_article WHERE a.nom_article LIKE ? AND c.libelle LIKE ?;";
	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom , email ,telephone ,rue , code_postal, ville, mot_de_passe, credit, administrateur) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
	private static final String CHECK_CONNEXION = "select no_utilisateur ,pseudo , nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, administrateur, credit from UTILISATEURS where pseudo = ? and mot_de_passe = ?;";
	private static final String CHECK_PSEUDO = "SELECT pseudo FROM UTILISATEURS WHERE pseudo = ?";
	private static final String CHECK_MAIL = "SELECT email FROM UTILISATEURS WHERE email = ?";
	private static final String SELECT_UTILISATEUR = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville FROM UTILISATEURS WHERE pseudo = ?";
	private static final String DELETE = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?;";
	private static final String UPDATE_UTILISATEUR = "update UTILISATEURS set pseudo = ?, nom = ?, prenom =  ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? where no_utilisateur = ?;";
	private static final String DELETE_ARTICLE = "DELETE FROM ARTICLES WHERE no_article = ?";
	private static final String DELETE_RETRAIT = "DELETE FROM RETRAITS WHERE no_article = ?";
	
	private static final String UPDATE_ARTICLE = "UPDATE ARTICLES set nom_article = ?,description  = ?, no_categorie  = ?,prix_initial  = ?, date_debut_encheres  = ?, date_fin_encheres  = ? where no_article = ?;";
	private static final String UPDATE_RETRAIT = "UPDATE RETRAITS set rue = ?, code_postal = ?, ville = ? where no_article = ? ;";
	
	
	public void insertArticle(Article article, Retrait retrait) throws BusinessException {
		Connection con = null;
		BusinessException be = new BusinessException();
		int numeroArticle;

		try {
			// Ouverture de la connexion
			con = ConnectionProvider.getConnection();
			con.setAutoCommit(false);
			PreparedStatement psmt = con.prepareStatement(INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS);
			// Insertion des données de l'article
			psmt.setString(1, article.getNomArticle());
			psmt.setString(2, article.getDescription());
			psmt.setInt(3, article.getMiseAPrix());
			psmt.setDate(4, Date.valueOf(article.getDateDebutEncheres()));
			psmt.setDate(5, Date.valueOf(article.getDateFinEncheres()));
			psmt.setInt(6, article.getVendeur().getNoUtilisateur());
			psmt.setInt(7, article.getCategorieArticle().getNoCategorie());
			psmt.setString(8, article.getEtatVente());
			// génération de la clé
			int nombreEnregistrementInsere = psmt.executeUpdate();
			if (nombreEnregistrementInsere == 1) {
				ResultSet rs = psmt.getGeneratedKeys();
				if (rs.next()) {
					article.setNoArticle(rs.getInt(1));
				}
				rs.close();
			} else {
				be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			}
			psmt.close();
			
			psmt = con.prepareStatement(INSERT_LIEU_RETRAIT);
			numeroArticle = article.getNoArticle();
			//Insertion des données du lieu de retrait
			psmt.setInt(1, numeroArticle);
			psmt.setString(2, retrait.getRue());
			psmt.setString(3, retrait.getCodePostal());
			psmt.setString(4, retrait.getVille());
			psmt.executeUpdate();
			psmt.close();
			
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			be.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (be.hasErreurs()) {
				throw be;
			}
		}
	}

	private Article mappingArticle(ResultSet rs) throws SQLException {

		Article newArticle = new Article();
		Utilisateur newUtilisateur = new Utilisateur();
		Categorie newCategorie = new Categorie();
		newArticle.setNoArticle(rs.getInt("no_article"));
		newArticle.setNomArticle(rs.getString("nom_article"));
		newArticle.setDescription(rs.getString("description"));
		newArticle.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
		newArticle.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
		newArticle.setMiseAPrix(rs.getInt("prix_initial"));
		newArticle.setPrixVente(rs.getInt("prix_vente"));
		newUtilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
		newCategorie.setNoCategorie(rs.getInt("no_categorie"));
		newArticle.setEtatVente(rs.getString("etat_vente"));
		newCategorie.setLibelle(rs.getString("libelle"));
		newUtilisateur.setPseudo(rs.getString("pseudo"));
		newUtilisateur.setNom(rs.getString("nom"));
		newUtilisateur.setPrenom(rs.getString("prenom"));
		newUtilisateur.setEmail(rs.getString("email"));
		newUtilisateur.setTelephone(rs.getString("telephone"));
		newUtilisateur.setRue(rs.getString("rue"));
		newUtilisateur.setCodePostal(rs.getString("code_postal"));
		newUtilisateur.setVille(rs.getString("ville"));
		newUtilisateur.setMotDePasse(rs.getString("mot_de_passe"));
		newUtilisateur.setCredit(rs.getInt("credit"));
		newUtilisateur.setAdministrateur(rs.getInt("administrateur"));
		newArticle.setVendeur(newUtilisateur);
		newArticle.setCategorieArticle(newCategorie);
		return newArticle;
	}

	@Override
	public List<Article> selectArticleDeconnecte(String nomCategorie, String nomArticle) throws BusinessException {
		List<Article> listeArticleDeconnecte = new ArrayList<Article>();
		try (Connection con = ConnectionProvider.getConnection();
				PreparedStatement psmt = con.prepareStatement(SELECT_ARTICLE_DECONNECTE);){
			BusinessException be = new BusinessException();
			psmt.setString(1, "%"+nomArticle+"%");  
			psmt.setString(2, "%"+nomCategorie+"%");
			ResultSet rs = psmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = mappingArticle(rs);
				if (articleCourant.getEtatVente().equals("En cours")) {
					listeArticleDeconnecte.add(articleCourant);
				}
			}
			return listeArticleDeconnecte;
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_ARTICLE_DECONNECTE);
			throw be;
		}
	}

	public Utilisateur insert(Utilisateur user) throws BusinessException {
		Connection cnx = null;
		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);
			PreparedStatement psmt = cnx.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS);
			psmt.setString(1, user.getPseudo());
			psmt.setString(2, user.getNom());
			psmt.setString(3, user.getPrenom());
			psmt.setString(4, user.getEmail());
			if (user.getTelephone() != null) {
				psmt.setString(5, user.getTelephone());
			} else {
				psmt.setString(5, null);
			}
			psmt.setString(6, user.getRue());
			psmt.setString(7, user.getCodePostal());
			psmt.setString(8, user.getVille());
			psmt.setString(9, user.getMotDePasse());
			psmt.setInt(10, user.getCredit());
			psmt.setInt(11, 0);

			int resultatUpdate = psmt.executeUpdate();
			if (resultatUpdate == 1) {
				ResultSet rs = psmt.getGeneratedKeys();
				if (rs.next()) {
					user.setNoUtilisateur(rs.getInt(1));
				}
			}
			cnx.commit();
			cnx.close();
			psmt.close();
			return user;
		} catch (Exception e) {
			try {
				e.printStackTrace();
				cnx.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return user;
	}

	public boolean checkPseudoMail(String pseudo, String mail) throws BusinessException {
		Connection cnx = null;
		BusinessException be = new BusinessException();

		try {
			cnx = ConnectionProvider.getConnection();

			PreparedStatement psmt = cnx.prepareStatement(CHECK_MAIL);
			psmt.setString(1, mail);

			ResultSet rs = psmt.executeQuery();

			if (rs.next()) {
				if (rs.getString("email").equals(mail)) {
					return false;
				}
			}
			try {
				cnx.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();

			throw be;
		}
		try {
			cnx = ConnectionProvider.getConnection();

			PreparedStatement psmt = cnx.prepareStatement(CHECK_PSEUDO);
			psmt.setString(1, pseudo);

			ResultSet rs = psmt.executeQuery();

			rs.next();
			if (rs.next()) {
				if (rs.getString("pseudo").equals(pseudo)) {
					return false;
				}
			}
			try {
				cnx.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();

			throw be;
		}
		return true;
	}

	public Utilisateur checkConnexion(String tryPseudo, String tryMdp) throws BusinessException {
		Connection cnx = null;
		BusinessException be = new BusinessException();

		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement psmt = cnx.prepareStatement(CHECK_CONNEXION);
			psmt.setString(1, tryPseudo);
			psmt.setString(2, tryMdp);
			ResultSet rs = psmt.executeQuery();
			Utilisateur tryUser = new Utilisateur();
			if (rs.next()) {
				tryUser = mappingUser(rs);
			} else {
				tryUser.setNom("non");
			}
			try {
				System.out.println("okayy");
				cnx.close();
				return tryUser;
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw be;
		}
		return null;
	}

	private Utilisateur mappingUser(ResultSet rs) throws BusinessException {

		Utilisateur newUtilisateur = new Utilisateur();
		try {
			newUtilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
			newUtilisateur.setNom(rs.getString("nom"));
			newUtilisateur.setPrenom(rs.getString("prenom"));
			newUtilisateur.setEmail(rs.getString("email"));
			if (rs.getString("telephone") != null) {
				newUtilisateur.setTelephone(rs.getString("telephone"));
			}
			newUtilisateur.setRue(rs.getString("rue"));
			newUtilisateur.setCodePostal(rs.getString("code_postal"));
			newUtilisateur.setVille(rs.getString("ville"));
			newUtilisateur.setMotDePasse(rs.getString("mot_de_passe"));
			newUtilisateur.setCredit(rs.getInt("credit"));
			newUtilisateur.setAdministrateur(rs.getInt("administrateur"));
			newUtilisateur.setPseudo(rs.getString("pseudo"));

			return newUtilisateur;
			
			//commentaire
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newUtilisateur;
	}

	public Utilisateur selectUtilisateur(String pseudo) throws BusinessException {
		Connection cnx = null;
		 BusinessException be = new BusinessException();

		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);

			PreparedStatement psmt = cnx.prepareStatement(SELECT_UTILISATEUR);
			psmt.setString(1, pseudo);

			ResultSet rs = psmt.executeQuery();
			Utilisateur utilisateurselect = null;

			if(rs.next()) {
				
			utilisateurselect = mappingUtilisateur(rs);
			}
			return utilisateurselect;
			
		} catch (SQLException e) {
			e.printStackTrace();
		
			throw be;
		}

	}

	private Utilisateur mappingUtilisateur(ResultSet rs) throws SQLException {
		Utilisateur newUtilisateur = new Utilisateur();
		newUtilisateur.setPseudo(rs.getString("pseudo"));
		newUtilisateur.setNom(rs.getString("nom"));
		newUtilisateur.setPrenom(rs.getString("prenom"));
		newUtilisateur.setEmail(rs.getString("email"));
		if (rs.getString("telephone") != null)
			newUtilisateur.setTelephone(rs.getString("telephone"));
		newUtilisateur.setRue(rs.getString("rue"));
		newUtilisateur.setCodePostal(rs.getString("code_postal"));
		newUtilisateur.setVille(rs.getString("ville"));

		return newUtilisateur;

	}

	public boolean delete(int nbUtilisateur) throws BusinessException{
		BusinessException be = new BusinessException();
			try {
				Connection con = ConnectionProvider.getConnection();
				con.setAutoCommit(false);
				
				PreparedStatement psmt = con.prepareStatement(DELETE);
				psmt.setInt(1, nbUtilisateur);
				int nbEnr = psmt.executeUpdate();
				if (nbEnr != 1) {
					con.rollback();
					be.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
					
				}
				con.commit();
				con.close();
				psmt.close();
				return  true;
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw be;
			}

	}
	
	public List<Article> selectAllArticles() throws BusinessException{
		
		List<Article> listeArticle = new ArrayList<Article>();
		try (Connection con = ConnectionProvider.getConnection();
				PreparedStatement psmt = con.prepareStatement(SELECT_ARTICLE);){
			BusinessException be = new BusinessException();
			ResultSet rs = psmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = mappingArticle(rs);
				listeArticle.add(articleCourant);
			}
			return listeArticle;
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_ARTICLES);
			throw be;
		}
	}

	public  Utilisateur updateUtilisateur(Utilisateur utilisateur)throws BusinessException {
		Connection cnx = null;
		 BusinessException be = new BusinessException();
		 try {
				cnx = ConnectionProvider.getConnection();
				cnx.setAutoCommit(false);

				PreparedStatement psmt = cnx.prepareStatement(UPDATE_UTILISATEUR);
				
				psmt.setString(1, utilisateur.getPseudo());
				psmt.setString(2, utilisateur.getNom());
				psmt.setString(3, utilisateur.getPrenom());
				psmt.setString(4, utilisateur.getEmail());
				if (utilisateur.getTelephone() != null) {
					psmt.setString(5, utilisateur.getTelephone());
				}

				psmt.setString(6, utilisateur.getRue());
				psmt.setString(7, utilisateur.getCodePostal());
				psmt.setString(8, utilisateur.getVille());
				psmt.setString(9, utilisateur.getMotDePasse());
				psmt.setInt(10, utilisateur.getNoUtilisateur());
				
			if(	psmt.executeUpdate()==1) {
				cnx.commit();
			}
				return utilisateur;
				
		 } catch (SQLException e) {
			 e.printStackTrace();

			 throw be;
		 }

	}
	
	public boolean delete_article(int noArticle) throws BusinessException{
		BusinessException be = new BusinessException();
		
			try {
				Connection con = ConnectionProvider.getConnection();
				con.setAutoCommit(false);
				
				PreparedStatement psmt = con.prepareStatement(DELETE_ARTICLE);
				psmt.setInt(1, noArticle);
				int nbEnr = psmt.executeUpdate();
				if (nbEnr != 1) {
					con.rollback();
					be.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
					return false;
				}
					
				PreparedStatement psm = con.prepareStatement(DELETE_RETRAIT);
				psm.setInt(1, noArticle);
				int nmbEnr = psmt.executeUpdate();
				if (nmbEnr != 1) {
					con.rollback();
					be.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
					return false;
				}
				con.commit();
				con.close();
				psmt.close();
				return  true;
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw be;
			}

	}

	public Article updateArticle(Article art, Retrait retrait)throws BusinessException{
		int noArticle;
		Connection cnx = null;
		 BusinessException be = new BusinessException();
		 try {
				cnx = ConnectionProvider.getConnection();
				cnx.setAutoCommit(false);

				PreparedStatement psmt = cnx.prepareStatement(UPDATE_ARTICLE);
				
				psmt.setString(1, art.getNomArticle());
				psmt.setString(2,art.getDescription());
				psmt.setInt(3,art.getCategorieArticle().getNoCategorie());
				psmt.setInt(4,art.getMiseAPrix());
				psmt.setDate(5,Date.valueOf(art.getDateDebutEncheres()));
				psmt.setDate(6,Date.valueOf(art.getDateFinEncheres()));
				psmt.setString(7, art.getLieuRetrait().getRue());
				psmt.setInt(7,art.getNoArticle());
				
			if(	psmt.executeUpdate()==1) {
				cnx.commit();
			}
			psmt.close();
		
			psmt=cnx.prepareStatement(UPDATE_RETRAIT);
			noArticle=art.getNoArticle();
			
			psmt.setString(1,retrait.getRue());
			psmt.setString(2,retrait.getCodePostal());
			psmt.setString(3,retrait.getVille()); 
			psmt.setInt(4, noArticle);
			psmt.executeUpdate();
			cnx.commit();
			
			return art;
		 } catch (SQLException e) {
			 e.printStackTrace();

			 throw be;
		 }
	}
}

