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
@RequestMapping("/HireMe")
public class RedirectURLController {

    private ShortURLService shortUrlService;

    @Autowired
    public RedirectURLController(ShortURLService shortUrlService){
        this.shortUrlService = shortUrlService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{alias}")
    ResponseEntity<?> redirectURL(@PathVariable("alias") String alias) throws Exception {

        URLShortner url = shortUrlService.findOriginalURLByShort(alias);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", url.getOriginalURL());

        shortUrlService.updateURLView(url);

        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }
}
