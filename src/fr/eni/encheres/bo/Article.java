package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Article {
	//ATTRIBUTS
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private String etatVente;
	//ASSOCIATIONS
	private Utilisateur vendeur;
	private List<Utilisateur> listeAcheteurs;
	private Retrait lieuRetrait;
	private Categorie categorieArticle;

	public Article() {
		// TODO Auto-generated constructor stub
	}
	

	public Article(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, int miseAPrix, int prixVente, String etatVente, Utilisateur vendeur,
			List<Utilisateur> listeAcheteurs, Retrait lieuRetrait, Categorie categorieArticle) {
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.vendeur = vendeur;
		this.listeAcheteurs = listeAcheteurs;
		this.lieuRetrait = lieuRetrait;
		this.categorieArticle = categorieArticle;
	}

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public String getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public List<Utilisateur> getListeAcheteurs() {
		return listeAcheteurs;
	}

	public void setListeAcheteurs(List<Utilisateur> listeAcheteurs) {
		this.listeAcheteurs = listeAcheteurs;
	}

	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}

	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}

	public Categorie getCategorieArticle() {
		return categorieArticle;
	}

	public void setCategorieArticle(Categorie categorieArticle) {
		this.categorieArticle = categorieArticle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categorieArticle == null) ? 0 : categorieArticle.hashCode());
		result = prime * result + ((dateDebutEncheres == null) ? 0 : dateDebutEncheres.hashCode());
		result = prime * result + ((dateFinEncheres == null) ? 0 : dateFinEncheres.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((etatVente == null) ? 0 : etatVente.hashCode());
		result = prime * result + ((lieuRetrait == null) ? 0 : lieuRetrait.hashCode());
		result = prime * result + ((listeAcheteurs == null) ? 0 : listeAcheteurs.hashCode());
		result = prime * result + miseAPrix;
		result = prime * result + noArticle;
		result = prime * result + ((nomArticle == null) ? 0 : nomArticle.hashCode());
		result = prime * result + prixVente;
		result = prime * result + ((vendeur == null) ? 0 : vendeur.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (categorieArticle == null) {
			if (other.categorieArticle != null)
				return false;
		} else if (!categorieArticle.equals(other.categorieArticle))
			return false;
		if (dateDebutEncheres == null) {
			if (other.dateDebutEncheres != null)
				return false;
		} else if (!dateDebutEncheres.equals(other.dateDebutEncheres))
			return false;
		if (dateFinEncheres == null) {
			if (other.dateFinEncheres != null)
				return false;
		} else if (!dateFinEncheres.equals(other.dateFinEncheres))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (etatVente == null) {
			if (other.etatVente != null)
				return false;
		} else if (!etatVente.equals(other.etatVente))
			return false;
		if (lieuRetrait == null) {
			if (other.lieuRetrait != null)
				return false;
		} else if (!lieuRetrait.equals(other.lieuRetrait))
			return false;
		if (listeAcheteurs == null) {
			if (other.listeAcheteurs != null)
				return false;
		} else if (!listeAcheteurs.equals(other.listeAcheteurs))
			return false;
		if (miseAPrix != other.miseAPrix)
			return false;
		if (noArticle != other.noArticle)
			return false;
		if (nomArticle == null) {
			if (other.nomArticle != null)
				return false;
		} else if (!nomArticle.equals(other.nomArticle))
			return false;
		if (prixVente != other.prixVente)
			return false;
		if (vendeur == null) {
			if (other.vendeur != null)
				return false;
		} else if (!vendeur.equals(other.vendeur))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Article [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix="
				+ miseAPrix + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", vendeur=" + vendeur
				+ ", listeAcheteurs=" + listeAcheteurs + ", lieuRetrait=" + lieuRetrait + ", categorieArticle="
				+ categorieArticle + "]";
	}
	
	
	
}
