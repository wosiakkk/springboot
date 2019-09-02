package curso.springboot.controller;

import java.util.List;
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
import curso.springboot.model.Telefone;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.TelefoneRepository;
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
	@Autowired
	private TelefoneRepository telefoneRepository; 
	
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
		//carregando a lista de pessoas da tabela
		// buscando todos os usuários
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
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
	
	/*Método para interceptar a requisição ao clicar em um nome de usuário na datatable.*/
	@GetMapping("/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {
		//carregando o objeto pessoa com a busca pelo id passado como parâmetro, lembrando que o find do spring retorna optional
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		//setando o retorno que será para a tela de telefones
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		//Pegando a lista de telefones do usuário selecionado para mostrar na data table
		List<Telefone> listaTelefone = telefoneRepository.listarTodosPorIdDeUsuario(pessoa.get());
		//add ao Model a lsita de telefones do usário
		modelAndView.addObject("telefones", listaTelefone);
		//add o objeto pessoa ao modelandview, o método get() do optional pega o objeto do tipo pessoa pesquisado
		modelAndView.addObject("pessoaobj", pessoa.get());
		//retornando o modelandview preenchido
		return modelAndView;
	}
	
	/*Método que intercepta um objeto telefone e um long que será sua fk referente ao usuário
	 * dono do telefone*/
	@PostMapping("**/addfonePessoa/{pessoaid}")
	public ModelAndView addfonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		//carregando a pessoa  refenrete a fk do telefone
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		//ionjentando a pessoa no obj telefone
		telefone.setPessoa(pessoa);
		//salvando o telefone
		telefoneRepository.save(telefone);
		List<Telefone> listaTelefone = telefoneRepository.listarTodosPorIdDeUsuario(pessoa);
		//add ao Model a lsita de telefones do usário
		modelAndView.addObject("telefones", listaTelefone);
		modelAndView.addObject("pessoaobj", pessoa);
		
		return modelAndView;
	}
	
	@GetMapping("/excluirtelefone/{idtelefone}")
	public ModelAndView removerTelefone(@PathVariable("idtelefone") Long idtelefone) {
		// setando a view de retorno no modelandview
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		//recuperando o obj pessoa do telefone antes da exclusão para poder setar na tela novamente
		Pessoa pessoaobj = telefoneRepository.findById(idtelefone).get().getPessoa();
		//removendo o telefone através do id passado por parâmetro
		telefoneRepository.deleteById(idtelefone);
		// buscando todos os telefones novamente após a exclusão
		Iterable<Telefone> telefonesIt = telefoneRepository.findAll();
		modelAndView.addObject("telefones", telefonesIt);
		//add um objeto vazio para a o form funcionar corretamente, por causa da edição
		modelAndView.addObject("pessoaobj", pessoaobj);
		// efetuando a resposta com view e model
		return modelAndView;
	}
	
	
}
