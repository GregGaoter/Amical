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
@Table(name = "pret_emprunt_manuel", indexes = { @Index(columnList = "authentification_email_preteur"),
		@Index(columnList = "authentification_email_emprunteur"), @Index(columnList = "manuel_id") })
//Lombok
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor(staticName = "creer")
public class PretEmpruntManuel implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Lombok
	@Setter(AccessLevel.PROTECTED)
	private Long id;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "authentification_email_preteur", nullable = false)
	private Authentification emailPreteur;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "authentification_email_emprunteur", nullable = false)
	private Authentification emailEmprunteur;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "manuel_id", nullable = false)
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
