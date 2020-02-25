package app.gaugiciel.amical.controller.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import app.gaugiciel.amical.model.Commentaire;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

//Spring
@Component
//Lombok
@NoArgsConstructor
@Getter
@Setter
public class EditionCommentaireForm {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EditionCommentaireForm.class);

	public static final String ID = "id";
	public static final String COMMENTAIRE = "commentaire";

	@NonNull
	private Long id;
	@NonNull
	private String commentaire;

	private EditionCommentaireForm(Commentaire commentaire) {
		id = commentaire.getId();
		this.commentaire = commentaire.getCommentaire();
	}

	public static EditionCommentaireForm creer(Commentaire commentaire) {
		LOGGER.info("Start {}()", "creer");
		return new EditionCommentaireForm(commentaire);
	}

}
