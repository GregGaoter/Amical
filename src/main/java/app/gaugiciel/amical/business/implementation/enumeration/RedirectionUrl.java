package app.gaugiciel.amical.business.implementation.enumeration;

public enum RedirectionUrl {

	PREVIOUS_URL("redirectPreviousUrl"), SPOT("redirectSpot"), VOIE("redirectVoie"), LONGUEUR("redirectLongueur"),
	MANUEL("redirectManuel"), TOPOS("redirectTopos"), SECTEUR_FORM("redirectSecteurForm"),
	VOIE_FORM("redirectVoieForm"), LONGUEUR_FORM("redirectLongueurForm"), TOPO_FORM("redirectTopoForm"),
	EDITION_SPOT_FORM("redirectEditionSpotForm"), EDITION_SECTEUR_FORM("redirectEditionSecteurForm"),
	EDITION_VOIE_FORM("redirectEditionVoieForm"), EDITION_LONGUEUR_FORM("redirectEditionLongueurForm"),
	RECHERCHE_TOPO_FORM("redirectRechercheTopoForm");

	public final String label;

	private RedirectionUrl(String label) {
		this.label = label;
	}

}
