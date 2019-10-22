package app.gaugiciel.amical.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
//Lombok
@NoArgsConstructor
@Data
public class Voie implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// Lombok
	@Setter(AccessLevel.PROTECTED)
	private long id;

	// Persistance
	@Column(length = 128)
	// Validation constraints
	@Size(max = 128, message = "{validation.size.interval}")
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
	@Column(length = 32)
	// Validation constraints
	@Size(max = 32, message = "{validation.size.max}")
	private String numero;

	// Persistance
	@Column(nullable = true)
	// Validation constraints
	@Positive
	private double hauteur;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "secteur_id", nullable = false)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	// Lombok
	@NonNull
	private Secteur secteur;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Plan plan;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "cotation_france_id")
	private CotationFrance cotationFrance;

	@Override
	public String toString() {
		return numero + " - " + nom;
	}

}
