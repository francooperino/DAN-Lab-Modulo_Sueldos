package danms.sueldos.services.interfaces;

import java.util.List;
import java.util.Optional;

import danms.sueldos.domain.CodigoDetalle;
import danms.sueldos.domain.DetalleRecibo;

public interface ReciboSueldoService {

	// ABM CodigoDetalle

	public Optional<CodigoDetalle> guardarCodigoDetalle(CodigoDetalle codigoDetalle);

	public Optional<CodigoDetalle> actualizarCodigoDetalle(CodigoDetalle codigoDetalle);

	public Optional<CodigoDetalle> getCodigoDetalle(Integer idCodigoDetalle);

	public List<CodigoDetalle> getAllCodigoDetalle();

	public Optional<CodigoDetalle> borrarCodigoDetalle(CodigoDetalle codigoDetalle);

	// ABM DetalleRecibo

	public Optional<DetalleRecibo> guardarDetalleRecibo(DetalleRecibo detalleRecibo);

	public Optional<DetalleRecibo> actualizarDetalleRecibo(DetalleRecibo detalleRecibo);

	public Optional<DetalleRecibo> getDetalleRecibo(Integer idDetalleRecibo);

	public List<DetalleRecibo> getAllDetalleRecibo();

	public Optional<DetalleRecibo> borrarDetalleRecibo(DetalleRecibo detalleRecibo);

	// ABM ReciboSueldo

}
