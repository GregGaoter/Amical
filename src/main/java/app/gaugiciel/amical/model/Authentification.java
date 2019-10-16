package app.gaugiciel.amical.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Authentification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(length = 64)
	private String email;
	@NotNull
	@Column(name = "mot_de_passe", length = 128)
	private String motDePasse;
	@NotNull
	@Column(name = "actif_q")
	private boolean actifQ;
	@ManyToMany
	@NotNull
	@JoinTable(name = "profil", joinColumns = @JoinColumn(name = "authentification_email"), inverseJoinColumns = @JoinColumn(name = "role_role"))
	private Set<Role> listeRoles;
	@OneToOne(mappedBy = "authentification", cascade = CascadeType.ALL)
	private Utilisateur utilisateur;

	public Authentification() {
		super();
	}

	public Authentification(@NotNull String motDePasse, @NotNull boolean actifQ, @NotNull Set<Role> listeRoles,
			Utilisateur utilisateur) {
		super();
		this.motDePasse = motDePasse;
		this.actifQ = actifQ;
		this.listeRoles = listeRoles;
		this.utilisateur = utilisateur;
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

	public boolean isActifQ() {
		return actifQ;
	}

	public void setActifQ(boolean actifQ) {
		this.actifQ = actifQ;
	}

	public Set<Role> getListeRoles() {
		return listeRoles;
	}

	public void setListeRoles(Set<Role> listeRoles) {
		this.listeRoles = listeRoles;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
