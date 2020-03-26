package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.util.List;

public class ArticleVendu {
	
	private int noArticle;
	private String nomArticle;
	private String description;
	private int dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private int etatVente;
	
	//Association
	private List<Enchere> listEncheres;
}
