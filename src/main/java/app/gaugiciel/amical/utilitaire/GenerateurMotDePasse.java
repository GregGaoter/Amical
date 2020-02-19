package app.gaugiciel.amical.utilitaire;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class GenerateurMotDePasse {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void genererMotsDePasse() {
		char[] listeCaracteresMotDePasse = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
				'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
				'5', '6', '7', '8', '9' };
		List<String> listeMotsDePasse = new ArrayList<>(360);
		for (int i = 0; i < 360; i++) {
			String[] motDePasse = new String[2];
			String motDePasseClair = "", motDePasseCrypte;
			Random randomLongueurMotDePasse = new Random();
			int longueurMotDePasse = randomLongueurMotDePasse.ints(1, 8, 17).sum();
			for (int j = 0; j < longueurMotDePasse; j++) {
				Random randomCaractere = new Random();
				motDePasseClair += listeCaracteresMotDePasse[randomCaractere.nextInt(listeCaracteresMotDePasse.length)];
			}
			motDePasseCrypte = passwordEncoder.encode(motDePasseClair);
			motDePasse[0] = motDePasseClair;
			motDePasse[1] = motDePasseCrypte;
			listeMotsDePasse.add(convertToCsv(motDePasse));
		}
		try {
			FileWriter fichierMotDePasse = new FileWriter(
					"C:\\Users\\gregg\\Documents\\openclassrooms\\developpeur_applications_java\\06_creer_site_communautaire_autour_escalade\\doc\\mots_de_passe.csv");
			PrintWriter printWriter = new PrintWriter(fichierMotDePasse);
			listeMotsDePasse.stream().forEach(printWriter::println);
			printWriter.close();
		} catch (IOException e) {
		}
	}

	private String convertToCsv(String[] data) {
		return Stream.of(data).collect(Collectors.joining(","));
	}

}
