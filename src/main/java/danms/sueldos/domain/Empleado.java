package danms.sueldos.domain;

public class Empleado {
	private Integer id;
	private String email;
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
