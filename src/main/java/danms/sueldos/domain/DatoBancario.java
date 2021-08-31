package danms.sueldos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class DatoBancario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //valor autonumerico
	@Column(name="ID_DATO_BANCARIO")
	private Integer id;
	private String nombreBanco;
	private String numeroCuenta;
	
	@OneToOne
	@JoinColumn(name="ID_EMPLEADO")
	private Empleado empleado;
	
	public DatoBancario() {
		super();
	}
	public DatoBancario(String nombreBanco, String numeroCuenta, Empleado empleado) {
		super();
		this.nombreBanco = nombreBanco;
		this.numeroCuenta = numeroCuenta;
		this.empleado = empleado;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public String getNombreBanco() {
		return nombreBanco;
	}
	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	
	
	

}
