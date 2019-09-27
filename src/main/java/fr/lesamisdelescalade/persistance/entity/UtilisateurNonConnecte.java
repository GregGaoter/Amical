package fr.lesamisdelescalade.persistance.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "utilisateur_non_connecte")
@PrimaryKeyJoinColumn(name = "utilisateur_email")
public class UtilisateurNonConnecte extends Utilisateur implements Serializable {

	private static final long serialVersionUID = 1L;

	public UtilisateurNonConnecte() {
		super();
	}

	public UtilisateurNonConnecte(@NotNull String email, @NotNull String motDePasse, @NotNull String prenom,
			@NotNull String nom, @NotNull Boolean actifQ, @NotNull Set<Role> listeRoles) {
		super(email, motDePasse, prenom, nom, actifQ, listeRoles);
	}
}
