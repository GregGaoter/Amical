package fr.lesamisdelescalade.persistance.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	private String email;
	@NotNull
	@Column(name = "mot_de_passe")
	private String motDePasse;
	@NotNull
	private String prenom;
	@NotNull
	private String nom;
	@NotNull
	@Column(name = "actif_q")
	private Boolean actifQ;
	@NotNull
	@ManyToMany
	@JoinTable(name = "profil")
	private Set<Role> listeRoles;

	public Utilisateur(@NotNull String email, @NotNull String motDePasse, @NotNull String prenom, @NotNull String nom,
			@NotNull Boolean actifQ, @NotNull Set<Role> listeRoles) {
		super();
		this.email = email;
		this.motDePasse = motDePasse;
		this.prenom = prenom;
		this.nom = nom;
		this.actifQ = actifQ;
		this.listeRoles = listeRoles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
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

	public Boolean getActifQ() {
		return actifQ;
	}

	public void setActifQ(Boolean actifQ) {
		this.actifQ = actifQ;
	}

	public Set<Role> getListeRoles() {
		return listeRoles;
	}

	public void setListeRoles(Set<Role> listeRoles) {
		this.listeRoles = listeRoles;
	}

}
