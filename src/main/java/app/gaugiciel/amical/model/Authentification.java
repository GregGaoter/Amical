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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Persistance
@Entity
// Lombok
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Setter
@RequiredArgsConstructor(staticName = "creer")
// @EqualsAndHashCode
public class Authentification implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MOT_DE_PASSE = "motDePasse";
	public static final String UTILISATEUR = "utilisateur";
	public static final String ACTIF_Q = "actifQ";
	public static final String EMAIL = "email";
	public static final String LISTE_ROLES = "listeRoles";

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
	// length = 60 because BCrypt algorithm generates a String of length 60
	@Column(name = "mot_de_passe", nullable = false, length = 60)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 8, max = 60, message = "{validation.size.interval}")
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
	// Lombok
	@NonNull
	private Set<Role> listeRoles;

	// Persistance
	@OneToOne(mappedBy = "authentification", cascade = CascadeType.ALL)
	private Utilisateur utilisateur;

	// Persistance
	// @OneToMany(mappedBy = "preteur", fetch = FetchType.LAZY)
	// Set<PretEmpruntManuel> listePretsManuels = new HashSet<>();

	// Persistance
	// @OneToMany(mappedBy = "emprunteur", fetch = FetchType.LAZY)
	// Set<PretEmpruntManuel> listeEmpruntsManuels = new HashSet<>();

	// Persistance
	// @OneToMany(mappedBy = "demandeur", fetch = FetchType.LAZY)
	// Set<DemandePretEmpruntManuel> listeDemandesPretsManuels = new HashSet<>();

	// Persistance
	// @OneToMany(mappedBy = "proprietaire", fetch = FetchType.LAZY)
	// Set<DemandePretEmpruntManuel> listeDemandesEmpruntsManuels = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authentification other = (Authentification) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
