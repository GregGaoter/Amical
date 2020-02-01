package app.gaugiciel.amical.business.implementation;

public enum ServiceRedirectionUrl {

	PREVIOUS_URL("redirectPreviousUrl"), SPOT("redirectSpot"), SECTEUR_FORM("redirectSecteurForm"),
	VOIE_FORM("redirectVoieForm");

	public final String label;

	private ServiceRedirectionUrl(String label) {
		this.label = label;
	}

}
