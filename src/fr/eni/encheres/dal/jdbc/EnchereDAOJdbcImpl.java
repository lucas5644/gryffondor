package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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

public class EnchereDAOJdbcImpl implements EnchereDAO {
	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES(nom_article,description,prix_initial,date_debut_encheres,date_fin_encheres,no_utilisateur,no_categorie,etat_vente) VALUES(?,?,?,?,?,?,?,?);";
	private static final String INSERT_LIEU_RETRAIT = "INSERT INTO RETRAITS(no_article,rue,code_postal,ville) VALUES (?,?,?,?);";
	private static final String SELECT_ARTICLE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur,a.no_categorie,a.etat_vente,c.libelle, u.pseudo,u.nom,u.prenom,u.email,u.telephone,u.rue,u.code_postal,u.ville,u.mot_de_passe,u.credit,u.pseudo,u.administrateur FROM ARTICLES a LEFT JOIN CATEGORIES c on c.no_categorie = a.no_categorie LEFT JOIN UTILISATEURS u on u.no_utilisateur = a.no_utilisateur LEFT JOIN RETRAITS r on r.no_article = a.no_article WHERE a.nom_article LIKE ? AND c.libelle LIKE ?";
	private static final String SELECT_ARTICLE_BY_ID = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur,a.no_categorie,a.etat_vente,c.libelle, u.pseudo,u.nom,u.prenom,u.email,u.telephone,u.mot_de_passe,u.credit,u.administrateur,e.date_enchere,e.montant_enchere, r.rue,r.code_postal,r.ville FROM ARTICLES a LEFT JOIN CATEGORIES c on c.no_categorie = a.no_categorie LEFT JOIN UTILISATEURS u on u.no_utilisateur = a.no_utilisateur LEFT JOIN RETRAITS r on r.no_article = a.no_article LEFT JOIN ENCHERES e on e.no_article = a.no_article WHERE a.no_article=?";
	private static final String SELECT_ARTICLE_DECONNECTE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres,a.date_fin_encheres,a.prix_initial,a.prix_vente,a.no_utilisateur,a.no_categorie,a.etat_vente,c.libelle, u.pseudo,u.nom,u.prenom,u.email,u.telephone,u.rue,u.code_postal,u.ville,u.mot_de_passe,u.credit,u.administrateur,r.rue,r.code_postal,r.ville FROM ARTICLES a LEFT JOIN CATEGORIES c on c.no_categorie = a.no_categorie LEFT JOIN UTILISATEURS u on u.no_utilisateur = a.no_utilisateur LEFT JOIN RETRAITS r on r.no_article = a.no_article WHERE a.nom_article LIKE ? AND c.libelle LIKE ?;";
	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom , email ,telephone ,rue , code_postal, ville, mot_de_passe, credit, administrateur) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
	private static final String CHECK_CONNEXION = "select no_utilisateur ,pseudo , nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, administrateur, credit from UTILISATEURS where pseudo = ? and mot_de_passe = ?;";
	private static final String CHECK_PSEUDO = "SELECT pseudo FROM UTILISATEURS WHERE pseudo = ?";
	private static final String CHECK_MAIL = "SELECT email FROM UTILISATEURS WHERE email = ?";
	private static final String SELECT_UTILISATEUR = "SELECT no_utilisateur, pseudo,nom,prenom,email,telephone,rue,code_postal,ville, credit FROM UTILISATEURS WHERE pseudo = ?";
	private static final String DELETE = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?;";
	private static final String UPDATE_UTILISATEUR = "update UTILISATEURS set pseudo = ?, nom = ?, prenom =  ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? where no_utilisateur = ?;";
	private static final String DELETE_ARTICLE = "DELETE FROM ARTICLES WHERE no_article = ?";
	private static final String DELETE_RETRAIT = "DELETE FROM RETRAITS WHERE no_article = ?";
	private static final String UPDATE_ARTICLE = "UPDATE ARTICLES set nom_article = ?,description  = ?, no_categorie  = ?,prix_initial  = ?, date_debut_encheres  = ?, date_fin_encheres  = ? where no_article = ?;";
	private static final String UPDATE_RETRAIT = "UPDATE RETRAITS set rue = ?, code_postal = ?, ville = ? where no_article = ? ;";
	private static final String UPDATE_ETAT_FIN = "update ARTICLES set etat_vente = 'Enchères terminées' where date_fin_encheres < ? and etat_vente = 'En cours';";
	private static final String UPDATE_ETAT_EN_COURS = "update ARTICLES set etat_vente = 'En cours' where date_debut_encheres = ? and etat_vente = 'Créée';";
	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere,montant_enchere) VALUES(?,?,?,?);";
	private static final String UPDATE_ENCHERE = "UPDATE ENCHERES set date_enchere = ?, montant_enchere = ? where no_article = ? AND no_utilisateur =?;";
	private static final String SELECT_ENCHERE = "SELECT no_utilisateur,no_article,date_enchere, montant_enchere FROM ENCHERES WHERE no_article = ?;";
	private static final String SELECT_UTILISATEUR_ADMIN = "SELECT no_utilisateur,pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS";
	private static final String SELECT_UTILISATEUR_BY_ID = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville,no_utilisateur,credit FROM UTILISATEURS WHERE no_utilisateur = ?";
	private static final String SELECT_ENCHERES_UTILISATEUR = "SELECT u.no_utilisateur, e.no_article, e.date_enchere, e.montant_enchere FROM UTILISATEURS u LEFT JOIN ENCHERES e on e.no_utilisateur = u.no_utilisateur WHERE e.no_utilisateur= ?";
	private static final String CHECK_ENCHERE = "SELECT e.no_utilisateur, pseudo, no_article FROM UTILISATEURS u LEFT JOIN ENCHERES e on e.no_utilisateur = u.no_utilisateur WHERE u.no_utilisateur=? AND e.no_article = ?";
	private static final String UPDATE_PRIX_VENTE_ARTICLE = "UPDATE ARTICLES set prix_vente = ? WHERE no_article = ?;";
	private static final String UPDATE_MDP = "UPDATE UTILISATEURS set mot_de_passe =? WHERE email= ?";
	private static final String UPDATE_CREDIT_USER="UPDATE UTILISATEURS set credit = ? WHERE no_utilisateur = ?;";
	private static final String SELECT_MEILLEURE_ENCHERE = "SELECT TOP(1) no_utilisateur,no_article, date_enchere, MAX(montant_enchere) as montant_enchere FROM ENCHERES WHERE no_article = ? GROUP BY no_article,no_utilisateur,date_enchere ORDER BY montant_enchere DESC;"; 
	
	public Enchere selectMeilleureEnchere(int numeroArticle) throws BusinessException {
		Enchere meilleureEnchere = new Enchere();
		Utilisateur meilleureEncherisseur = new Utilisateur();
		Article articleCourant = new Article();
		try {
			Connection con = null;
			con = ConnectionProvider.getConnection();
			PreparedStatement psmt = con.prepareStatement(SELECT_MEILLEURE_ENCHERE);
			// insertion des données
			psmt.setInt(1, numeroArticle);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				meilleureEncherisseur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				meilleureEncherisseur = selectUtilisateurById(meilleureEncherisseur.getNoUtilisateur());
				meilleureEnchere.setUtilisateur(meilleureEncherisseur);
				meilleureEnchere.setMontantEnchere(rs.getInt("montant_enchere"));
				meilleureEnchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				articleCourant = selectArticleById(numeroArticle);
				meilleureEnchere.setArticle(articleCourant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.UPDATE_ENCHERE);
			throw be;
		}
		return meilleureEnchere;
	}
	
	public void updateCreditAncienEncherisseur(String pseudo, int montantEnchere) throws BusinessException {
		Utilisateur utilisateurCourant = new Utilisateur();
		utilisateurCourant = selectUtilisateur(pseudo);
		try {
			Connection con = null;
			con = ConnectionProvider.getConnection();
			PreparedStatement psmt = con.prepareStatement(UPDATE_CREDIT_USER);
			// insertion des données
			psmt.setInt(1, (utilisateurCourant.getCredit() + montantEnchere));
			psmt.setInt(2, utilisateurCourant.getNoUtilisateur());
			if (psmt.executeUpdate() == 1) {
				con.commit();
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.UPDATE_ENCHERE);
			throw be;
		}
	}
	
	public void updateCreditNouveauEncherisseur(String pseudo, int montantEnchere) throws BusinessException {
		Utilisateur utilisateurCourant = new Utilisateur();
		utilisateurCourant = selectUtilisateur(pseudo);
		try {
			Connection con = null;
			con = ConnectionProvider.getConnection();
			PreparedStatement psmt = con.prepareStatement(UPDATE_CREDIT_USER);
			// insertion des données
			psmt.setInt(1, (utilisateurCourant.getCredit() - montantEnchere));
			psmt.setInt(2, utilisateurCourant.getNoUtilisateur());
			if (psmt.executeUpdate() == 1) {
				con.commit();
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.UPDATE_ENCHERE);
			throw be;		}
	}
	
	public void updatePrixVente(int numeroArticle, int montantEnchere) throws BusinessException {
		Article articleNewPrix = new Article();
		articleNewPrix = selectArticleById(numeroArticle);

		try {
			Connection con = null;
			con = ConnectionProvider.getConnection();
			PreparedStatement psmt = con.prepareStatement(UPDATE_PRIX_VENTE_ARTICLE);
			// insertion des données
			psmt.setInt(1, montantEnchere);
			psmt.setInt(2, numeroArticle);
			if (psmt.executeUpdate() == 1) {
				con.commit();
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.UPDATE_ENCHERE);
			throw be;
		}
	}

	public boolean checkEnchere(int numeroArticle, int numeroUtilisateur) throws BusinessException {
		boolean resultat = false;
		try {
			Connection con = null;
			con = ConnectionProvider.getConnection();
			PreparedStatement psmt = con.prepareStatement(CHECK_ENCHERE);
			// insertion des données
			psmt.setInt(1, numeroUtilisateur);
			psmt.setInt(2, numeroArticle);
			ResultSet rs = psmt.executeQuery();
			// recherche de l'enchérisseur
			while (rs.next()) {
				resultat = true;

			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.CHECK_ENCHERE);
			throw be;
		}
		return resultat;
	}

	public List<Enchere> selectEncheresUtilisateur(int noUtilisateur) throws BusinessException {

		Connection con = null;
		List<Enchere> maListe = new ArrayList<Enchere>();

		try {
			con = ConnectionProvider.getConnection();
			PreparedStatement psmt = con.prepareStatement(SELECT_ENCHERES_UTILISATEUR);
			// Insertion des données
			psmt.setInt(1, noUtilisateur);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				maListe.add(mappingEnchere(rs, noUtilisateur));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			throw be;
		}
		return maListe;
	}

	private Enchere mappingEnchere(ResultSet rs, int idUser) throws BusinessException {
		Enchere monEnchere = new Enchere();
		try {
			Utilisateur encherisseur = selectUtilisateurById(idUser);
			Article monArticle = selectArticleById((rs.getInt("no_article")));
			monEnchere.setArticle(monArticle);
			monEnchere.setUtilisateur(encherisseur);
			monEnchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
			monEnchere.setMontantEnchere(rs.getInt("montant_enchere"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return monEnchere;
	}

	public Utilisateur selectUtilisateurById(int id) throws BusinessException {
		Connection cnx = null;
		BusinessException be = new BusinessException();
		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);

			PreparedStatement psmt = cnx.prepareStatement(SELECT_UTILISATEUR_BY_ID);
			psmt.setInt(1, id);

			ResultSet rs = psmt.executeQuery();
			Utilisateur utilisateurselect = null;

			if (rs.next()) {

				utilisateurselect = mappingUtilisateur(rs);
			}
			return utilisateurselect;

		} catch (SQLException e) {
			e.printStackTrace();

			throw be;
		}

	}

	public Enchere selectEnchere(int numeroArticle) throws BusinessException {
		Enchere enchereCourante = new Enchere();
		Utilisateur utilisateurCourant = new Utilisateur();
		Article articleCourant = new Article();

		try {
			Connection con = null;
			con = ConnectionProvider.getConnection();
			con.setAutoCommit(false);
			PreparedStatement psmt = con.prepareStatement(SELECT_ENCHERE);
			// insertion des données
			psmt.setInt(1, numeroArticle);
			ResultSet rs = psmt.executeQuery();
			// Recherche de l'enchère
			while (rs.next()) {
				if (rs.getInt("no_article") == numeroArticle) {
					utilisateurCourant.setNoUtilisateur(rs.getInt("no_utilisateur"));
					articleCourant.setNoArticle(rs.getInt("no_article"));
					enchereCourante.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
					enchereCourante.setMontantEnchere(rs.getInt("montant_enchere"));
					enchereCourante.setArticle(articleCourant);
					enchereCourante.setUtilisateur(utilisateurCourant);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE);
			throw be;
		}
		return enchereCourante;
	}

	public Enchere updateEnchere(String pseudoUser, int numeroArticle, int montantEnchere) throws BusinessException {
		Enchere newEnchere = new Enchere();
		LocalDate now = LocalDate.now();
		Article articleCourant = null;
		Utilisateur encherisseur = null;
		encherisseur = selectUtilisateur(pseudoUser);
		newEnchere.setUtilisateur(encherisseur);
		articleCourant = selectArticleById(numeroArticle);
		newEnchere.setArticle(articleCourant);
		newEnchere.setDateEnchere(now);
		newEnchere.setMontantEnchere(montantEnchere);
		try {
			Connection con = null;
			con = ConnectionProvider.getConnection();
			con.setAutoCommit(false);
			PreparedStatement psmt = con.prepareStatement(UPDATE_ENCHERE);
			// insertion des données
			psmt.setDate(1, Date.valueOf(newEnchere.getDateEnchere()));
			psmt.setInt(2, newEnchere.getMontantEnchere());
			psmt.setInt(3, articleCourant.getNoArticle());
			psmt.setInt(4, encherisseur.getNoUtilisateur());
			if (psmt.executeUpdate() == 1) {
				con.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.UPDATE_ENCHERE);
			throw be;
		}
		return newEnchere;
	}

	public Enchere insertEnchere(String pseudoUser, int numeroArticle, int montantEnchere) throws BusinessException {
		Enchere newEnchere = new Enchere();
		LocalDate now = LocalDate.now();
		Article articleCourant = null;
		Utilisateur encherisseur = null;
		encherisseur = selectUtilisateur(pseudoUser);
		newEnchere.setUtilisateur(encherisseur);
		articleCourant = selectArticleById(numeroArticle);
		newEnchere.setArticle(articleCourant);
		newEnchere.setDateEnchere(now);
		newEnchere.setMontantEnchere(montantEnchere);

		try {
			Connection con = null;
			con = ConnectionProvider.getConnection();
			con.setAutoCommit(false);
			PreparedStatement psmt = con.prepareStatement(INSERT_ENCHERE);
			newEnchere.setMontantEnchere(montantEnchere);
			// insertion des données
			psmt.setInt(1, encherisseur.getNoUtilisateur());
			psmt.setInt(2, articleCourant.getNoArticle());
			psmt.setDate(3, Date.valueOf(newEnchere.getDateEnchere()));
			psmt.setInt(4, newEnchere.getMontantEnchere());
			if (psmt.executeUpdate() == 1) {
				con.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.INSERT_ENCHERE);
			throw be;
		}
		return newEnchere;
	}

	public void updateEtatVente() throws BusinessException {
		Connection cnx = null;
		BusinessException be = new BusinessException();
		Date JourFin = Date.valueOf(LocalDate.now().plusDays(1));
		Date JourDebutEnchere = Date.valueOf(LocalDate.now());
		String JourFinal = String.valueOf(JourFin);
		String JourDeb = String.valueOf(JourDebutEnchere);
		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);
			PreparedStatement psmt1 = cnx.prepareStatement(UPDATE_ETAT_EN_COURS);
			psmt1.setString(1, JourDeb);
			psmt1.executeUpdate();
			psmt1.close();
			PreparedStatement psmt = cnx.prepareStatement(UPDATE_ETAT_FIN);
			psmt.setString(1, JourFinal);
			psmt.executeUpdate();
			psmt.close();
			cnx.commit();
			try {
				cnx.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw be;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw be;
		}
	}

	public Article selectArticleById(int numeroArticle) throws BusinessException {
		Article articleCourant = new Article();
		Retrait lieuRetrait = new Retrait();
		Utilisateur user = new Utilisateur();
		int nombreEnregistrement = 0;
		Connection con = null;

		try {
			con = ConnectionProvider.getConnection();
			con.setAutoCommit(false);
			PreparedStatement psmt = con.prepareStatement(SELECT_ARTICLE_BY_ID);
			// Insertion des données
			psmt.setInt(1, numeroArticle);
			ResultSet rs = psmt.executeQuery();

			while (rs.next()) {
				if (nombreEnregistrement == 0) {
					mappingArticle(rs);
					lieuRetrait = mappingRetrait(rs);
					user = mappingUser(rs);
					articleCourant = mappingArticle(rs);
					articleCourant.setVendeur(user);
					articleCourant.setLieuRetrait(lieuRetrait);
					nombreEnregistrement++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_ARTICLE_DECONNECTE);
			throw be;
		}
		return articleCourant;
	}

	public int insertArticle(Article article, Retrait retrait) throws BusinessException {
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
			// Insertion des données du lieu de retrait
			psmt.setInt(1, numeroArticle);
			psmt.setString(2, retrait.getRue());
			psmt.setString(3, retrait.getCodePostal());
			psmt.setString(4, retrait.getVille());
			psmt.executeUpdate();
			psmt.close();

			con.commit();
			return numeroArticle;
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
		return -1;
	}

	private Retrait mappingRetrait(ResultSet rs) throws SQLException {
		Retrait newLieuRetrait = new Retrait();
		newLieuRetrait.setRue(rs.getString("rue"));
		newLieuRetrait.setCodePostal(rs.getString("code_postal"));
		newLieuRetrait.setVille(rs.getString("ville"));
		return newLieuRetrait;
	}

	private Article mappingArticle(ResultSet rs) throws SQLException {

		Article newArticle = new Article();
		Categorie newCategorie = new Categorie();
		newArticle.setNoArticle(rs.getInt("no_article"));
		newArticle.setNomArticle(rs.getString("nom_article"));
		newArticle.setDescription(rs.getString("description"));
		newArticle.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
		newArticle.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
		newArticle.setMiseAPrix(rs.getInt("prix_initial"));
		newArticle.setPrixVente(rs.getInt("prix_vente"));
		newCategorie.setNoCategorie(rs.getInt("no_categorie"));
		newArticle.setEtatVente(rs.getString("etat_vente"));
		newCategorie.setLibelle(rs.getString("libelle"));
		newArticle.setCategorieArticle(newCategorie);
		return newArticle;
	}

	@Override
	public List<Article> selectArticleDeconnecte(String nomCategorie, String nomArticle) throws BusinessException {
		List<Article> listeArticleDeconnecte = new ArrayList<Article>();
		Utilisateur user = new Utilisateur();
		try (Connection con = ConnectionProvider.getConnection();
				PreparedStatement psmt = con.prepareStatement(SELECT_ARTICLE_DECONNECTE);) {
			BusinessException be = new BusinessException();
			psmt.setString(1, "%" + nomArticle + "%");
			psmt.setString(2, "%" + nomCategorie + "%");
			ResultSet rs = psmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				user = mappingUser(rs);
				articleCourant = mappingArticle(rs);
				articleCourant.setVendeur(user);
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

			// commentaire
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

			if (rs.next()) {

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
		newUtilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
		newUtilisateur.setPseudo(rs.getString("pseudo"));
		newUtilisateur.setNom(rs.getString("nom"));
		newUtilisateur.setPrenom(rs.getString("prenom"));
		newUtilisateur.setEmail(rs.getString("email"));
		if (rs.getString("telephone") != null)
			newUtilisateur.setTelephone(rs.getString("telephone"));
		newUtilisateur.setRue(rs.getString("rue"));
		newUtilisateur.setCodePostal(rs.getString("code_postal"));
		newUtilisateur.setVille(rs.getString("ville"));
		newUtilisateur.setCredit(rs.getInt("credit"));

		return newUtilisateur;

	}

	public boolean delete(int nbUtilisateur) throws BusinessException {
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
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw be;
		}

	}

	public List<Article> selectAllArticles(String nomCategorie, String nomArticle) throws BusinessException {

		List<Article> listeArticle = new ArrayList<Article>();
		try (Connection con = ConnectionProvider.getConnection();
				PreparedStatement psmt = con.prepareStatement(SELECT_ARTICLE);) {
			BusinessException be = new BusinessException();
			psmt.setString(1, "%" + nomArticle + "%");
			psmt.setString(2, "%" + nomCategorie + "%");
			ResultSet rs = psmt.executeQuery();
			Article articleCourant = new Article();
			Utilisateur user = new Utilisateur();
			while (rs.next()) {
				String pseudo = rs.getString("pseudo");
				user = selectUtilisateur(pseudo);
				articleCourant = mappingArticle(rs);
				articleCourant.setVendeur(user);
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

	public Utilisateur updateUtilisateur(Utilisateur utilisateur) throws BusinessException {
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

			if (psmt.executeUpdate() == 1) {
				cnx.commit();
			}
			return utilisateur;

		} catch (SQLException e) {
			e.printStackTrace();

			be.ajouterErreur(CodesResultatDAL.UPDATE_UTILISATEUR);
			throw be;
		}

	}

	public boolean delete_article(int noArticle) throws BusinessException {
		BusinessException be = new BusinessException();

		try {
			Connection con = ConnectionProvider.getConnection();
			con.setAutoCommit(false);
			// exécution de la première requête -- suppression du retrait
			PreparedStatement psmt = con.prepareStatement(DELETE_RETRAIT);
			psmt.setInt(1, noArticle);
			int nmbEnr = psmt.executeUpdate();
			if (nmbEnr != 1) {
				con.rollback();
				be.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
				return false;
			}
			psmt.close();

			// exécution de la deuxième requête -- suppression de l'article
			psmt = con.prepareStatement(DELETE_ARTICLE);
			psmt.setInt(1, noArticle);
			int nbEnr = psmt.executeUpdate();
			if (nbEnr != 1) {
				con.rollback();
				be.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
				return false;
			}
			psmt.close();
			con.commit();
			con.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw be;
		}

	}

	public Article updateArticle(Article art, Retrait retrait) throws BusinessException {
		int noArticle;
		Connection cnx = null;
		BusinessException be = new BusinessException();
		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);

			PreparedStatement psmt = cnx.prepareStatement(UPDATE_ARTICLE);

			psmt.setString(1, art.getNomArticle());
			psmt.setString(2, art.getDescription());
			psmt.setInt(3, art.getCategorieArticle().getNoCategorie());
			psmt.setInt(4, art.getMiseAPrix());
			psmt.setDate(5, Date.valueOf(art.getDateDebutEncheres()));
			psmt.setDate(6, Date.valueOf(art.getDateFinEncheres()));
			psmt.setString(7, art.getLieuRetrait().getRue());
			psmt.setInt(7, art.getNoArticle());

			if (psmt.executeUpdate() == 1) {
				cnx.commit();
			}
			psmt.close();

			psmt = cnx.prepareStatement(UPDATE_RETRAIT);
			noArticle = art.getNoArticle();

			psmt.setString(1, retrait.getRue());
			psmt.setString(2, retrait.getCodePostal());
			psmt.setString(3, retrait.getVille());
			psmt.setInt(4, noArticle);
			psmt.executeUpdate();
			cnx.commit();

			return art;
		} catch (SQLException e) {
			e.printStackTrace();

			throw be;
		}
	}

	public List<Utilisateur> selectUtilisateurPourAdmin() throws BusinessException {
		Utilisateur user = new Utilisateur();
		List<Utilisateur> listeUtilisateur = new ArrayList<Utilisateur>();
		Connection cnx = null;
		BusinessException be = new BusinessException();

		try {
			cnx = ConnectionProvider.getConnection();

			PreparedStatement psmt = cnx.prepareStatement(SELECT_UTILISATEUR_ADMIN);
			{
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					user = mappingUser(rs);
					listeUtilisateur.add(user);
				}
			}
			cnx.close();
			psmt.close();
			return listeUtilisateur;
		} catch (SQLException e) {
			e.printStackTrace();

			throw be;
		}
	}

	public Utilisateur updateMDP(Utilisateur user) throws BusinessException {
		
		Connection cnx = null;
		BusinessException be = new BusinessException();
		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);

			PreparedStatement psmt = cnx.prepareStatement(UPDATE_MDP);

			psmt.setString(1, user.getMotDePasse());
			psmt.setString(2, user.getEmail());

			psmt.executeUpdate();
			cnx.commit();
			psmt.close();
			cnx.close();
			
			return user;

		} catch (SQLException e) {
			e.printStackTrace();

			 be.ajouterErreur(CodesResultatDAL.UPDATE_MDP);
			throw be;
		}

	}

}
