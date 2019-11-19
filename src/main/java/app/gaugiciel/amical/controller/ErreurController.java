package app.gaugiciel.amical.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErreurController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping(value = "/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());
			Map<Integer, String> httpStatus = new HashMap<>();
			httpStatus.put(HttpStatus.FORBIDDEN.value(), "403");
			httpStatus.put(HttpStatus.NOT_FOUND.value(), "404");
			if (httpStatus.containsKey(statusCode)) {
				return httpStatus.get(statusCode);
			}
		}
		return "error";
	}

}
