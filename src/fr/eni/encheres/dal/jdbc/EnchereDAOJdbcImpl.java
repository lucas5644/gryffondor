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
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.exception.BusinessException;

public class EnchereDAOJdbcImpl implements EnchereDAO {
	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES(nom_article,description,date_debut_encheres,date_fin_encheres,no_utilisateur,no_categorie) VALUES(?,?,?,?,?,?);";
	private static final String SELECT_ARTICLE_DECONNECTE = "SELECT a.nom_article, a.prix_initial, a.date_fin_encheres, u.pseudo FROM ARTICLES a INNER JOIN CATEGORIES c on c.no_categorie = a.no_categorie INNER JOIN UTILISATEURS u on u.no_utilisateur = a.no_utilisateur WHERE a.nom_article LIKE ? AND c.libelle LIKE ?;";
	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom , email ,telephone ,rue , code_postal, ville, mot_de_passe, credit, administrateur) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
	private static final String CHECK_CONNEXION = "select no_utilisateur ,pseudo , nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, administrateur, credit from UTILISATEURS where pseudo = ? and mot_de_passe = ?;";
	private static final String CHECK_PSEUDO = "SELECT pseudo FROM UTILISATEURS WHERE pseudo = ?";
	private static final String CHECK_MAIL = "SELECT email FROM UTILISATEURS WHERE email = ?";

	public void insertArticle(Article article) throws BusinessException {
		Connection con = null;
		BusinessException be = new BusinessException();

		try {
			// Ouverture de la connexion
			con = ConnectionProvider.getConnection();
			con.setAutoCommit(false);
			PreparedStatement psmt = con.prepareStatement(INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS);
			// Insertion des données
			psmt.setString(1, article.getNomArticle());
			psmt.setString(2, article.getDescription());
			psmt.setDate(3, Date.valueOf(article.getDateDebutEncheres()));
			psmt.setDate(4, Date.valueOf(article.getDateFinEncheres()));
			psmt.setInt(5, article.getVendeur().getNoUtilisateur());
			psmt.setInt(6, article.getCategorieArticle().getNoCategorie());
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
		newArticle.setNomArticle(rs.getString("nom_article"));
		newArticle.setPrixVente(rs.getInt("prix_initial"));
		newArticle.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
		newArticle.getVendeur().setPseudo(rs.getString("pseudo"));
		return newArticle;
	}

	@Override
	public List<Article> selectArticleDeconnecte(String nomCategorie, String nomArticle) throws BusinessException {
		List<Article> listeArticleDeconnecte = new ArrayList<Article>();
		try {
			Connection con = null;
			BusinessException be = new BusinessException();
			// Ouverture de la connexion
			con = ConnectionProvider.getConnection();
			con.setAutoCommit(false);
			PreparedStatement psmt = con.prepareStatement(SELECT_ARTICLE_DECONNECTE);
			psmt.setString(1, nomArticle);
			psmt.setString(2, nomCategorie);
			ResultSet rs = psmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = mappingArticle(rs);
				listeArticleDeconnecte.add(articleCourant);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newUtilisateur;
	}

}
