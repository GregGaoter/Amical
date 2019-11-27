package app.gaugiciel.amical.repository.specification;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//Lombok
@RequiredArgsConstructor
@Getter
@Setter
public class CritereRecherche {

	@NonNull
	private String nomAttribut;
	@NonNull
	private String operation;
	@NonNull
	private Object valeur;

}
