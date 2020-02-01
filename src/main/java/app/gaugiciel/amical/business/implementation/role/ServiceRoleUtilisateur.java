package app.gaugiciel.amical.business.implementation.role;

import app.gaugiciel.amical.business.contrat.ServiceRole;

public enum ServiceRoleUtilisateur implements ServiceRole {

	ADMIN("admin"), AMI("ami"), VISITEUR("visiteur");

	public final String label;

	private ServiceRoleUtilisateur(String label) {
		this.label = label;
	}

}
