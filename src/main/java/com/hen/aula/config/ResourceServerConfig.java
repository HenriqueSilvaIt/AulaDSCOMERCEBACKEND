package com.hen.aula.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ResourceServerConfig {

	/*Ele puxa essa variavel do aplication.properties e chama no método
	* corsConfigurationSource 	 */

	/*CORS é um recurso que os navegadores tem que por padrão
	 * eles não deixam que um recurso sejam acessado por um host que não esteja autorizado
	 * por exemplo um FRONT END n pode acessar uma api que n está autorizado
	 *
	 * O que colocamos no application.properties são os hosts que serão aceito,
	 * no caso do postman (ele n é um navegador) ele tem um privilégio que ele
	 * consegue acessar os recurso, mesmo n sendo um dos hosts liberado aqui
	 * , mas se for aplicação rodando no navegador só vai conseguir acessar
	 * se tiver apontado */
	@Value("${cors.origins}")
	private String corsOrigins;



	/*Método  para liberar o acesso ao banco H2*/
	@Bean
	@Profile("test") // só no perfil do teste
	@Order(1) // PRIORIDADE
	public SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {

		http.securityMatcher(PathRequest.toH2Console()).csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); /*
				esse disable do frame é para n traza a  interface grafic do H2*/
		return http.build();
	}


	@Bean
	@Order(3)
	public SecurityFilterChain rsSecurityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable());
		/*
        Aqui ele acessa esse objeto http que é passado como arugmento e
         é do tipo HttpSecurity, e ele chama o csrf.disable,  que desabiltia a
         proteção contra ataques do tipo csrf (que é um ataque
         contra aplicações que gravam dados na sessão), isso pode gerar
         uma vulnerabilidade que as pessoas podem usar isso para acessar
         recurso da sua aplicação de forma indevida, porém como nosso backend
         é um backend que está liberando uma API REST e API REST n guarda  estado
              em sessão n precisamos nos preocupar com isso e podemos desabilitar
		 */
		http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
		    /*essa configuração authorizeHttpRequests é para auth.anyRequest() isso quer dizer qualquer
        requisição que tiver ai temos vários métodos quando você coloca .  como .hasAnyRole
        nesse caso vamos deixar o permitAll, porque vamos fazer
        a resitrção de acesso a nível de rota e não nessa classe a nível global do spring
         se colocar authorize.authenticated(), vai apontar que tudo precisa de login para acessar,
         mas é melhor deixar tudo ok, e lá nos end point configurar qual perfil tem acesso a cada rota(end point)
         é mais fácil customizar no nível de rota ou serviç odo que aqui que é geral para toda aplicação*/
		http.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));
		/* o código abaixo, faz a chamada do método do cors e faz a liberação do cors somente para host autorizado*/
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
		return http.build();
	}

	/*Configuração do jwt para que funcione também no resource server (liberaççao de recurso)*/
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
		grantedAuthoritiesConverter.setAuthorityPrefix("");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

	/*CORS é um recurso que os navegadores tem que por padrão
	* eles não deixam que um recurso sejam acessado por um host que não esteja autorizado
	* por exemplo um FRONT END n pode acessar uma api que n está autorizado*/

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		String[] origins = corsOrigins.split(","); /*
		variavel cors origin do aplicattion,properties e da um split com a
		virgula, porque está separa o host por virgula lá  e tem um array com os hosts*/

		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOriginPatterns(Arrays.asList(origins)); // seta origins permitida
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH")); // métodos permitidos
		corsConfig.setAllowCredentials(true); // diz que pode usar credenciais
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));// autoriza usar alguns cabeçalho

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	/*CORS é um recurso que os navegadores tem que por padrão
	 * eles não deixam que um recurso sejam acessado por um host que não esteja autorizado
	 * por exemplo um FRONT END n pode acessar uma api que n está autorizado
	 *  bean.setOrder(Ordered.HIGHEST_PRECEDENCE);*/

	/*Método abaixo fala que o método acima tem que ser instância
	*    com precedência máxima ou seja ante de tudo */
	@Bean
	FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}