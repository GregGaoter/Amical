package app.gaugiciel.amical.controller.form;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Secteur;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@Getter
@Setter
public class EditionSecteurForm {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EditionSecteurForm.class);

	public static final String ID = "id";
	public static final String NOM = "nom";
	public static final String DESCRIPTION = "description";
	public static final String REMARQUE = "remarque";
	public static final String NOM_SPOT = "nomSpot";
	public static final String NOM_PLAN = "nomPlan";
	private static final Map<String, Boolean> IS_NOT_NULL = new HashMap<>();

	static {
		IS_NOT_NULL.put(ID, true);
		IS_NOT_NULL.put(NOM, false);
		IS_NOT_NULL.put(DESCRIPTION, false);
		IS_NOT_NULL.put(REMARQUE, false);
		IS_NOT_NULL.put(NOM_SPOT, true);
		IS_NOT_NULL.put(NOM_PLAN, false);
	}

	@NotNull(message = "{validation.notnull}")
	private Long id;

	@Size(max = 128, message = "{validation.size.interval}")
	private String nom;

	@Size(max = 2000, message = "{validation.size.max}")
	private String description;

	@Size(max = 2000, message = "{validation.size.max}")
	private String remarque;

	@NotNull(message = "{validation.notnull}")
	private String nomSpot;

	@Size(max = 256, message = "{validation.size.max}")
	private String nomPlan;
	private Plan plan;

	private EditionSecteurForm(Secteur secteur) {
		id = secteur.getId();
		nom = secteur.getNom();
		description = secteur.getDescription();
		remarque = secteur.getRemarque();
		nomSpot = secteur.getSpot().getNom();
		nomPlan = secteur.getPlan() == null ? null : secteur.getPlan().getPlan();
	}

	public static EditionSecteurForm creer(Secteur secteur) {
		LOGGER.info("Start {}()", "creer");
		return new EditionSecteurForm(secteur);
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

	public void updateSecteur(Secteur secteur) {
		LOGGER.info("Start {}()", "updateSecteur");
		secteur.setNom(nom);
		secteur.setDescription(description);
		secteur.setRemarque(remarque);
		secteur.setPlan(plan);
	}

}
