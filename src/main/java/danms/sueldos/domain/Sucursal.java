package danms.sueldos.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Sucursal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //valor autonumerico
	@Column(name="ID_SUCURSAL")
	private Integer id;
	private String direccion;
	private String ciudad;
	private String cuitEmpresa;
	
	@ManyToMany
	@JoinTable(
			name = "TABLE_UNION_SUCURSAL_EMPLEADO",
			joinColumns = @JoinColumn(name = "ID_SUCURSAL"),
			inverseJoinColumns = @JoinColumn(name="ID_EMPLEADO"))
	private List<Empleado> empleados;
	
	
	public Sucursal() {
		super();
		this.empleados = new ArrayList<>();
	}
	public Sucursal(String direccion, String ciudad, String cuitEmpresa) {
		super();
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.cuitEmpresa = cuitEmpresa;
		this.empleados = new ArrayList<>();
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
	
	public List<Empleado> getEmpleados() {
		return empleados;
	}
	public void addEmpleado(Empleado empleado) {
		this.empleados.add(empleado);
	}
	
	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}
	
	public void clearEmpleados() {
		this.empleados.clear();
	}
	
	
	@Override
	public String toString() {
		return "Sucursal [id=" + id + ", direccion=" + direccion + ", ciudad=" + ciudad + ", cuitEmpresa=" + cuitEmpresa
				+ ", empleados=" + empleados + "]";
	}
	

}
