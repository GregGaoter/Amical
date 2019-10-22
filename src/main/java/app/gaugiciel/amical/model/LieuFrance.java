package app.gaugiciel.amical.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "lieu_france")
//Lombok
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Data
public class LieuFrance implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// Lombok
	@Setter(AccessLevel.PROTECTED)
	private long id;

	// Persistance
	@Column(nullable = false, length = 64)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 64, message = "{validation.size.interval}")
	// Lombok
	@NonNull
	@ToString.Include
	private String region;

	// Persistance
	@Column(nullable = false, length = 64)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 64, message = "{validation.size.interval}")
	// Lombok
	@NonNull
	@ToString.Include
	private String departement;

	// Persistance
	@Column(name = "code_postal", nullable = false, length = 5)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 5, max = 5, message = "{validation.size.exact}")
	// Lombok
	@NonNull
	@ToString.Include
	private String codePostale;

	// Persistance
	@Column(nullable = false, length = 64)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 64, message = "{validation.size.interval}")
	// Lombok
	@NonNull
	@ToString.Include
	private String ville;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Plan plan;

}
