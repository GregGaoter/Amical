package app.gaugiciel.amical.controller.form;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@Getter
@Setter
public class NouveauPlanForm {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NouveauPlanForm.class);

	public static final String FICHIER = "fichier";
	public static final String DESCRIPTION = "description";
	private static final Map<String, Boolean> IS_NOT_NULL = new HashMap<>();

	static {
		IS_NOT_NULL.put(FICHIER, true);
		IS_NOT_NULL.put(DESCRIPTION, false);
	}

	private MultipartFile fichier;

	@Size(min = 3, max = 256, message = "{validation.size.interval}")
	private String nomFichier;

	@Size(max = 2000, message = "{validation.size.max}")
	private String description;

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
