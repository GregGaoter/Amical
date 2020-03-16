package app.gaugiciel.amical.controller.form;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@Getter
@Setter
public class NouveauSpotForm {

	public static final String NOM = "nom";
	public static final String DESCRIPTION = "description";
	public static final String REMARQUE = "remarque";
	public static final String TAG_Q = "tagQ";
	public static final String LIEU_FRANCE = "lieuFrance";
	public static final String PLAN = "plan";
	private static final Map<String, Boolean> IS_NOT_NULL = new HashMap<>();

	static {
		IS_NOT_NULL.put(NOM, true);
		IS_NOT_NULL.put(DESCRIPTION, false);
		IS_NOT_NULL.put(REMARQUE, false);
		IS_NOT_NULL.put(TAG_Q, true);
		IS_NOT_NULL.put(LIEU_FRANCE, true);
		IS_NOT_NULL.put(PLAN, false);
	}

	private String nom;

	private String description;

	private String remarque;

	private Boolean tagQ;

	private String lieuFrance;

	private String plan;

	private MultipartFile fichier;

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

}
