package br.mobi.reponse;
//Cçasse responsavel por montar a resposta da API
import java.util.HashMap;

public class ShortURLResponse {
	public String alias;
	public String url;
	public HashMap<String, String> statistics;
	public String shortenedURL;
	
	public ShortURLResponse(String alias,String url, HashMap<String, String> statistics) {
		this.alias = alias;
		this.shortenedURL = "localhost:8080/HireMe/"+ this.alias;
		this.url = url;
		this.statistics = statistics;
	}
}
