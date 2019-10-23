package app.gaugiciel.amical.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

//Persistance
@Entity
@Table(indexes = { @Index(columnList = "nom"), @Index(columnList = "prenom") })
//Lombok
@NoArgsConstructor
@Data
public abstract class Utilisateur implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@Column(name = "authentification_email")
	private String authentificationEmail;

	// Persistance
	@Column(nullable = false, length = 64)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 64, message = "{validation.size.interval}")
	// Lombok
	@NonNull
	private String prenom;

	// Persistance
	@Column(nullable = false, length = 64)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 64, message = "{validation.size.interval}")
	// Lombok
	@NonNull
	private String nom;

	// Persistance
	@OneToOne
	@MapsId
	private Authentification authentification;

	// Persistance
	@OneToMany(mappedBy = "utilisateur")
	Set<PretManuel> listePretsManuels;

	@Override
	public String toString() {
		return prenom + " " + nom;
	}

}
