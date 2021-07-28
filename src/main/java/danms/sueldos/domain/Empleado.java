package danms.sueldos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.lang.NonNull;

@Entity
public class Empleado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //valor autonumerico
	@Column(name="ID_EMPLEADO")
	private Integer id;
	private String email;
	
	@NonNull
	@OneToOne(mappedBy = "empleado")
	private DatoBancario datoBancario;
	
	public Empleado() {
		super();
	}
	public Empleado(String email,DatoBancario datoBancario) {
		super();
		this.email = email;
		this.datoBancario = datoBancario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public DatoBancario getDatoBancario() {
		return datoBancario;
	}
	public void setDatoBancario(DatoBancario datoBancario) {
		this.datoBancario = datoBancario;
	}
	
	

}
