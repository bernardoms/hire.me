## Tecnologias Utilizadas no Projeto
* Linguagem de programação: Java
* Framework: Spring boot
* Banco de dados: H2 embedded
* Front-end: Bootstrap

## Framework utilizado
A aplicação foi desenvolvida com a utilização do framework spring boot, pois além de ter conhecimentos maiores com o JAVA 
o spring boot simplifica a programação a ser feita, conseguindo entregar a tarefa mais rapido.

## Algoritmo para gerar o hash
Para gerar o hash das urls encurtadas foi utilizado um encode em Base 62. A ideia é ter um hash significativamente menor que a url original.
Na base 62 temos 26 letras minusculas (a-z), 26 maiusculas (A-Z) e 10 numeros (0-9), 26 + 26 + 10 = 62. Com isso temos um algoritimo simples de implementar
e bem eficaz para se gerar diversas url's encurtadas.
Foi utilizado o numero de instancias de url na base de dados para usar como indice ao ser gerado o hash com a base62.

## Testes unitários
Os testes unitários foram feitos utilzando o JUnit com MockMvc, pois com essas ferramentas foi possivel fazer o teste unitario de forma simples
e rapida, uma vez que o MockMvc simula requisções GET e POST ao controler.


## Top 10 urls
Foi feita a consulta das 10 urls mais acessadas utilizando o spring data.

## Diagramas
Os diagramas foram feitos utilizando a ferramenta https://www.websequencediagrams.com/

## endpoints presentes no projeto e seus respectivos exemplos:

Criar url encurtada com um alias

```sh
curl -H "Content-Type: application/json" -X POST "http://localhost:8080/create?url=http://bemobi.com.br&alias=bemobi"
```

Criar uma url encurtada sem alias(utilizando base62)

```sh
curl -H "Content-Type: application/json" -X POST "http://localhost:8080/create?url=http://bemobi.com.br
```

Consultar um alias exibindo sua url original:

```sh
curl "http://localhost:8080/read?alias=bemobi
```

Ver os top 10 mais visitados:

```sh
curl "http://localhost:8080/topURL"
```

