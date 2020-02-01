package app.gaugiciel.amical.business.implementation.stockage;

public final class ServiceStockagePlan {

	public static final String REPERTOIRE_LOCAL_PLAN = "#{repertoireLocalPlanConvertionConfiguration.convert('${repertoire.local.plan}')}";
	public static final String URL_SERVEUR_PLAN = "#{urlPlanConvertionConfiguration.convert('${url.serveur.plan}')}";
	public static final String RESOURCE_HANDLER_PLAN = "/plan/";

	private ServiceStockagePlan() {
	}

}
