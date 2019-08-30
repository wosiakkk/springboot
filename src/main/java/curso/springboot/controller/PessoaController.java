package curso.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;

//anotação necessária para indicar ao springboot que essa classe é um controller
@Controller
public class PessoaController {
	
	/*Injetando a interface que extends de CrudRepository para ter acessos aos métodos de persistência*/
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
	
	//método para salvar  e retornar para a mesma tela ao interceptar a url "salvarpessoa"
	@RequestMapping(method = RequestMethod.POST, value = "/salvarpessoa")
	public String salvar(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
		return "cadastro/cadastropessoa";
	}
}
