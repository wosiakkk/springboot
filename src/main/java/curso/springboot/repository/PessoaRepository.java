package curso.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Pessoa;

/*Como a interface extends o crud repository do spring, ela já vem com diversos métodos de CRUD prontos,
 * também não há a necessidade de ter uma classe implementando essa interface PessoaRepository tendo em vista que o spring data 
 * já tem essa implementação*/
@Repository // definindo que esta interface é um repository
@Transactional // anotação necessária já que a interface irá modificar BD
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

	/*Método para buscar usuários por nome, como não é um método padrão do
	 * CrudRepository, fazemos uma query customizada em JPQL*/
	@Query(value = "select p from Pessoa p where p.nome like %?1%")
	List<Pessoa> findPessoaByName(String nome);
	//query para o camp de pesquisa com nome e sexo
	@Query(value = "select p from Pessoa p where p.nome like %?1% and p.sexopessoa = ?2")
	List<Pessoa> findPessoaByNameAndSex(String nome, String sexopessoa);
}
