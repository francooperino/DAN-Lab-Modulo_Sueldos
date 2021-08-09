package danms.sueldos.services.interfaces;

import java.util.List;
import java.util.Optional;

import danms.sueldos.domain.Sucursal;

public interface SucursalService {

	public Optional<Sucursal> guardarSucursal(Sucursal sucursal);
	
	public Optional<Sucursal> actualizarSucursal(Sucursal sucursal);
	
	public Optional<Sucursal> getSucursal(Integer idSucursal);
	
	public List<Sucursal> getAllSucursal();
	
	public Optional<Sucursal> borrarSucursal(Sucursal sucursal);
	
	public Optional<Sucursal> addEmpleado(Integer idSucursal, Integer idEmpleado);
	
	public Optional<Sucursal> removeEmpleado(Integer idSucursal, Integer idEmpleado);
}
