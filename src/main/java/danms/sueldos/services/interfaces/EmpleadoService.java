package danms.sueldos.services.interfaces;

import java.util.List;
import java.util.Optional;

import danms.sueldos.domain.DatoBancario;
import danms.sueldos.domain.Empleado;

public interface EmpleadoService {
	
	public Optional<Empleado> getEmpleado(Integer idEmpleado);
	
	public List<Empleado> getAllEmpleado();
	
	//
	public Optional<DatoBancario> guardarDatoBancario(DatoBancario datoBancario);
	
	public Optional<DatoBancario> actualizarDatoBancario(DatoBancario datoBancario);
	
	public Optional<DatoBancario> getDatoBancario(Integer idDatoBancario);
	
	public List<DatoBancario> getAllDatoBancario();
	
	public Optional<DatoBancario> borrarDatoBancario(DatoBancario datoBancario);
	
	
	//
}
