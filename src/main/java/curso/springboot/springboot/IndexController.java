package curso.springboot.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*Anotação controller indica que a classe será um controller de mvc*/

@Controller
public class IndexController {
	
	@RequestMapping("/") //método que irá interceptar a requisição e retornar para o index.html
	public String index() {
		return "index";
	}
	
}
