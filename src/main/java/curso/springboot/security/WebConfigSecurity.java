package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*Classe de configuração de segurança, é preciso extends WebSecurityConfigurerAdapter
 * e das anotações @Configuration @EnableWebSecurity*/
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	//injetando a implementação do UserDetail
	@Autowired
	private ImplementacaoUserDetailService implementacaoUserDetailService;
	
	
	@Override //Configura as solicitações de acesso por HTTP
	protected void configure(HttpSecurity http) throws Exception {
		//cada métdo chamado abaixo é uma ativação de configuração
		http.csrf()
		.disable() //desabilitando as configurações padrões do Spring
		.authorizeRequests() //Permitir restringir acessos
		.antMatchers(HttpMethod.GET, "/").permitAll() //Permite que qualquer usuário acesse a página inicial do sistema sem precisar estar logado
		.anyRequest().authenticated()
		.and().formLogin().permitAll() //Permite acesso a qualquer usuário ao form de login 
		.and().logout() //Mapeia a URL de saída do sistema (logout) e invalida usuário autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")); 
	}
	
	@Override //Cria a autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*Realizando a autenticação com o banco de dados e com criptografia na senha*/
		auth.userDetailsService(implementacaoUserDetailService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		
		
		//Código apra teste em memória, inutilizando após a implementação do userdetailservice
		/*auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()) //configurando o encode do password, utiliznado texto plano, o que nao é seguro, usado aenas para testes de implementação
		.withUser("bruno")
		.password("123")
		.roles("ADMIN");*/
	}
	
	@Override //Ignora UL específicas
	public void configure(WebSecurity web) throws Exception {
		//Ignorando tudo que tem na pasta de css para poder utiliza-las sem restrições
		web.ignoring().antMatchers("/materialize/**");
	}
	
}
