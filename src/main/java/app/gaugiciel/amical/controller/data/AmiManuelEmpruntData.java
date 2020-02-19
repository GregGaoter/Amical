package app.gaugiciel.amical.controller.data;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

//Spring
@Component
//Lombok
@NoArgsConstructor
@Getter
@Setter
public class AmiManuelEmpruntData {

	@NonNull
	private Long idTopoDemandeEmprunt;
	@NonNull
	private String nomTopoDemandeEmprunt;
	@NonNull
	private String dateDemandeEmprunt;

	@NonNull
	private Long idTopoEmpruntEnCours;
	@NonNull
	private String nomTopoEmpruntEnCours;
	@NonNull
	private String prenomProprietaire;
	@NonNull
	private String nomProprietaire;
	@NonNull
	private String emailProprietaire;
	@NonNull
	private String dateEmprunt;

	public AmiManuelEmpruntData getDemandeEmpruntData(@NonNull Long idTopoDemandeEmprunt,
			@NonNull String nomTopoDemandeEmprunt, @NonNull String dateDemandeEmprunt) {
		this.idTopoDemandeEmprunt = idTopoDemandeEmprunt;
		this.nomTopoDemandeEmprunt = nomTopoDemandeEmprunt;
		this.dateDemandeEmprunt = dateDemandeEmprunt;
		return this;
	}

	public AmiManuelEmpruntData getEmpruntEnCoursData(@NonNull Long idTopoEmpruntEnCours,
			@NonNull String nomTopoEmpruntEnCours, @NonNull String prenomProprietaire, @NonNull String nomProprietaire,
			@NonNull String emailProprietaire, @NonNull String dateEmprunt) {
		this.idTopoEmpruntEnCours = idTopoEmpruntEnCours;
		this.nomTopoEmpruntEnCours = nomTopoEmpruntEnCours;
		this.prenomProprietaire = prenomProprietaire;
		this.nomProprietaire = nomProprietaire;
		this.emailProprietaire = emailProprietaire;
		this.dateEmprunt = dateEmprunt;
		return this;
	}

}
