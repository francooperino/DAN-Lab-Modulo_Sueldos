package danms.sueldos.domain;

public class DatoBancario {
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
