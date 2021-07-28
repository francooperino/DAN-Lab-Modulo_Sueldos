package danms.sueldos.services.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import danms.sueldos.domain.CodigoDetalle;

@Repository
public interface CodigoDetalleRepository extends JpaRepository<CodigoDetalle, Integer>{

}
