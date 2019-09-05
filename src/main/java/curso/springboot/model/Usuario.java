package curso.springboot.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*Não utilizar o nome use nas classes a serem usada pelo spring security, pois ele usa algumas palavras reservadas e a entidade não é criada no banco com esse nome.
 *A classe deve implementar a interface UserDatails, que tem diversos métodos para auxiliar */


@Entity
public class Usuario implements UserDetails{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String login;
	private String senha;
	
	/*Um usuário pode ter um ou muitos acessos.
	 * Já a anotação JoinTable é apra criar automaticamente uma table associativa, pois
	 * o relacionamento é muitos para muitos()muitos acessos tem muitos usuários e 
	 * muitos usuários tem muitos acessos), o parâmetro name define o nome da tabela.
	 * Ja o atributo joincolumn com a sua anotação é para identificar(amarrar) para uqal usuário nessa tabela
	 * vai ser acessado, sendo o nome da coluna usuario_id da tabela usuarios_role referenciando a coluna id da tabela
	 * usuario.
	 * Já a anotação inverseJoinColumn é o join column refenrete a outra tabela da classe Role, na qual será criada 
	 * uma coluna com o nome role_id na tabela associativa que irá referênciar a coluna id na tabela role*/
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_role", 
		joinColumns = @JoinColumn(name = "usuario_id",
				referencedColumnName = "id",table = "usuario"),
					inverseJoinColumns = @JoinColumn(name = "role_id",
							referencedColumnName = "id", table = "role"))
	private List<Role> acessos;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override //Retorna os acessos
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return acessos;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
