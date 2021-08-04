package danms.sueldos.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;

@Entity
public class ReciboSueldo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //valor autonumerico
	@Column(name="ID_RECIBO_SUELDO")
	private Integer id;
	private Integer numeroRecibo;
	private Date fechaEmision;
	private String lugarDePago;
	private Date fechaDePago;
	private Boolean pagado;
	private Double totalBruto;
	private Double totalNeto;
	private Double deducciones;
	
	@NonNull
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_RECIBO_SUELDO")
	private List<DetalleRecibo> listaDetalleRecibo;
	
	@NonNull
	@ManyToOne
	@JoinColumn(name = "ID_SUCURSAL")
	private Sucursal sucursal;
	
	@NonNull
	@ManyToOne
	@JoinColumn(name = "ID_EMPLEADO")
	private Empleado empleado;
	
	public ReciboSueldo(Integer numeroRecibo, Date fechaEmision, String lugarDePago, Date fechaDePago, Boolean pagado,
			Double totalBruto, Double totalNeto, Double deducciones,Sucursal sucursal, Empleado empleado) {
		super();
		this.numeroRecibo = numeroRecibo;
		this.fechaEmision = fechaEmision;
		this.lugarDePago = lugarDePago;
		this.fechaDePago = fechaDePago;
		this.pagado = pagado;
		this.totalBruto = totalBruto;
		this.totalNeto = totalNeto;
		this.deducciones = deducciones;
		this.empleado = empleado;
		this.sucursal = sucursal;
		this.listaDetalleRecibo = new ArrayList<>();
	}

	public ReciboSueldo() {
		super();
		this.listaDetalleRecibo = new ArrayList<>();
	}
	
	public Integer getNumeroRecibo() {
		return numeroRecibo;
	}
	public void setNumeroRecibo(Integer numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getLugarDePago() {
		return lugarDePago;
	}
	public void setLugarDePago(String lugarDePago) {
		this.lugarDePago = lugarDePago;
	}
	public Date getFechaDePago() {
		return fechaDePago;
	}
	public void setFechaDePago(Date fechaDePago) {
		this.fechaDePago = fechaDePago;
	}
	public Boolean getPagado() {
		return pagado;
	}
	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}
	public Double getTotalBruto() {
		return totalBruto;
	}
	public void setTotalBruto(Double totalBruto) {
		this.totalBruto = totalBruto;
	}
	public Double getTotalNeto() {
		return totalNeto;
	}
	public void setTotalNeto(Double totalNeto) {
		this.totalNeto = totalNeto;
	}
	public Double getDeducciones() {
		return deducciones;
	}
	public void setDeducciones(Double deducciones) {
		this.deducciones = deducciones;
	}

	public List<DetalleRecibo> getListaDetalleRecibo() {
		return listaDetalleRecibo;
	}

	public void setListaDetalleRecibo(List<DetalleRecibo> listaDetalleRecibo) {
		this.listaDetalleRecibo = listaDetalleRecibo;
	}
	
	public void addDetalleRecibo(DetalleRecibo detalleRecibo) {
		this.listaDetalleRecibo.add(detalleRecibo);
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
	
}
