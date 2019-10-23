package app.gaugiciel.amical.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

//Persistance
@Entity
@Table(name = "pret_manuel", indexes = { @Index(columnList = "utilisateur_authentification_email"),
		@Index(columnList = "manuel_id") })
//Lombok
@NoArgsConstructor
@Data
public class PretManuel implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@EmbeddedId
	private PretEmpruntManuelClef id;

	// Persistance
	@ManyToOne
	@MapsId("utilisateur_authentification_email")
	@JoinColumn(name = "utilisateur_authentification_email")
	private Utilisateur utilisateur;

	// Persistance
	@ManyToOne
	@MapsId("manuel_id")
	@JoinColumn(name = "manuel_id")
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
