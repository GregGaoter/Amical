package app.gaugiciel.amical.controller.form;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import app.gaugiciel.amical.model.Longueur;
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
public class EditionLongueurForm {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditionLongueurForm.class);

	public static final String ID = "id";
	public static final String NOM_SPOT = "nomSpot";
	public static final String NOM_SECTEUR = "nomSecteur";
	public static final String NOM_VOIE = "nomVoie";
	public static final String NOM = "nom";
	public static final String DESCRIPTION = "description";
	public static final String REMARQUE = "remarque";
	public static final String LONGUEUR = "longueur";
	public static final String NB_SPITS = "nbSpits";
	public static final String NOM_PLAN = "nomPlan";
	private static final Map<String, Boolean> IS_NOT_NULL = new HashMap<>();

	static {
		IS_NOT_NULL.put(ID, true);
		IS_NOT_NULL.put(NOM_SPOT, true);
		IS_NOT_NULL.put(NOM_SECTEUR, true);
		IS_NOT_NULL.put(NOM_VOIE, true);
		IS_NOT_NULL.put(NOM, false);
		IS_NOT_NULL.put(DESCRIPTION, false);
		IS_NOT_NULL.put(REMARQUE, false);
		IS_NOT_NULL.put(LONGUEUR, false);
		IS_NOT_NULL.put(NB_SPITS, false);
		IS_NOT_NULL.put(NOM_PLAN, false);
	}

	private Long id;

	private String nomSpot;
	private Spot spot;

	private String nomSecteur;
	private Secteur secteur;

	private String nomVoie;
	private Voie voie;

	private String nom;

	private String description;

	private String remarque;

	private Double longueur;

	private Integer nbSpits;

	private String nomPlan;
	private Plan plan;

	private EditionLongueurForm(Longueur longueur) {
		id = longueur.getId();
		voie = longueur.getVoie();
		nomVoie = voie.getNom();
		secteur = voie.getSecteur();
		nomSecteur = secteur.getNom();
		spot = secteur.getSpot();
		nomSpot = spot.getNom();
		plan = longueur.getPlan();
		nomPlan = plan == null ? null : plan.getPlan();
		nom = longueur.getNom();
		description = longueur.getDescription();
		remarque = longueur.getRemarque();
		this.longueur = longueur.getLongueur();
		nbSpits = longueur.getNbSpits();
	}

	public static EditionLongueurForm creer(Longueur longueur) {
		LOGGER.info("Start {}()", "creer");
		return new EditionLongueurForm(longueur);
	}

	public void reinitialiser() {
		Stream.of(getClass().getDeclaredFields()).forEach(field -> {
			field.setAccessible(true);
			try {
				field.set(this, null);
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
		});
	}

	public boolean isNotNull(String nomAttribut) {
		return IS_NOT_NULL.get(nomAttribut);
	}

	public void updateLongueur(Longueur longueur) {
		LOGGER.info("Start {}()", "updateLongueur");
		longueur.setDescription(description);
		longueur.setLongueur(this.longueur);
		longueur.setNbSpits(nbSpits);
		longueur.setNom(nom);
		longueur.setPlan(plan);
		longueur.setRemarque(remarque);
	}

}
