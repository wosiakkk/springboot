package curso.springboot.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = "curso.springboot.model") //anotação para informar ao spring boot onde estão as entidades
@ComponentScan(basePackages = {"curso.*"}) //forca o spring a mapear todas os pacotes, pois pode ocorrer do spring não encontrar as classes controllers, e isso força ele mapemar todas
@EnableJpaRepositories(basePackages = {"curso.springboot.repository"}) //ativando o mapemando dos repositories
@EnableTransactionManagement //ativando o recurso de trnasações usados na persistência de dados
@EnableWebMvc //para poder utilizar métodos do  WebMvcConfigurer
public class SpringbootApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	
	/*método da interface WebConfigurer utilizado para redirecionar páginas web.
	 * Como o spring security cria automaticamente uma tela de login para fins de testes
	 * e implemntação, não é possível customiza-la, por isso iremos criar a nossa própria tela
	 * de login e realizar o redirecionamento*/
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//mapemando a url de login e setando a página que deve ser redirecionada
		registry.addViewController("/login").setViewName("/login");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
	}
	
}
