package app.gaugiciel.amical.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public abstract class Utilisateur implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name = "authentification_email", length = 64)
	private String authentificationEmail;
	@NotNull
	@Column(length = 64)
	private String prenom;
	@NotNull
	@Column(length = 64)
	private String nom;
	@OneToOne
	@MapsId
	private Authentification authentification;

	public Utilisateur() {
		super();
	}

	public Utilisateur(@NotNull String authentificationEmail, @NotNull String prenom, @NotNull String nom,
			Authentification authentification) {
		super();
		this.authentificationEmail = authentificationEmail;
		this.prenom = prenom;
		this.nom = nom;
		this.authentification = authentification;
	}

	public String getAuthentificationEmail() {
		return authentificationEmail;
	}

	public void setAuthentificationEmail(String authentificationEmail) {
		this.authentificationEmail = authentificationEmail;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Authentification getAuthentification() {
		return authentification;
	}

	public void setAuthentification(Authentification authentification) {
		this.authentification = authentification;
	}

}
