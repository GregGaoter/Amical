package app.gaugiciel.amical.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Spring
@Component
//Lombok
@NoArgsConstructor
@Getter
@Setter
public class SpotFormDto {

	private String nomSpot;
	private String tagQ;

}
