package fr.eni.encheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
	
	private int noUtilisateur;
	private String pseudo;
    private String nom;            
    private String prenom;         
    private String email;          
    private String telephone;      
    private String rue;            
    private String code_postal;    
    private String ville;          
    private String mot_de_passe;   
    private int credit;         
    private boolean administrateur;
    
    //Association
    private List<Enchere> listEncheres;
    private List<ArticleVendu> listAchat;
    private List<ArticleVendu> listVente;
    
    public Utilisateur() {
		listEncheres = new ArrayList<Enchere>();
		listAchat = new ArrayList<ArticleVendu>();
		listVente = new ArrayList<ArticleVendu>();
	}
    
    public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String code_postal, String ville, String mot_de_passe, int credit, boolean administrateur, List<Enchere> listEncheres, List<ArticleVendu> listAchat, List<ArticleVendu> listVente) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
		this.mot_de_passe = mot_de_passe;
		this.credit = credit;
		this.administrateur = administrateur;
		this.listEncheres = listEncheres;
		this.listAchat = listAchat;
		this.listVente = listVente;
	}

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String code_postal, String ville, String mot_de_passe, int credit, boolean administrateur, List<Enchere> listEncheres, List<ArticleVendu> listAchat, List<ArticleVendu> listVente) {
		this(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur, listEncheres, listAchat, listVente);
		this.noUtilisateur = noUtilisateur;
	}

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

	public List<Enchere> getListEncheres() {
		return listEncheres;
	}

	public void setListEncheres(List<Enchere> listEncheres) {
		this.listEncheres = listEncheres;
	}

	public List<ArticleVendu> getListAchat() {
		return listAchat;
	}

	public void setListAchat(List<ArticleVendu> listAchat) {
		this.listAchat = listAchat;
	}

	public List<ArticleVendu> getListVente() {
		return listVente;
	}

	public void setListVente(List<ArticleVendu> listVente) {
		this.listVente = listVente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + noUtilisateur;
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
		Utilisateur other = (Utilisateur) obj;
		if (noUtilisateur != other.noUtilisateur)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Utilisateur [noUtilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", code_postal="
				+ code_postal + ", ville=" + ville + ", mot_de_passe=" + mot_de_passe + ", credit=" + credit
				+ ", administrateur=" + administrateur + ", listEncheres=" + listEncheres + "]";
	}
}
