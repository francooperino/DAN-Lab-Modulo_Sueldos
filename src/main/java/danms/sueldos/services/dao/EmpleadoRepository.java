package danms.sueldos.services.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import danms.sueldos.domain.Empleado;

@Repository
@Transactional(readOnly= true)
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{

}
