package app.gaugiciel.amical.controller.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AmiManuelPretData {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AmiManuelPretData.class);

	@NonNull
	private Long idTopoDemandePret;
	@NonNull
	private String nomTopoDemandePret;
	@NonNull
	private String prenomDemandeurPret;
	@NonNull
	private String nomDemandeurPret;
	@NonNull
	private String emailDemandeurPret;
	@NonNull
	private String dateDemandePret;

	@NonNull
	private Long idTopoPretEnCours;
	@NonNull
	private String nomTopoPretEnCours;
	@NonNull
	private String prenomEmprunteur;
	@NonNull
	private String nomEmprunteur;
	@NonNull
	private String emailEmprunteur;
	@NonNull
	private String dateEmprunt;

	public AmiManuelPretData getDemandePretData(@NonNull Long idTopoDemandePret, @NonNull String nomTopoDemandePret,
			@NonNull String prenomDemandeurPret, @NonNull String nomDemandeurPret, @NonNull String emailDemandeurPret,
			@NonNull String dateDemandePret) {
		this.idTopoDemandePret = idTopoDemandePret;
		this.nomTopoDemandePret = nomTopoDemandePret;
		this.prenomDemandeurPret = prenomDemandeurPret;
		this.nomDemandeurPret = nomDemandeurPret;
		this.emailDemandeurPret = emailDemandeurPret;
		this.dateDemandePret = dateDemandePret;
		return this;
	}

	public AmiManuelPretData getPretEnCoursData(@NonNull Long idTopoPretEnCours, @NonNull String nomTopoPretEnCours,
			@NonNull String prenomEmprunteur, @NonNull String nomEmprunteur, @NonNull String emailEmprunteur,
			@NonNull String dateEmprunt) {
		this.idTopoPretEnCours = idTopoPretEnCours;
		this.nomTopoPretEnCours = nomTopoPretEnCours;
		this.prenomEmprunteur = prenomEmprunteur;
		this.nomEmprunteur = nomEmprunteur;
		this.emailEmprunteur = emailEmprunteur;
		this.dateEmprunt = dateEmprunt;
		return this;
	}

}
