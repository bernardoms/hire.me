package br.bemobi.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="url")
public class URLShortner implements Serializable{

	private static final long serialVersionUID = 1L;
		@Id
	    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	    @JsonIgnore
	    private Long id;
	    private String originalURL;
	    private String alias;
	    private Long views;

	    public URLShortner(String alias, String original)
	    {
	    	this.alias = alias;
	    	this.originalURL = original;
	    	this.views = 0L; 
	    }
	    
	    public URLShortner() {
			
		}
	    public void setAlias(String alias)
	    {
	    	this.alias = alias;
	    }
	    public String getAlias()
	    {
	    	return alias;
	    }
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getOriginalURL() {
			return originalURL;
		}

		public void setOriginalURL (String originalURL) {
			this.originalURL = originalURL;
		}

		public Long getViews() {
			return views;
		}

		public void setViews(Long views) {
			this.views = views;
		}
	    
}
