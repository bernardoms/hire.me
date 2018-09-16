package br.bemobi.service;

import br.bemobi.entity.URLShortner;
import br.bemobi.repository.URLRepository;
import br.bemobi.utils.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

@Service
public class ShortURLService {

    private URLRepository urlRepository;

    @Autowired
    public ShortURLService(URLRepository urlRepository){
        this.urlRepository = urlRepository;
    }

    public void updateURLView(URLShortner urlShort) {
        long views = urlShort.getViews() + 1;
        urlShort.setViews(views);
        urlRepository.save(urlShort);
    }

    public void save(URLShortner shortner) throws Exception {

        if (urlRepository.findByalias(shortner.getAlias()) != null) {
            throw new Exception("CUSTOM ALIAS ALREADY EXISTS");
        }

        if(shortner.getAlias() == null){

            long index = urlRepository.count();

            while(urlRepository.findByalias(Base62.base10To62(index)) != null)
            {
                index = urlRepository.count() + 1;
            }
            shortner.setAlias(Base62.base10To62(index));
        }

        urlRepository.save(shortner);
    }

    public URLShortner findOriginalURLByShort(String shorted) throws Exception {

        URLShortner shortURL  = urlRepository.findByalias(shorted);

        if (shortURL == null) {
            throw new Exception("SHORTENED URL NOT FOUND");
        }

        return shortURL;

    }

    public List<URLShortner> findTopTen() {
        return urlRepository.findTop10ByOrderByViewsDesc();
    }
}
