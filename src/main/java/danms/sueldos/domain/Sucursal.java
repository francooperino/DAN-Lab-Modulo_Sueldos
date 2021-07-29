package danms.sueldos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sucursal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //valor autonumerico
	@Column(name="ID_SUCURSAL")
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Sucursal [id=" + id + ", direccion=" + direccion + ", ciudad=" + ciudad + ", cuitEmpresa=" + cuitEmpresa
				+ "]";
	}
	

	
}
