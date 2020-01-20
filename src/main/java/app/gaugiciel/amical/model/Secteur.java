package app.gaugiciel.amical.model;

import java.io.Serializable;
import java.util.Objects;

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
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

//Persistance
@Entity
@Table(indexes = { @Index(columnList = "spot_id"), @Index(columnList = "nom") })
//Lombok
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Data
public class Secteur implements Serializable {

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
	@ToString.Include
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
	@ManyToOne
	@JoinColumn(name = "spot_id", nullable = false)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	// Lombok
	@NonNull
	private Spot spot;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "plan")
	private Plan plan;

	public boolean hasPlan() {
		return !Objects.isNull(plan);
	}

}
