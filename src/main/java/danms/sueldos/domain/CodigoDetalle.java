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
	@Column(unique = true)
	private Integer codigoDetalle;
	private String descripcion;
	private Double porcentaje;
	private Double haber;
	
	
	
	public CodigoDetalle() {
		super();
	}
	
	
	public CodigoDetalle(Integer codigoDetalle, String descripcion, Double porcentaje, Double haber, Double deduccion) {
		super();
		this.codigoDetalle = codigoDetalle;
		this.descripcion = descripcion;
		this.porcentaje = porcentaje;
		this.haber = haber;
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

	public Double getPorcentaje() {
		return porcentaje;
	}


	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}


	public Double getHaber() {
		return haber;
	}


	public void setHaber(Double haber) {
		this.haber = haber;
	}


	@Override
	public String toString() {
		return "CodigoDetalle [id=" + id + ", codigoDetalle=" + codigoDetalle + ", descripcion=" + descripcion + "]";
	}
	
	
	
	
	
	
}
