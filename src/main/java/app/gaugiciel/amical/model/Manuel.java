package app.gaugiciel.amical.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

//Persistance
@Entity
@Table(indexes = { @Index(columnList = "lieu_france_id"), @Index(columnList = "nom"), @Index(columnList = "etat") })
//Lombok
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Data
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
	private Timestamp dateParution;

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
	@OneToMany(mappedBy = "manuel")
	private Set<PretManuel> listePretsManuels;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "lieu_france_id")
	private LieuFrance lieuFrance;

}
