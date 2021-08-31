package danms.sueldos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Transactional(readOnly= true)
public class Empleado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //valor autonumerico
	@Column(name="ID_EMPLEADO")
	private Integer id;
	private String mail;
	
	public Empleado() {
		super();
	}
	public Empleado(String email,DatoBancario datoBancario) {
		super();
		this.mail = email;
	}
	
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public String getEmail() {
		return mail;
	}
	public void setEmail(String email) {
		this.mail = email;
	}
}
