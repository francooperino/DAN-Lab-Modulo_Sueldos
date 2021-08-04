package danms.sueldos.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import danms.sueldos.domain.DatoBancario;
import danms.sueldos.domain.Empleado;
import danms.sueldos.services.dao.DatoBancarioRepository;
import danms.sueldos.services.dao.EmpleadoRepository;
import danms.sueldos.services.interfaces.EmpleadoService;

@Service
public class EmpleadoServiceImp implements EmpleadoService {

	private static final Logger logger = LoggerFactory.getLogger(EmpleadoServiceImp.class);

	@Autowired
	EmpleadoRepository empleadoRepo;
	
	@Autowired
	DatoBancarioRepository datoBancarioRepo;

	
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

	@Override
	public Optional<DatoBancario> guardarDatoBancario(DatoBancario datoBancario) {
		logger.info("Solicitud de guardado de datoBancario");
		//Chequeamos que el empleado asociado al dato bancario exista en la DB.
		Optional<Empleado> optEmpleadoBuscado = this.getEmpleado(datoBancario.getEmpleado().getId());
		if(optEmpleadoBuscado.isEmpty()) {
			logger.error("No existe el empleado asociado al dato bancario");
			return Optional.empty();
		}
		try {
			datoBancarioRepo.saveAndFlush(datoBancario);
			logger.debug("Se guardo correctamente el dato bancario");
			return Optional.of(datoBancario);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Fallo al guardar el dato bancario");
			return Optional.empty();
		}
	}

	@Override
	public Optional<DatoBancario> actualizarDatoBancario(DatoBancario datoBancario) {
		logger.info("Solicitud de actualizacion de dato bancario");
		try {
			// Chequemos que ya exista
			if (datoBancario.getId() != null) {
				Optional<DatoBancario> optDatoBancarioBuscado = datoBancarioRepo.findById(datoBancario.getId());
				if (optDatoBancarioBuscado.isPresent()) {
					// Existe en la DB
					logger.debug("Existe el dato bancario con id: " + datoBancario.getId());
					return this.guardarDatoBancario(datoBancario);
				} else {
					// No existe en la DB
					logger.error("El dato bancario con la id: " + datoBancario.getId() + " no existe en la DB");
					throw new IllegalArgumentException(
							"El dato bancariol con la id: " + datoBancario.getId() + " no existe en la DB");
				}
			} else {
				// Id nula
				logger.error("La id recibida es nula");
				throw new IllegalArgumentException("La id recibida es nula");
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("El dato bancario no pudo ser actualizado");
			return Optional.empty();
		}
	}

	@Override
	public Optional<DatoBancario> getDatoBancario(Integer idDatoBancario) {
		logger.info("Solicitud de obtenciion del dato bancario: " + idDatoBancario);
		try {
			Optional<DatoBancario> optDatoBancario = datoBancarioRepo.findById(idDatoBancario);
			// Chequemos si la encontro
			if (optDatoBancario.isEmpty()) {
				throw new Exception("No se encontro el dato bancario con la id: " + idDatoBancario+ " en la DB");
			}
			logger.debug("Se encontro el dato bancario con la id: " + idDatoBancario);
			return optDatoBancario;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("La id recibida es null");
			return Optional.empty();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No se encontro el dato bancario con la id: " + idDatoBancario+ " en la DB");
			return Optional.empty();
		}
	}

	@Override
	public List<DatoBancario> getAllDatoBancario() {
		return datoBancarioRepo.findAll();
	}

	@Override
	public Optional<DatoBancario> borrarDatoBancario(DatoBancario datoBancario) {
		logger.info("Solicitud de borrado del dato bancario: " + datoBancario.toString());

		try {
			if (getDatoBancario(datoBancario.getId()).isEmpty()) {
				logger.error("No existe el dato bancario con id: " + datoBancario.getId() + " en la base de datos");
				throw new IllegalArgumentException(
						"No existe el dato bancario con id: " + datoBancario.getId() + " en la base de datos");
			}
			
			datoBancarioRepo.deleteById(datoBancario.getId());
			logger.debug("Se borro correctamente el dato bancario: " + datoBancario.toString());
			return Optional.of(datoBancario);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No se pudo remover el dato bancario: " + datoBancario.toString());
			return Optional.empty();

		}

	}

	

}
