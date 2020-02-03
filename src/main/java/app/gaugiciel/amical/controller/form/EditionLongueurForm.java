package app.gaugiciel.amical.controller.form;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

	@NotNull(message = "{validation.notnull}")
	private Long id;

	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 128, message = "{validation.size.interval}")
	private String nomSpot;
	private Spot spot;

	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 128, message = "{validation.size.interval}")
	private String nomSecteur;
	private Secteur secteur;

	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 128, message = "{validation.size.interval}")
	private String nomVoie;
	private Voie voie;

	@Size(max = 128, message = "{validation.size.interval}")
	private String nom;

	@Size(max = 2000, message = "{validation.size.max}")
	private String description;

	@Size(max = 2000, message = "{validation.size.max}")
	private String remarque;

	private Double longueur;

	private Integer nbSpits;

	@Size(max = 256, message = "{validation.size.max}")
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
		longueur.setDescription(description);
		longueur.setLongueur(this.longueur);
		longueur.setNbSpits(nbSpits);
		longueur.setNom(nom);
		longueur.setPlan(plan);
		longueur.setRemarque(remarque);
	}

}
