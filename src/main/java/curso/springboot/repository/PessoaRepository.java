package curso.springboot.repository;

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

}
