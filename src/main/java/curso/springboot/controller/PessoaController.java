package curso.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;

//anotação necessária para indicar ao springboot que essa classe é um controller
@Controller
public class PessoaController {

	/*
	 * Injetando a interface que extends de CrudRepository para ter acessos aos
	 * métodos de persistência
	 */
	@Autowired
	private PessoaRepository pessoaRepository;

	/*
	 * método para interceptar a requisição de qualquer link contendo
	 * "/cadastropessoa" e retornar para a página salva em "cadastro/cadastropessoa"
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public String inicio() {
		return "cadastro/cadastropessoa";
	}

	// método para salvar e retornar para a mesma tela ao interceptar a url
	// "salvarpessoa"
	// método alterald para modelandview para salvar e ja retornar a lsita com dados
	// inseridos
	@RequestMapping(method = RequestMethod.POST, value = "/salvarpessoa")
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
		// setando a view de retorno no modelandview
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		// buscando todos os usuários
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		// sentando a lista de usuários no modelandview, a lista que será na view
		// ${pessoas}
		modelAndView.addObject("pessoas", pessoasIt);
		// efetuando a resposta com view e model
		return modelAndView;
	}

	/*
	 * método para listar pessoas na view, para isso é necessário o método ser do
	 * tipo objeto ModelAndView, essaclasse permite que a controller retorne um
	 * model e view no mesmo valor de retorno
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
	public ModelAndView pessoas() {
		// setando a view de retorno no modelandview
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		// buscando todos os usuários
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		// sentando a lista de usuários no modelandview, a lista que será na view
		// ${pessoas}
		modelAndView.addObject("pessoas", pessoasIt);
		// efetuando a resposta com view e model
		return modelAndView;
	}
}
