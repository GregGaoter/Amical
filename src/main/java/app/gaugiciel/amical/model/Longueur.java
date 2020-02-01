package app.gaugiciel.amical.model;

import java.io.Serializable;

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
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

//Persistance
@Entity
@Table(indexes = { @Index(columnList = "voie_id"), @Index(columnList = "plan") })
//Lombok
@NoArgsConstructor
@Data
public class Longueur implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Lombok
	@Setter(AccessLevel.PROTECTED)
	private Long id;

	// Persistance
	@Column(length = 128)
	// Validation constraints
	@Size(max = 128, message = "{validation.size.interval}")
	// Lombok
	private String nom;

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
	@Column(nullable = true)
	// Validation constraints
	@Positive(message = "{validation.positive}")
	private Double longueur;

	// Persistance
	@Column(name = "nb_spits", nullable = true)
	// Validation constraints
	@Positive(message = "{validation.positive}")
	private Integer nbSpits;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "voie_id", nullable = false)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	// Lombok
	@NonNull
	private Voie voie;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "plan")
	private Plan plan;

	private Longueur(String nom, String description, String remarque, Double longueur, Integer nbSpits, Voie voie,
			Plan plan) {
		this.nom = nom;
		this.description = description;
		this.remarque = remarque;
		this.longueur = longueur;
		this.nbSpits = nbSpits;
		this.voie = voie;
		this.plan = plan;
	}

	public static Longueur creer(String nom, String description, String remarque, Double longueur, Integer nbSpits, Voie voie,
			Plan plan) {
		return new Longueur(nom, description, remarque, longueur, nbSpits, voie, plan);
	}

}
