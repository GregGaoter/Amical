package app.gaugiciel.amical.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//Persistance
@Entity
@Table(indexes = { @Index(columnList = "authentification_email"), @Index(columnList = "lieu_france_id"),
		@Index(columnList = "nom"), @Index(columnList = "etat") })
//Lombok
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Setter
@RequiredArgsConstructor(staticName = "creer")
public class Manuel implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Lombok
	@Setter(AccessLevel.PROTECTED)
	private Long id;

	// Persistance
	@Column(nullable = false, length = 128)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 128, message = "{validation.size.interval}")
	// Lombok
	@NonNull
	@ToString.Include
	private String nom;

	// Persistance
	@Column(name = "date_parution")
	// Validation constraints
	@PastOrPresent(message = "{validation.pastorpresent}")
	private Timestamp dateTimeParution;

	// Persistance
	@Column(length = 128)
	// Validation constraints
	@Size(max = 128, message = "{validation.size.max}")
	private String auteur;

	// Persistance
	@Column(length = 2000)
	// Validation constraints
	@Size(max = 2000, message = "{validation.size.max}")
	private String description;

	// Persistance
	@Column(length = 2000)
	// Validation constraints
	@Size(max = 2000, message = "{validation.size.max}")
	private String remarque;

	// Persistance
	@Column(nullable = false, length = 64)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 64, message = "{validation.size.interval}")
	// Lombok
	@NonNull
	private String etat;

	// Persistance
	@Column(nullable = false, length = 64)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 64, message = "{validation.size.interval}")
	// Lombok
	@NonNull
	private String categorie;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "authentification_email", nullable = false)
	private Authentification authentification;

	// Persistance
	//@OneToMany(mappedBy = "manuel", fetch = FetchType.LAZY)
	//Set<PretEmpruntManuel> listePretsEmpruntsManuels = new HashSet<>();

	// Persistance
	@ManyToOne
	@JoinColumn(name = "lieu_france_id")
	private LieuFrance lieuFrance;

	private Manuel(String nom, Timestamp dateTimeParution, String auteur, String description, String remarque,
			String etat, String categorie, Authentification authentification, LieuFrance lieuFrance) {
		this.nom = nom;
		this.dateTimeParution = dateTimeParution;
		this.auteur = auteur;
		this.description = description;
		this.remarque = remarque;
		this.etat = etat;
		this.categorie = categorie;
		this.authentification = authentification;
		this.lieuFrance = lieuFrance;
	}

	public static Manuel creer(String nom, Timestamp dateTimeParution, String auteur, String description,
			String remarque, String etat, String categorie, Authentification authentification, LieuFrance lieuFrance) {
		return new Manuel(nom, dateTimeParution, auteur, description, remarque, etat, categorie, authentification,
				lieuFrance);
	}

	public String getDateParution() {
		return dateTimeParution == null ? "" : dateTimeParution.toString().split(" ")[0];
	}

}
