package danms.sueldos.services.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import danms.sueldos.domain.DatoBancario;
import danms.sueldos.domain.Empleado;

@Repository
public interface DatoBancarioRepository extends JpaRepository<DatoBancario, Integer>{
	public List<DatoBancario> findByEmpleado(Empleado empleado);
	
}
