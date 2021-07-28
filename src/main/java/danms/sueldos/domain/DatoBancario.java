package danms.sueldos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DatoBancario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //valor autonumerico
	@Column(name="ID_DATO_BANCARIO")
	private Integer id;
	private String nombreBanco;
	private String numeroCuenta;
	
	
	public DatoBancario() {
		super();
	}
	public DatoBancario(String nombreBanco, String numeroCuenta) {
		super();
		this.nombreBanco = nombreBanco;
		this.numeroCuenta = numeroCuenta;
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
