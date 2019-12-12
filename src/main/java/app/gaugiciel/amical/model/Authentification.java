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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

// Persistance
@Entity
// Lombok
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Data
public class Authentification implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@Column(nullable = false, length = 64)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Email(message = "{validation.email}")
	@Size(max = 64, message = "{validation.size.max}")
	// Lombok
	@NonNull
	@ToString.Include
	private String email;

	// Persistance
	@Column(name = "mot_de_passe", nullable = false, length = 128)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 8, max = 128, message = "{validation.size.interval}")
	// Lombok
	@NonNull
	private String motDePasse;

	// Persistance
	@Column(name = "actif_q", nullable = false)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	// Lombok
	@NonNull
	@ToString.Include
	private Boolean actifQ;

	// Persistance
	@ManyToMany
	@JoinTable(name = "profil", joinColumns = @JoinColumn(name = "authentification_email"), inverseJoinColumns = @JoinColumn(name = "role_role"))
	private Set<Role> listeRoles;

	// Persistance
	@OneToOne(mappedBy = "authentification", cascade = CascadeType.ALL)
	private Utilisateur utilisateur;

}
