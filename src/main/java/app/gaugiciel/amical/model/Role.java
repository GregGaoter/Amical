package app.gaugiciel.amical.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public abstract class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(length = 64)
	private String role;
	@ManyToMany(mappedBy = "listeRoles")
	@NotNull
	private Set<Authentification> listeAuthentifications;

	public Role() {
		super();
	}

	public Role(@NotNull String role, @NotNull Set<Authentification> listeAuthentifications) {
		super();
		this.role = role;
		this.listeAuthentifications = listeAuthentifications;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Authentification> getListeAuthentifications() {
		return listeAuthentifications;
	}

	public void setListeAuthentifications(Set<Authentification> listeAuthentifications) {
		this.listeAuthentifications = listeAuthentifications;
	}

}
