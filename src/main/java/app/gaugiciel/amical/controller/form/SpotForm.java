package app.gaugiciel.amical.controller.form;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFranceUniteTertiaire;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Spring
@Component
//Lombok
@NoArgsConstructor
@Getter
@Setter
public class SpotForm {

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

	@Size(max = 128, message = "{validation.size.max}")
	private String nomSpot;

	@Size(max = 64, message = "{validation.size.max}")
	private String lieuFranceSpot;

	private Boolean isOfficielSpot;

	@Size(max = 128, message = "{validation.size.max}")
	private String nomSecteur;

	@Size(max = 128, message = "{validation.size.max}")
	private String nomVoie;

	@Size(max = 1, message = "{validation.size.max}")
	private String cotationMinVoieUnitePrincipale;

	@Size(max = 3, message = "{validation.size.max}")
	private String cotationMinVoieUniteSecondaire;

	@Size(max = 1, message = "{validation.size.max}")
	private String cotationMinVoieUniteTertiaire;

	@Size(max = 1, message = "{validation.size.max}")
	private String cotationMaxVoieUnitePrincipale;

	@Size(max = 3, message = "{validation.size.max}")
	private String cotationMaxVoieUniteSecondaire;

	@Size(max = 1, message = "{validation.size.max}")
	private String cotationMaxVoieUniteTertiaire;

	@PositiveOrZero(message = "{validation.positiveorzero}")
	private Integer hauteurMinVoie;

	@PositiveOrZero(message = "{validation.positiveorzero}")
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
		return ServiceCotationFrance.creer(ServiceCotationFranceUnitePrincipale.ofLabel(cotationMinVoieUnitePrincipale),
				ServiceCotationFranceUniteSecondaire.ofLabel(cotationMinVoieUniteSecondaire),
				ServiceCotationFranceUniteTertiaire.ofLabel(cotationMinVoieUniteTertiaire));
	}

	public ServiceCotationFrance getCotationMax() {
		return ServiceCotationFrance.creer(ServiceCotationFranceUnitePrincipale.ofLabel(cotationMaxVoieUnitePrincipale),
				ServiceCotationFranceUniteSecondaire.ofLabel(cotationMaxVoieUniteSecondaire),
				ServiceCotationFranceUniteTertiaire.ofLabel(cotationMaxVoieUniteTertiaire));
	}

	public boolean estVide() {
		return estNull(nomSpot) && estNull(lieuFranceSpot) && estNull(isOfficielSpot) && estNull(nomSecteur)
				&& estNull(nomVoie) && estCotationVide() && estNull(hauteurMinVoie) && estNull(hauteurMaxVoie);
	}

	public boolean estCotationVide() {
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
