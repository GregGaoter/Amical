package app.gaugiciel.amical.business.implementation.enumeration;

public enum NomModel {

	AUTHENTIFICATION("authentification"), COMMENTAIRE("commentaire"), COTATION_FRANCE("cotationFrance"),
	EMPRUNT_MANUEL("empruntManuel"), LIEU_FRANCE("lieuFrance"), LONGUEUR("longueur"), MANUEL("manuel"), PLAN("plan"),
	PRET_EMPRUNT_MANUEL_CLEF("pretEmpruntManuelClef"), PRET_MANUEL("pretManuel"), ROLE("role"), SECTEUR("secteur"),
	SPOT("spot"), UTILISATEUR("utilisateur"), VOIE("voie");

	public final String label;

	private NomModel(String label) {
		this.label = label;
	}

}
