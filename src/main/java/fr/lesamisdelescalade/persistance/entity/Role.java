package fr.lesamisdelescalade.persistance.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public abstract class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	private String role;
	@NotNull
	@ManyToMany(mappedBy = "listeRoles")
	private Set<Utilisateur> listeUtilisateurs;

	public Role(@NotNull String role, @NotNull Set<Utilisateur> listeUtilisateurs) {
		super();
		this.role = role;
		this.listeUtilisateurs = listeUtilisateurs;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Utilisateur> getListeUtilisateurs() {
		return listeUtilisateurs;
	}

	public void setListeUtilisateurs(Set<Utilisateur> listeUtilisateurs) {
		this.listeUtilisateurs = listeUtilisateurs;
	}

}
