package danms.sueldos.domain;

public class CodigoDetalle {

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
	
	
	
	
}
