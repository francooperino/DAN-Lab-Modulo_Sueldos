package danms.sueldos.services.interfaces;

import java.util.List;
import java.util.Optional;

import danms.sueldos.domain.CodigoDetalle;

public interface ReciboSueldoService {
	
	//ABM CodigoDetalle

	public Optional<CodigoDetalle> guardarCodigoDetalle(CodigoDetalle codigoDetalle);
	
	public Optional<CodigoDetalle> actualizarCodigoDetalle(CodigoDetalle codigoDetalle);
	
	public Optional<CodigoDetalle> getCodigoDetalle(Integer idCodigoDetalle);
	
	public List<CodigoDetalle> getAllCodigoDetalle();
	
	public Optional<CodigoDetalle> borrarCodigoDetalle(CodigoDetalle codigoDetalle);
	
	//ABM DetalleRecibo
	//ABM ReciboSueldo
	
	
	
	
}
