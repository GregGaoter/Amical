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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

//Persistance
@Entity
@Table(indexes = { @Index(columnList = "utilisateur_authentification_email"), @Index(columnList = "spot_id") })
//Lombok
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Commentaire implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Lombok
	@Setter(AccessLevel.PROTECTED)
	@ToString.Include
	private Long id;

	// Persistance
	@Column(nullable = false, length = 2000)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(max = 2000, message = "{validation.size.max}")
	// Lombok
	@NonNull
	private String commentaire;

	// Persistance
	@Column(nullable = false)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@PastOrPresent(message = "{validation.pastorpresent}")
	// Lombok
	@NonNull
	private Timestamp date;

	@Transient
	private String dateString;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "utilisateur_authentification_email", nullable = false)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	// Lombok
	@NonNull
	private Utilisateur utilisateur;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "spot_id", nullable = false)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	// Lombok
	@NonNull
	private Spot spot;

	private Commentaire(String commentaire, Timestamp date, Utilisateur utilisateur, Spot spot) {
		this.commentaire = commentaire;
		this.date = date;
		this.utilisateur = utilisateur;
		this.spot = spot;
	}

	public static Commentaire creer(String commentaire, Timestamp date, Utilisateur utilisateur, Spot spot) {
		return new Commentaire(commentaire, date, utilisateur, spot);
	}

}
