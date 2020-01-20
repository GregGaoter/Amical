package app.gaugiciel.amical.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Persistance
@Entity
//Lombok
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Data
public class Plan implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@Column(nullable = false, length = 256)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 3, max = 256, message = "{validation.size.interval}")
	// Lombok
	@ToString.Include
	private String plan;

	// Persistance
	@Column(length = 2000)
	// Validation constraints
	@Size(max = 2000, message = "{validation.size.max}")
	private String description;

	private Plan(String plan, String description) {
		this.plan = plan;
		this.description = description;
	}

	public static Plan creer(String plan, String description) {
		return new Plan(plan, description);
	}

}
