package danms.sueldos.services.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import danms.sueldos.domain.Empleado;

@Repository
@Transactional(readOnly= true)
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{

	@Query(value = "SELECT DISTINCT e.* "
			+ "FROM empleado e "
			+ "LEFT JOIN table_union_sucursal_empleado t ON t.id_empleado = e.id_empleado "
			+ "WHERE t.id_empleado IS NULL", 
			  nativeQuery = true)
	public List<Empleado> getAllEmpleadosNoAsociadoASucursal();
}
