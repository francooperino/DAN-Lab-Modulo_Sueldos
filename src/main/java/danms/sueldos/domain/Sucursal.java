package danms.sueldos.domain;

public class Sucursal {
	private Integer id;
	private String direccion;
	private String ciudad;
	private String cuitEmpresa;
	
	
	public Sucursal() {
		super();
	}
	public Sucursal(String direccion, String ciudad, String cuitEmpresa) {
		super();
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.cuitEmpresa = cuitEmpresa;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCuitEmpresa() {
		return cuitEmpresa;
	}
	public void setCuitEmpresa(String cuitEmpresa) {
		this.cuitEmpresa = cuitEmpresa;
	}
	
	
	
	
}
