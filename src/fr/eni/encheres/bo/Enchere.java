package fr.eni.encheres.bo;

import java.time.LocalDate;

public class Enchere {
	private LocalDate dateEnchere;
	private int montantEnchere;
	
	ArticleVendu article;
	
	public Enchere() {
		// TODO Auto-generated constructor stub
	}

	public Enchere(LocalDate dateEnchere, int montantEnchere) {
		super();
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateEnchere == null) ? 0 : dateEnchere.hashCode());
		result = prime * result + montantEnchere;
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
		if (dateEnchere == null) {
			if (other.dateEnchere != null)
				return false;
		} else if (!dateEnchere.equals(other.dateEnchere))
			return false;
		if (montantEnchere != other.montantEnchere)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Enchere [dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere + "]";
	}
	
	

}
