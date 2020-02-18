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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//Persistance
@Entity
@Table(name = "demande_pret_emprunt_manuel", indexes = { @Index(columnList = "demandeur"),
		@Index(columnList = "proprietaire"), @Index(columnList = "manuel_id") })
//Lombok
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "creer")
@Getter
@Setter
public class DemandePretEmpruntManuel implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Lombok
	@Setter(AccessLevel.PROTECTED)
	private Long id;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "demandeur", nullable = false)
	// Lombok
	@NonNull
	private Authentification demandeur;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "proprietaire", nullable = false)
	// Lombok
	@NonNull
	private Authentification proprietaire;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "manuel_id", nullable = false)
	// Lombok
	@NonNull
	private Manuel manuel;

	// Persistance
	@Column(nullable = false)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@PastOrPresent(message = "{validation.pastorpresent}")
	// Lombok
	@NonNull
	private Timestamp date;

}
