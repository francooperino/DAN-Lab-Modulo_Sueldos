package danms.sueldos.services.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import danms.sueldos.domain.DetalleRecibo;

@Repository
public interface DetalleReciboRepository extends JpaRepository<DetalleRecibo, Integer>{

}
