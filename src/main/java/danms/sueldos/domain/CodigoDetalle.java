package danms.sueldos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CodigoDetalle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //valor autonumerico
	@Column(name="ID_CODIGO_DETALLE")
	private Integer id;
	private Integer codigoDetalle;
	private String descripcion;
	
	
	
	public CodigoDetalle() {
		super();
	}
	
	
	public CodigoDetalle(Integer codigoDetalle, String descripcion) {
		super();
		this.codigoDetalle = codigoDetalle;
		this.descripcion = descripcion;
	}
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCodigoDetalle() {
		return codigoDetalle;
	}
	public void setCodigoDetalle(Integer codigoDetalle) {
		this.codigoDetalle = codigoDetalle;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	@Override
	public String toString() {
		return "CodigoDetalle [id=" + id + ", codigoDetalle=" + codigoDetalle + ", descripcion=" + descripcion + "]";
	}
	
	
	
	
	
	
}
