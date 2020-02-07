package app.gaugiciel.amical.business.implementation.enumeration;

public enum RoleUtilisateur {

	ADMIN("admin"), AMI("ami"), VISITEUR("visiteur");

	public final String label;

	private RoleUtilisateur(String label) {
		this.label = label;
	}

}
