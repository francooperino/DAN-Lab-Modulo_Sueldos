package danms.sueldos.services.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import danms.sueldos.domain.ReciboSueldo;

@Repository
public interface ReciboSueldoRepository extends JpaRepository<ReciboSueldo, Integer>{

}
