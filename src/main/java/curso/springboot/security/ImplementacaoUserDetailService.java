package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Usuario;
import curso.springboot.repository.UsuarioRepository;

/*Classe de serviço para implementar a UserDetailService que é
 * para utilizar os recursos de consulta ao banco de dados para o Spring Security.
 * Essa classe será passada como parâmetro para a classe WebConfigSecurity.
 * é necessário a a anotação @Service para dizer ao Spring que essa classe é um serviço.
 * Já a anotação @Transaction é necessária para a classe poder carregar no BD os acessos*/

@Service
@Transactional
public class ImplementacaoUserDetailService implements UserDetailsService{

	//Injeção do repository 
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	//a interface só possui esse método para ser implementado para auxiliar na validação de login
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//realiznado a busca do usuário pelo username
		Usuario usuario = usuarioRepository.findUserByLogin(username);
		//validação se um usuário foi encontrado
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado");
		}
		/*No retorno será utilizado um objeto do spring(userdetail) no qual é passado usuário, senha e os acessos e também
		 * os demais retornos dos métodos sobrescritos da interface como isenable, isaccountnonexpired, etc*/
		return new User(usuario.getLogin(), usuario.getSenha(), usuario.isEnabled(), usuario.isAccountNonExpired(), usuario.isCredentialsNonExpired(), usuario.isAccountNonLocked(), usuario.getAuthorities());
	}

}
