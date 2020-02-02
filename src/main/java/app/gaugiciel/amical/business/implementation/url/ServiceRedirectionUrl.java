package app.gaugiciel.amical.business.implementation.url;

public enum ServiceRedirectionUrl {

	PREVIOUS_URL("redirectPreviousUrl"), SPOT("redirectSpot"), VOIE("redirectVoie"),
	SECTEUR_FORM("redirectSecteurForm"), VOIE_FORM("redirectVoieForm"), LONGUEUR_FORM("redirectLongueurForm"),
	EDITION_SPOT_FORM("redirectEditionSpotForm"), EDITION_SECTEUR_FORM("redirectEditionSecteurForm");

	public final String label;

	private ServiceRedirectionUrl(String label) {
		this.label = label;
	}

}
