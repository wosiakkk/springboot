package curso.springboot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import curso.springboot.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	//query personalizada para buscar usuario pelo login
	@Query("select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String login);
}
