package app.gaugiciel.amical.controller.form;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Voie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@Getter
@Setter
public class EditionVoieForm {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditionVoieForm.class);

	public static final String ID = "id";
	public static final String NOM_SPOT = "nomSpot";
	public static final String NOM = "nom";
	public static final String DESCRIPTION = "description";
	public static final String REMARQUE = "remarque";
	public static final String NUMERO = "numero";
	public static final String HAUTEUR = "hauteur";
	public static final String NOM_SECTEUR = "nomSecteur";
	public static final String NOM_PLAN = "nomPlan";
	public static final String COTATION_UNITE_PRINCIPALE = "cotationUnitePrincipale";
	public static final String COTATION_UNITE_SECONDAIRE = "cotationUniteSecondaire";
	public static final String COTATION_UNITE_TERTIAIRE = "cotationUniteTertiaire";
	private static final Map<String, Boolean> IS_NOT_NULL = new HashMap<>();

	static {
		IS_NOT_NULL.put(ID, true);
		IS_NOT_NULL.put(NOM_SPOT, true);
		IS_NOT_NULL.put(NOM, false);
		IS_NOT_NULL.put(DESCRIPTION, false);
		IS_NOT_NULL.put(REMARQUE, false);
		IS_NOT_NULL.put(NUMERO, false);
		IS_NOT_NULL.put(HAUTEUR, false);
		IS_NOT_NULL.put(NOM_SECTEUR, true);
		IS_NOT_NULL.put(NOM_PLAN, false);
		IS_NOT_NULL.put(COTATION_UNITE_PRINCIPALE, false);
		IS_NOT_NULL.put(COTATION_UNITE_SECONDAIRE, false);
		IS_NOT_NULL.put(COTATION_UNITE_TERTIAIRE, false);
	}

	private Long id;

	private String nomSpot;
	private Spot spot;

	private String nom;

	private String description;

	private String remarque;

	private String numero;

	private Double hauteur;

	private String nomSecteur;
	private Secteur secteur;

	private String nomPlan;
	private Plan plan;

	private String cotationUnitePrincipale;
	private String cotationUniteSecondaire;
	private String cotationUniteTertiaire;
	private CotationFrance cotationFrance;

	private EditionVoieForm(Voie voie) {
		id = voie.getId();
		spot = voie.getSecteur().getSpot();
		nomSpot = spot.getNom();
		nom = voie.getNom();
		description = voie.getDescription();
		remarque = voie.getRemarque();
		numero = voie.getNumero();
		hauteur = voie.getHauteur();
		secteur = voie.getSecteur();
		nomSecteur = secteur.getNom();
		plan = voie.getPlan();
		nomPlan = plan == null ? null : plan.getPlan();
		cotationFrance = voie.getCotationFrance();
		cotationUnitePrincipale = cotationFrance == null ? null : cotationFrance.getUnitePrincipale();
		cotationUniteSecondaire = cotationFrance == null ? null : cotationFrance.getUniteSecondaire();
		cotationUniteTertiaire = cotationFrance == null ? null : cotationFrance.getUniteTertiaire();
	}

	public static EditionVoieForm creer(Voie voie) {
		LOGGER.info("Start {}()", "creer");
		return new EditionVoieForm(voie);
	}

	public boolean isNotNull(String nomAttribut) {
		return IS_NOT_NULL.get(nomAttribut);
	}

	public boolean estCotationVide() {
		LOGGER.info("Start {}()", "estCotationVide");
		return estNull(cotationUnitePrincipale) && estNull(cotationUniteSecondaire) && estNull(cotationUniteTertiaire);
	}

	private boolean estNull(String str) {
		return str == null ? true : str.strip().length() == 0;
	}

	public void updateVoie(Voie voie) {
		LOGGER.info("Start {}()", "updateVoie");
		voie.setCotationFrance(cotationFrance);
		voie.setDescription(description);
		voie.setHauteur(hauteur);
		voie.setNom(nom);
		voie.setNumero(numero);
		voie.setPlan(plan);
		voie.setRemarque(remarque);
	}

}
