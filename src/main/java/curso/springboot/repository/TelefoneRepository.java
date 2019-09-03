package curso.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Pessoa;
import curso.springboot.model.Telefone;

@Repository
@Transactional
public interface TelefoneRepository  extends CrudRepository<Telefone, Long>{
	
	@Query("select t from Telefone t where t.pessoa = ?1")
	List<Telefone> listarTodosPorIdDeUsuario(Pessoa id);
	
}
