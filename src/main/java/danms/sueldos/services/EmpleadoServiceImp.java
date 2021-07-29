package danms.sueldos.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import danms.sueldos.domain.Empleado;
import danms.sueldos.domain.Sucursal;
import danms.sueldos.services.dao.EmpleadoRepository;
import danms.sueldos.services.dao.SucursalRepository;
import danms.sueldos.services.interfaces.EmpleadoService;

@Service
public class EmpleadoServiceImp implements EmpleadoService {

	private static final Logger logger = LoggerFactory.getLogger(EmpleadoServiceImp.class);

	@Autowired
	EmpleadoRepository empleadoRepo;

	
	@Override
	public Optional<Empleado> getEmpleado(Integer idEmpleado) {
		logger.info("Solicitud de obtencion del idEmpleado: " + idEmpleado);
		try {
			Optional<Empleado> optEmpleado = empleadoRepo.findById(idEmpleado);
			// Chequemos si la encontro
			if (optEmpleado.isEmpty()) {
				throw new Exception("No se encontro el empleado con la id: " + idEmpleado+ " en la DB");
			}
			logger.debug("Se encontro el empleado con la id: " + idEmpleado);
			return optEmpleado;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("La id recibida es null");
			return Optional.empty();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No se encontro el empleado con la id: " + idEmpleado+ " en la DB");
			return Optional.empty();
		}
	}

	@Override
	public List<Empleado> getAllEmpleado() {
		return empleadoRepo.findAll();
	}

	

}
