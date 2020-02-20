package app.gaugiciel.amical.controller.form;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Utilisateur;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "creer")
@Getter
@Setter
public class NouveauCommentaireForm {
	
	public static final String COMMENTAIRE = "commentaire";

	@NonNull
	private String commentaire;

	@NonNull
	private Timestamp date;

	@NonNull
	private Utilisateur utilisateur;

	@NonNull
	private Spot spot;

}
