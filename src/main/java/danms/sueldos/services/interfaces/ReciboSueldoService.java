package danms.sueldos.services.interfaces;

import java.util.List;
import java.util.Optional;

import danms.sueldos.domain.CodigoDetalle;
import danms.sueldos.domain.DetalleRecibo;
import danms.sueldos.domain.Empleado;
import danms.sueldos.domain.ReciboSueldo;
import danms.sueldos.domain.Sucursal;

public interface ReciboSueldoService {

	// ABM CodigoDetalle

	public Optional<CodigoDetalle> guardarCodigoDetalle(CodigoDetalle codigoDetalle);

	public Optional<CodigoDetalle> actualizarCodigoDetalle(CodigoDetalle codigoDetalle);

	public Optional<CodigoDetalle> getCodigoDetalle(Integer idCodigoDetalle);

	public List<CodigoDetalle> getAllCodigoDetalle();

	public Optional<CodigoDetalle> borrarCodigoDetalle(CodigoDetalle codigoDetalle);

	// ABM DetalleRecibo

	public Optional<DetalleRecibo> guardarDetalleRecibo(DetalleRecibo detalleRecibo);
	
	public List<DetalleRecibo> guardarListaDetalleRecibo(List<DetalleRecibo> listaDetalleRecibo);

	public Optional<DetalleRecibo> actualizarDetalleRecibo(DetalleRecibo detalleRecibo);

	public Optional<DetalleRecibo> getDetalleRecibo(Integer idDetalleRecibo);

	public List<DetalleRecibo> getAllDetalleRecibo();

	public Optional<DetalleRecibo> borrarDetalleRecibo(DetalleRecibo detalleRecibo);

	// ABM ReciboSueldo
	
	public void generarRecibosSueldos();
	
	public Optional<ReciboSueldo> generarReciboSueldo(Sucursal sucursal, Empleado empleado);
	
	public Optional<ReciboSueldo> guardarReciboSueldo(ReciboSueldo reciboSueldo);

	public Optional<ReciboSueldo> actualizarReciboSueldo(ReciboSueldo reciboSueldo);

	public Optional<ReciboSueldo> getReciboSueldo(Integer idReciboSueldo);

	public List<ReciboSueldo> getAllReciboSueldo();

	public Optional<ReciboSueldo> borrarReciboSueldo(ReciboSueldo reciboSueldo);

}
