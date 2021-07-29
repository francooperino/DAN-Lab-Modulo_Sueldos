package danms.sueldos.services.interfaces;

import java.util.List;
import java.util.Optional;

import danms.sueldos.domain.Empleado;

public interface EmpleadoService {
	
	public Optional<Empleado> getEmpleado(Integer idEmpleado);
	
	public List<Empleado> getAllEmpleado();
	
	
	
	//
}
