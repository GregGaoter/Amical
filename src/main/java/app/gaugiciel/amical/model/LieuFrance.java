package app.gaugiciel.amical.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//Persistance
@Entity
@Table(name = "lieu_france", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "departement", "code_postal", "ville" }) }, indexes = {
				@Index(columnList = "region"), @Index(columnList = "departement"), @Index(columnList = "code_postal"),
				@Index(columnList = "ville") })
//Lombok
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Setter
@RequiredArgsConstructor(staticName = "creer")
public class LieuFrance implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String VILLE = "ville";
	public static final String DEPARTEMENT = "departement";
	public static final String ID = "id";
	public static final String CODE_POSTAL = "codePostal";
	public static final String REGION = "region";
	public static final String PLAN = "plan";

	// Persistance
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Lombok
	@Setter(AccessLevel.PROTECTED)
	private Long id;

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
	@Size(min = 1, max = 5, message = "{validation.size.interval}")
	// Lombok
	@NonNull
	@ToString.Include
	private String codePostal;

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
	@JoinColumn(name = "plan")
	private Plan plan;

	public List<String> getProprietes() {
		List<String> listeProprietes = new ArrayList<>(4);
		listeProprietes.add(region);
		listeProprietes.add(departement);
		listeProprietes.add(codePostal);
		listeProprietes.add(ville);
		return listeProprietes;
	}

	public String afficherLieuComplet() {
		return region + ", " + departement + ", " + codePostal + ", " + ville;
	}

}
