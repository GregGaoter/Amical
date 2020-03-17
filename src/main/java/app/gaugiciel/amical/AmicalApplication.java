package app.gaugiciel.amical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmicalApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(AmicalApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Start {}()", "main");
		SpringApplication.run(AmicalApplication.class, args);
	}

}
