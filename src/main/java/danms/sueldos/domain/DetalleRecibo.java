package danms.sueldos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;

@Entity
public class DetalleRecibo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // valor autonumerico
	@Column(name = "ID_DETALLE_RECIBO")
	private Integer id;
	private Double porcentaje;
	private Double haber;
	private Double deduccion;

	@NonNull
	@ManyToOne
	@JoinColumn(name = "ID_CODIGO_DETALLE")
	private CodigoDetalle codigoDetalle;

	public DetalleRecibo() {
		super();
	}

	public DetalleRecibo(Double porcentaje, Double haber, Double deduccion, CodigoDetalle codigoDetalle) {
		super();
		this.porcentaje = porcentaje;
		this.haber = haber;
		this.deduccion = deduccion;
		this.codigoDetalle = codigoDetalle;
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

	public Double getDeduccion() {
		return deduccion;
	}

	public void setDeduccion(Double deduccion) {
		this.deduccion = deduccion;
	}

	public CodigoDetalle getCodigoDetalle() {
		return codigoDetalle;
	}

	public void setCodigoDetalle(CodigoDetalle codigoDetalle) {
		this.codigoDetalle = codigoDetalle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DetalleRecibo [id=" + id + ", porcentaje=" + porcentaje + ", haber=" + haber + ", deduccion="
				+ deduccion + ", codigoDetalle=" + codigoDetalle + "]";
	}

}
