package app.gaugiciel.amical.controller.form;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteTertiaire;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Spring
@Component
//Lombok
@NoArgsConstructor
@Getter
@Setter
public class RechercheSpotForm {

	private static final Logger LOGGER = LoggerFactory.getLogger(RechercheSpotForm.class);

	public static final String NOM_SPOT = "nomSpot";
	public static final String LIEU_FRANCE_SPOT = "lieuFranceSpot";
	public static final String IS_OFFICIEL_SPOT = "isOfficielSpot";
	public static final String NOM_SECTEUR = "nomSecteur";
	public static final String NOM_VOIE = "nomVoie";
	public static final String COTATION_MIN_VOIE_UNITE_PRINCIPALE = "cotationMinVoieUnitePrincipale";
	public static final String COTATION_MIN_VOIE_UNITE_SECONDAIRE = "cotationMinVoieUniteSecondaire";
	public static final String COTATION_MIN_VOIE_UNITE_TERTIAIRE = "cotationMinVoieUniteTertiaire";
	public static final String COTATION_MAX_VOIE_UNITE_PRINCIPALE = "cotationMaxVoieUnitePrincipale";
	public static final String COTATION_MAX_VOIE_UNITE_SECONDAIRE = "cotationMaxVoieUniteSecondaire";
	public static final String COTATION_MAX_VOIE_UNITE_TERTIAIRE = "cotationMaxVoieUniteTertiaire";
	public static final String HAUTEUR_MIN_VOIE = "hauteurMinVoie";
	public static final String HAUTEUR_MAX_VOIE = "hauteurMaxVoie";
	public static final String LISTE_COTATION = "listeCotations";
	public static final List<String> LISTE_FIELDS_COTATION = Arrays.asList(COTATION_MIN_VOIE_UNITE_PRINCIPALE,
			COTATION_MIN_VOIE_UNITE_SECONDAIRE, COTATION_MIN_VOIE_UNITE_TERTIAIRE, COTATION_MAX_VOIE_UNITE_PRINCIPALE,
			COTATION_MAX_VOIE_UNITE_SECONDAIRE, COTATION_MAX_VOIE_UNITE_TERTIAIRE);

	private String nomSpot;

	private String lieuFranceSpot;

	private Boolean isOfficielSpot;

	private String nomSecteur;

	private String nomVoie;

	private String cotationMinVoieUnitePrincipale;

	private String cotationMinVoieUniteSecondaire;

	private String cotationMinVoieUniteTertiaire;

	private String cotationMaxVoieUnitePrincipale;

	private String cotationMaxVoieUniteSecondaire;

	private String cotationMaxVoieUniteTertiaire;

	private Integer hauteurMinVoie;

	private Integer hauteurMaxVoie;

	private List<ServiceCotationFrance> listeCotations;

	private Boolean isFieldsCotationValid;

	public void reinitialiser() {
		Stream.of(getClass().getDeclaredFields()).forEach(field -> {
			field.setAccessible(true);
			try {
				field.set(this, null);
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
		});
	}

	public ServiceCotationFrance getCotationMin() {
		LOGGER.info("Start {}()", "getCotationMin");
		return ServiceCotationFrance.creer(CotationFranceUnitePrincipale.ofLabel(cotationMinVoieUnitePrincipale),
				CotationFranceUniteSecondaire.ofLabel(cotationMinVoieUniteSecondaire),
				CotationFranceUniteTertiaire.ofLabel(cotationMinVoieUniteTertiaire));
	}

	public ServiceCotationFrance getCotationMax() {
		LOGGER.info("Start {}()", "getCotationMax");
		return ServiceCotationFrance.creer(CotationFranceUnitePrincipale.ofLabel(cotationMaxVoieUnitePrincipale),
				CotationFranceUniteSecondaire.ofLabel(cotationMaxVoieUniteSecondaire),
				CotationFranceUniteTertiaire.ofLabel(cotationMaxVoieUniteTertiaire));
	}

	public boolean estVide() {
		LOGGER.info("Start {}()", "estVide");
		return estNull(nomSpot) && estNull(lieuFranceSpot) && estNull(isOfficielSpot) && estNull(nomSecteur)
				&& estNull(nomVoie) && estCotationVide() && estNull(hauteurMinVoie) && estNull(hauteurMaxVoie);
	}

	public boolean estCotationVide() {
		LOGGER.info("Start {}()", "estCotationVide");
		return estNull(cotationMinVoieUnitePrincipale) && estNull(cotationMinVoieUniteSecondaire)
				&& estNull(cotationMinVoieUniteTertiaire) && estNull(cotationMaxVoieUnitePrincipale)
				&& estNull(cotationMaxVoieUniteSecondaire) && estNull(cotationMaxVoieUniteTertiaire);
	}

	private boolean estNull(String str) {
		return str == null ? true : str.strip().length() == 0;
	}

	private boolean estNull(Boolean bool) {
		return bool == null;
	}

	private boolean estNull(Integer i) {
		return i == null;
	}

}
