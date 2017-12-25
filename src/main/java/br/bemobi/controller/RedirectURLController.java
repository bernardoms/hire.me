package br.bemobi.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import br.bemobi.entity.URLShortner;
import br.bemobi.service.ShortURLService;

@Controller
public class RedirectURLController {

	@Autowired
	private ShortURLService service;
	
	@RequestMapping(method = RequestMethod.GET,value = "/HireMe/{alias}")
	ResponseEntity<?> redirectURL(@PathVariable("alias") String alias)
	{	
		URLShortner url = null;
		try {
			url = service.findOriginalURLByShort(alias);
		} catch (Exception e) {
			HashMap<String, String> error = new HashMap<>();
			error.put("error_code", "002");
			error.put("description", e.getMessage());
			//Cria a mensagem de error
			return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", url.getOriginalURL());    	
		//incrementa o numero de views para aquele site reduzido
		 service.updateURLView(url); 
		//Redireciona o usuario para a pagina desejada
		 return new ResponseEntity<String>(headers,HttpStatus.FOUND);
	}
}
