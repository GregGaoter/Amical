package app.gaugiciel.amical.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	// Spécifié dans le fichier application.properties
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// La requête de la méthode usersByUsernameQuery doit obligatoirement contenir
		// un username (principal), un mot de passe (credentials) et un booléen (actif)
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
				"SELECT email AS principal,mot_de_passe AS credentials,actif_q FROM authentification WHERE email=?")
				.authoritiesByUsernameQuery(
						"SELECT authentification_email AS principal, role_role AS role FROM profil WHERE authentification_email=?")
				.passwordEncoder(new BCryptPasswordEncoder()).rolePrefix("ROLE_");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Indique que l'authentification passe par un formulaire d'authentification
		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers("/").permitAll();
		// http.authorizeRequests().antMatchers("/ami/**").hasRole("AMI");
		// http.authorizeRequests().antMatchers("/administrateur/**").hasRole("ADMINISTRATEUR");
		http.exceptionHandling().accessDeniedPage("/403");
	}

}
