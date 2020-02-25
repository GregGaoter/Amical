package app.gaugiciel.amical.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import app.gaugiciel.amical.business.implementation.authentification.ServiceAuthenticationSuccessHandler;
import app.gaugiciel.amical.business.implementation.authentification.ServiceLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

	// Spécifié dans le fichier application.properties
	@Autowired
	private DataSource dataSource;
	@Autowired
	private ServiceAuthenticationSuccessHandler serviceAuthenticationSuccessHandler;
	@Autowired
	private ServiceLogoutSuccessHandler serviceLogoutSuccessHandler;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

		// La requête de la méthode usersByUsernameQuery doit obligatoirement contenir
		// un username (principal), un mot de passe (credentials) et un booléen (actif)
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
				"SELECT email AS principal,mot_de_passe AS credentials,actif_q FROM authentification WHERE email=?")
				.authoritiesByUsernameQuery(
						"SELECT authentification_email AS principal, role_role AS role FROM profil WHERE authentification_email=?")
				.passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.csrf().disable();

		// the order of the antMatchers() elements is significant – the more specific
		// rules need to come first, followed by the more general ones
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/ami/**").hasRole("AMI");
		http.authorizeRequests().antMatchers("/visiteur/**").permitAll();
		http.authorizeRequests().antMatchers("/").permitAll();

		http.formLogin().permitAll();
		http.formLogin().loginPage("/authentification");
		http.formLogin().usernameParameter("email");
		http.formLogin().passwordParameter("motDePasse");
		http.formLogin().successHandler(serviceAuthenticationSuccessHandler);
		http.formLogin().failureUrl("/authentification?erreur");

		http.logout().permitAll();
		http.logout().logoutSuccessHandler(serviceLogoutSuccessHandler);
		http.logout().deleteCookies("JSESSIONID");

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
