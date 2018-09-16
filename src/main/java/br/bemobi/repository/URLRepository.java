package br.bemobi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.bemobi.entity.URLShortner;


@Repository
public interface URLRepository extends JpaRepository<URLShortner, Long> {

	URLShortner findByoriginalURL(String originalURL);
	URLShortner findByalias(String alias);
	List<URLShortner> findTop10ByOrderByViewsDesc();

}
