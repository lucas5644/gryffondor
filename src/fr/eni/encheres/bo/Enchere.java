package fr.eni.encheres.bo;

import java.time.LocalDate;

public class Enchere {
	
	//ATTRIBUTS
	private LocalDate dateEnchere;
	private int montantEnchere;
	
	//ASSOCIATIONS
	private Article article;
	private Utilisateur utilisateur;
	
	public Enchere() {
		// TODO Auto-generated constructor stub
	}

	public Enchere(LocalDate dateEnchere, int montantEnchere, Article article, Utilisateur utilisateur) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.article = article;
		this.utilisateur = utilisateur;
	}

	public LocalDate getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDate dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result + ((dateEnchere == null) ? 0 : dateEnchere.hashCode());
		result = prime * result + montantEnchere;
		result = prime * result + ((utilisateur == null) ? 0 : utilisateur.hashCode());
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
		Enchere other = (Enchere) obj;
		if (article == null) {
			if (other.article != null)
				return false;
		} else if (!article.equals(other.article))
			return false;
		if (dateEnchere == null) {
			if (other.dateEnchere != null)
				return false;
		} else if (!dateEnchere.equals(other.dateEnchere))
			return false;
		if (montantEnchere != other.montantEnchere)
			return false;
		if (utilisateur == null) {
			if (other.utilisateur != null)
				return false;
		} else if (!utilisateur.equals(other.utilisateur))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Enchere [dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere + ", article=" + article
				+ ", utilisateur=" + utilisateur + "]";
	}
	
}
