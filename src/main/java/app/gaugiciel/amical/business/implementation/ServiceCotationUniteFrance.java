package app.gaugiciel.amical.business.implementation;

import app.gaugiciel.amical.business.contrat.ServiceCotationUnite;
import lombok.Data;
import lombok.NonNull;

@Data
public class ServiceCotationUniteFrance implements ServiceCotationUnite {

	@NonNull
	private ServiceCotationUniteFrancePrincipale unitePrincipale;
	@NonNull
	private ServiceCotationUniteFranceSecondaire uniteSecondaire;
	@NonNull
	private ServiceCotationUniteFranceTertiaire uniteTertiaire;

	@Override
	public String afficher() {
		return unitePrincipale.label + uniteSecondaire.label + uniteTertiaire.label;
	}

}
