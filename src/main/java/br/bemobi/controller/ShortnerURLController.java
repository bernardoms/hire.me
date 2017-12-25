package br.bemobi.controller;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.bemobi.entity.URLShortner;
import br.bemobi.repository.URLRepository;
import br.bemobi.service.ShortURLService;
import br.mobi.reponse.ShortURLResponse;

@RestController
public class ShortnerURLController{

	@Autowired
	private ShortURLService service;

	//Requisicao POST para a API, Grava o url passando a URL e o alias desejado, caso alias for nulo é criado um hash.
	@RequestMapping(method = RequestMethod.POST,value = "/create")
	ResponseEntity<?> saveURL(@RequestParam (value = "url") String url,@RequestParam(value = "alias",required = false) String alias) throws MalformedURLException
	{
		//TODO Remover e incluir uma validacao na pagina
		if(alias != null)
		{
			if(alias.trim().equals(""))
			{
				alias = null;
			}
		}
		
		//Hash para montar a msg JSON de estatistica com o tempo gasto para encurtar a url
		HashMap<String, String> statistics = new HashMap<>();
		long startTime = System.currentTimeMillis();
		long time_taken;
		
		/*
		Cria uma URL com o protocolo correto(http://) 
		caso não esteja formatada corretamente é exibido erro de URL mau formatada
		 */
		try{
			new URL(url);
		}
		catch(Exception e)
		{
			HashMap<String, String> error = new HashMap<>();
			error.put("url", url);
			error.put("error_code", "003");
			error.put("description", "INVALID_URL_FORMAT");
			return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		}
		//Tenta encurtar a url;
		try{
			service.save(alias, url);
		}
		catch(Exception e)
		{
			HashMap<String, String> error = new HashMap<>();
			error.put("alias", alias);
			error.put("error_code", "001");
			error.put("description", e.getMessage());
			return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		}
		
		long endTime   = System.currentTimeMillis();
		time_taken = endTime - startTime;
		statistics.put("time_tiken", time_taken + "ms");
		service.getOriginalUrl();
		service.getCustomName();
		ShortURLResponse response = new ShortURLResponse(service.getCustomName(), service.getOriginalUrl(), statistics);
		//Ocorrendo tudo bem retorna a resposta de sucesso
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	//Requisicao GET para a API, recupera o url passando o alias
	@RequestMapping(method = RequestMethod.GET,value = "/read")
	ResponseEntity<?> readURL(@RequestParam String alias) throws Exception
	{
		URLShortner urlShort = new URLShortner();
		urlShort = service.findOriginalURLByShort(alias);
		return new ResponseEntity<>(urlShort,HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET,value = "/topURL")
	ResponseEntity<?> top10URL() throws Exception
	{
		
		List<URLShortner> urlList = service.findTopTen();
		return new ResponseEntity<>(urlList,HttpStatus.OK);
		
	}
	

}