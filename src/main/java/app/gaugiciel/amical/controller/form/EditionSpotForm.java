package app.gaugiciel.amical.controller.form;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Spot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@Getter
@Setter
public class EditionSpotForm {

	public static final String ID = "id";
	public static final String NOM = "nom";
	public static final String DESCRIPTION = "description";
	public static final String REMARQUE = "remarque";
	public static final String TAG_Q = "tagQ";
	public static final String LIEU_FRANCE_NOM_COMPLET = "lieuFranceNomComplet";
	public static final String NOM_PLAN = "nomPlan";
	private static final Map<String, Boolean> IS_NOT_NULL = new HashMap<>();

	static {
		IS_NOT_NULL.put(ID, true);
		IS_NOT_NULL.put(NOM, true);
		IS_NOT_NULL.put(DESCRIPTION, false);
		IS_NOT_NULL.put(REMARQUE, false);
		IS_NOT_NULL.put(TAG_Q, true);
		IS_NOT_NULL.put(LIEU_FRANCE_NOM_COMPLET, true);
		IS_NOT_NULL.put(NOM_PLAN, false);
	}

	@NotNull(message = "{validation.notnull}")
	private Long id;

	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 128, message = "{validation.size.interval}")
	private String nom;

	@Size(max = 2000, message = "{validation.size.max}")
	private String description;

	@Size(max = 2000, message = "{validation.size.max}")
	private String remarque;

	@NotNull(message = "{validation.notnull}")
	private Boolean tagQ;

	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 256, message = "{validation.size.interval}")
	private String lieuFranceNomComplet;
	private LieuFrance lieuFrance;

	@Size(max = 256, message = "{validation.size.max}")
	private String nomPlan;
	private Plan plan;

	private MultipartFile fichier;

	private EditionSpotForm(Spot spot) {
		id = spot.getId();
		nom = spot.getNom();
		description = spot.getDescription();
		remarque = spot.getRemarque();
		tagQ = spot.getTagQ();
		lieuFranceNomComplet = spot.getLieuFrance().afficherLieuComplet();
		nomPlan = spot.getPlan() == null ? null : spot.getPlan().getPlan();
	}

	public static EditionSpotForm creer(Spot spot) {
		return new EditionSpotForm(spot);
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

	public void updateSpot(Spot spot) {
		spot.setNom(nom);
		spot.setDescription(description);
		spot.setRemarque(remarque);
		spot.setLieuFrance(lieuFrance);
		spot.setPlan(plan);
	}

}
