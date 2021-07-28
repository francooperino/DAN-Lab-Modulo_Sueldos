package danms.sueldos.services.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import danms.sueldos.domain.DatoBancario;

@Repository
public interface DatoBancarioRepository extends JpaRepository<DatoBancario, Integer>{

}
