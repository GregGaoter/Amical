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
@Table(indexes = { @Index(columnList = "voie_id"), @Index(columnList = "plan_id") })
//Lombok
@NoArgsConstructor
@Data
public class Longueur implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	@Positive
	private Double longueur;

	// Persistance
	@Column(name = "nb_spits", nullable = true)
	// Validation constraints
	@Positive
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
	@JoinColumn(name = "plan_id")
	private Plan plan;

}
