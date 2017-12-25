package br.bemobi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.bemobi.entity.URLShortner;
import br.bemobi.repository.URLRepository;
import br.bemobi.utils.Base62;

@Service
public class ShortURLService {
		//Faz a ligação com o repositorio de URL's onde será encarregado por recuperar a URL
		@Autowired
	    private URLRepository shortenerRepository;
	  	//Url que está sendo encurtada
	   	public String originaltUrl;
	    public String id;
	    public String customName;
	    //Atualiza a view do serviço
	    public void updateURLView(URLShortner urlShort)
	    {
			long views = urlShort.getViews() + 1;
			urlShort.setViews(views);
			shortenerRepository.save(urlShort);	    	
	    }
	    
	    public void save(String customName, String originalURL) throws Exception
	    {
	    	this.customName = customName;
	    	this.originaltUrl = originalURL;
	    	URLShortner shortURL = new URLShortner(this.customName ,this.originaltUrl);
	    	//Caso não tenha sido fornecido um custom Alias
	    	if(this.customName == null)
	    	{
	    		long index = shortenerRepository.count();
	    		this.customName = convertURLUsingBase62(index);
	    		/*
	    		Validação para verificar se o hash gerado é igual a algum que já existe no banco
	    		Caso exista é gerado um novo hash
	    		*/
	    		while(shortenerRepository.findByalias(this.customName) != null)
	    		{
	    			index = shortenerRepository.count() + 1;
	    			this.customName = convertURLUsingBase62(index);			
	    		}
	    		shortURL.setAlias(this.customName);
	    		shortenerRepository.save(shortURL);
	    	}
	    	//Caso tenha sido fornecido alias salvar com o alias fornecido
	    	else if(this.customName != null)
	    	{
		    	if(shortenerRepository.findByalias(customName) != null)
		    	{
		    		throw new Exception("CUSTOM ALIAS ALREADY EXISTS");
		    	}
	    		//URLShortner shortURL = new URLShortner(this.customName ,this.originaltUrl);
	    		shortenerRepository.save(shortURL);
	    	}
	    }   
	    public String convertURLUsingBase62(long urlHash)
	    {
	    	return Base62.base10To62(urlHash);
	    }
	    public String getCustomName()
	    {
	    	return this.customName;
	    }
	    
	    public String getOriginalUrl()
	    {
	    	return this.originaltUrl;
	    }
	    
	    public URLShortner findOriginalURLByShort(String shorted) throws Exception
	    {
	    	URLShortner shortURL = new URLShortner();
	    	shortURL = shortenerRepository.findByalias(shorted);
	    	//Não foi encontrado URL
	    	if(shortURL == null)
	   	    {
	    		throw new Exception("SHORTENED URL NOT FOUND");
	   	    }
			return shortURL;

	    }	
	    
	    public List<URLShortner> findTopTen()
	    {
	    	List<URLShortner> urlList = shortenerRepository.findTop10ByOrderByViewsDesc();
			return urlList;
	    }
	 
}
