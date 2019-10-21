package app.gaugiciel.amical.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

//Persistance
@Embeddable
//Lombok
@NoArgsConstructor
@Data
public class PretEmpruntManuelClef implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Column(name = "utilisateur_authentification_email")
	private String utilisateurAuthentificationEmail;

	// Persistance
	@Column(name = "manuel_id")
	private long manuelId;

}
