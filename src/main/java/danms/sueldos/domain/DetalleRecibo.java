package danms.sueldos.domain;

public class DetalleRecibo {
	private Integer id;
	private Double porcentaje;
	private Double haber;
	private Double deduccion;
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
	
	
	
	

}
