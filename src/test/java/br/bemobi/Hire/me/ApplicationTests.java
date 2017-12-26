package br.bemobi.Hire.me;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.bemobi.service.ShortURLService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ShortURLService service;
	
	//Teste para adicionar a URL, esperando como resposta um json com o alias e a url(url original)
	@Test
	public void shouldCreateURLWithoutAlias() throws Exception
	{
		this.mockMvc.perform(post("/short").param("url", "http://www.bemobi.com.br")).andExpect(status().isCreated())
        .andExpect(jsonPath("$.alias").value(service.getCustomName()))
        .andExpect(jsonPath("$.url").value("http://www.bemobi.com.br"));
	}
	//Teste para adicionar a URL com um alias, esperando como resposta um json com o alias passado e a url(url original)
	@Test
	public void shouldCreateURLWithAlias() throws Exception
	{
		this.mockMvc.perform(post("/short").param("url", "http://www.bemobi.com.br").param("alias","bemobi")).andExpect(status().isCreated())
        .andExpect(jsonPath("$.alias").value("bemobi"))
        .andExpect(jsonPath("$.url").value("http://www.bemobi.com.br"));
	}
	//Teste para verificar se foi retornado o erro caso o alias já tenho sido usado
	@Test
	public void shouldGiveAlreadyUsedAliasError() throws Exception
	{
		this.mockMvc.perform(post("/short").param("url", "http://www.bemobi.com.br").param("alias","bemobi")).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.alias").value("bemobi"))
        .andExpect(jsonPath("$.description").value("CUSTOM ALIAS ALREADY EXISTS"))
        .andExpect(jsonPath("$.error_code").value ("001"));
	}
	//Teste para validar se o formato da URL está correto
	@Test
	public void shouldGiveInvalidURLError() throws Exception
	{
		this.mockMvc.perform(post("/short").param("url", "www.bemobi.com.br").param("alias","bemobi2")).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.url").value("www.bemobi.com.br"))
        .andExpect(jsonPath("$.description").value("INVALID_URL_FORMAT"))
        .andExpect(jsonPath("$.error_code").value ("003"));
	}
	
	//Testes de GET
	@Test
	public void shouldReadURL() throws Exception
	{
		this.mockMvc.perform(get("/short").param("alias","bemobi")).andExpect(status().isOk())
        .andExpect(jsonPath("$.originalURL").value("http://www.bemobi.com.br"))
        .andExpect(jsonPath("$.alias").value("bemobi"))
        .andExpect(jsonPath("$.views").value("0")); //Views pois ainda não foi vsitada
	}
	
	//Teste de redirecionamento da URL encurtada para a original
	@Test
	public void shouldRedirectToURL() throws Exception
	{
		this.mockMvc.perform(get("/HireMe/bemobi").param("alias","bemobi")).andExpect(status().isFound())
        .andExpect(redirectedUrl("http://www.bemobi.com.br"));
	}
	//Teste para verificar se o alias não foi encontrado retornando erro
	@Test
	public void shouldNotFoundAlias() throws Exception
	{
		this.mockMvc.perform(get("/HireMe/aliasErrado").param("alias","bemobi")).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error_code").value("002"))
        .andExpect(jsonPath("$.description").value("SHORTENED URL NOT FOUND"));
	}
}
