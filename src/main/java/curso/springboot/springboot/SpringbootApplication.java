package curso.springboot.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "curso.springboot.model") //anotação para informar ao spring boot onde estão as entidades
@ComponentScan(basePackages = {"curso.*"}) //forca o spring a mapear todas os pacotes, pois pode ocorrer do spring não encontrar as classes controllers, e isso força ele mapemar todas
@EnableJpaRepositories(basePackages = {"curso.springboot.repository"}) //ativando o mapemando dos repositories
@EnableTransactionManagement //ativando o recurso de trnasações usados na persistência de dados
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
