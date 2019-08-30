package curso.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;
import javassist.expr.NewArray;

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
	 * Método alterado para modelandview, para retornar um objeto vazio na primeira execução(por causa da edição)
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		// setando a view de retorno no modelandview
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		//add um objeto vazio para a primera execução
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}

	// método para salvar e retornar para a mesma tela ao interceptar a url
	// "salvarpessoa"
	// método alterald para modelandview para salvar e ja retornar a lsita com dados
	// inseridos
	/*os 2 ** antes da URL salvarpessoa servem para a controller ignorar qualquer coisa que venha antes dessa url, 
	 * pois quando editamos algum registro a url se altera 
	 * ficando /editarpessoa/salvarpessoa e dando erro ao salvar, pois não consegue interceptar essa url.*/
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
		// setando a view de retorno no modelandview
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		// buscando todos os usuários
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		// sentando a lista de usuários no modelandview, a lista que será na view
		// ${pessoas}
		modelAndView.addObject("pessoas", pessoasIt);
		//add um objeto vazio para a o form funcionar corretamente, por causa da edição
		modelAndView.addObject("pessoaobj", new Pessoa());
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
		//add um objeto vazio para a o form funcionar corretamente, por causa da edição
		modelAndView.addObject("pessoaobj", new Pessoa());
		// efetuando a resposta com view e model
		return modelAndView;
	}
	
	
	/*Método para capturar a requisição de edição de pessoa, pegando seu id com 
	 * a anotação @PathVariable, informando o nome o o tipo do parâmetro
	 * configurado na view como @{/editarpessoa/{idpessoa}(idpessoa=${pessoa.id})} 
	 * Já a anotação @GetMapping é a forma resumida de @RequestMapping(method = RequestMethod.GET) 
	 * Na anotação getmapping o parâmetro que vem por get na url deve ser mostrado entre{}*/
	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
		// setando a view de retorno no modelandview
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		//carregando o objeto pessoa com a busca pelo id passado como parâmetro, lembrando que o find do spring retorna optional
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		//add o objeto pessoa ao modelandview, o método get() do optional pega o objeto do tipo pessoa pesquisado
		modelAndView.addObject("pessoaobj", pessoa.get());
		//retornando o modelandview preenchido
		return modelAndView;
	}
	
	@GetMapping("/excluirpessoa/{idpessoa}")
	public ModelAndView remover(@PathVariable("idpessoa") Long idpessoa) {
		// setando a view de retorno no modelandview
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		//removendo o usuário através do id passado por parâmetro
		pessoaRepository.deleteById(idpessoa);
		// buscando todos os usuários novamente após a exclusão
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
		//add um objeto vazio para a o form funcionar corretamente, por causa da edição
		modelAndView.addObject("pessoaobj", new Pessoa());
		// efetuando a resposta com view e model
		return modelAndView;
	}
	
	/*Método para o campo de busca por nome. @PostMapping é a abreviação de @RequestMapping(method = RequestMethod.POST) 
	 * A anotação RequestParam pega o parâmetro enviado pelo action do form via post*/
	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
		//add um objeto vazio para a o form funcionar corretamente, por causa da edição
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}
}
