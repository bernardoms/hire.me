package br.bemobi.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="URL")
public class URLShortner implements Serializable{

	private static final long serialVersionUID = 1L;

		@Id
	    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	    @JsonIgnore
	    private Long id;
		@Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", message = "A Url informada deve conter https://")
	    private String originalURL;
	    private String alias;
	    private Long views = 0L;

	    public URLShortner(String alias, String original)
	    {
	    	this.alias = alias;
	    	this.originalURL = original;
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
