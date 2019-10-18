package app.gaugiciel.amical.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	// Spécifié dans le fichier application.properties
	@Autowired
	private DataSource dataSource;

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
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMINISTRATEUR");
		http.authorizeRequests().antMatchers("/ami/**").hasRole("AMI");
		// http.authorizeRequests().antMatchers("/anonymous*").anonymous();
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/login*").permitAll();
		// http.authorizeRequests().anyRequest().authenticated();

		http.formLogin().loginPage("/login");
		// Specifies the URL to validate the credentials.
		// http.formLogin().loginProcessingUrl("/perform_login");
		// Specifies where users will go after authenticating successfully
		// http.formLogin().defaultSuccessUrl("homepage.html", true);
		// The URL to send users if authentication fails.
		// http.formLogin().failureUrl("/login.html?error=true");
		// Specifies the AuthenticationFailureHandler to use when authenticationfails.
		// The default is redirecting to "/login?error"
		// http.formLogin().failureHandler(authenticationFailureHandler());

		// http.logout().logoutUrl("/perform_logout");
		http.logout().deleteCookies("JSESSIONID");

		http.exceptionHandling().accessDeniedPage("/403");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
