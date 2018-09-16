package br.bemobi.controller;

import br.bemobi.entity.URLShortner;
import br.bemobi.response.ShortURLResponse;
import br.bemobi.service.ShortURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class ShortnerURLController{

	private ShortURLService shortURLService;

	@Autowired
	public ShortnerURLController(ShortURLService shortURLService){
		this.shortURLService = shortURLService;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	ResponseEntity<?> saveURL(@RequestBody @Validated URLShortner shortner) throws Exception {
		HashMap<String, String> statistics = new HashMap<>();
		long startTime = System.currentTimeMillis();
		long time_taken;

		shortURLService.save(shortner);
		
		long endTime   = System.currentTimeMillis();
		time_taken = endTime - startTime;
		statistics.put("time_tiken", time_taken + "ms");

		ShortURLResponse response = new ShortURLResponse(shortner.getAlias(), shortner.getOriginalURL(), statistics);

		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> readURL(@RequestParam String alias) throws Exception {
		return new ResponseEntity<>(shortURLService.findOriginalURLByShort(alias),HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET,value = "/topURL")
	ResponseEntity<?> top10URL() throws Exception {
		return new ResponseEntity<>(shortURLService.findTopTen(), HttpStatus.OK);
	}
}