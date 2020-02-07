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

import app.gaugiciel.amical.utilitaire.Utils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//Persistance
@Entity
@Table(indexes = { @Index(columnList = "cotation_france_id"), @Index(columnList = "secteur_id"),
		@Index(columnList = "hauteur") })
//Lombok
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor(staticName = "creer")
public class Voie implements Serializable {

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
	@Positive(message = "{validation.positive}")
	private Double hauteur;

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
	@JoinColumn(name = "plan")
	private Plan plan;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "cotation_france_id")
	private CotationFrance cotationFrance;

	private Voie(String nom, String description, String remarque, String numero, Double hauteur, Secteur secteur,
			Plan plan, CotationFrance cotationFrance) {
		this.nom = nom;
		this.description = description;
		this.remarque = remarque;
		this.numero = numero;
		this.hauteur = hauteur;
		this.secteur = secteur;
		this.plan = plan;
		this.cotationFrance = cotationFrance;
	}

	public static Voie creer(String nom, String description, String remarque, String numero, Double hauteur,
			Secteur secteur, Plan plan, CotationFrance cotationFrance) {
		return new Voie(nom, description, remarque, numero, hauteur, secteur, plan, cotationFrance);
	}

	@Override
	public String toString() {
		if (!Utils.isValid(numero) && !Utils.isValid(nom)) {
			return "";
		} else if (Utils.isValid(numero) && !Utils.isValid(nom)) {
			return numero;
		} else if (!Utils.isValid(numero) && Utils.isValid(nom)) {
			return nom;
		}
		return numero + " - " + nom;
	}

}
