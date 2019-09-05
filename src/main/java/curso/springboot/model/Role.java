package curso.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

/*A implementação da interface GrantedAuthority da acesso a recursos do SpringSecurity.
 * O padrão dos nomes da roles  do spring security no banco de dados é 'ROLE_ADMIN, ROLE_GERENTE, etc*/

@Entity
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	//Nome do acesso que será retornado no método da interface GrantedAuthority getAuthority
	private String nomeRole;
	
	@Override //método para retornar o autorização
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.nomeRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeRole() {
		return nomeRole;
	}

	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}
	
	
	
}
