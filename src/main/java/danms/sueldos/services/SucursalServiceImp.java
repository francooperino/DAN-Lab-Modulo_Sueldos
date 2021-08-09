package danms.sueldos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import danms.sueldos.domain.Empleado;
import danms.sueldos.domain.Sucursal;
import danms.sueldos.services.dao.SucursalRepository;
import danms.sueldos.services.interfaces.EmpleadoService;
import danms.sueldos.services.interfaces.SucursalService;

@Service
public class SucursalServiceImp implements SucursalService {

	private static final Logger logger = LoggerFactory.getLogger(SucursalServiceImp.class);

	@Autowired
	SucursalRepository sucursalRepo;

	@Autowired
	EmpleadoService empleadoService;

	
	/* 
	 * Guarda la sucursal. Si esta tiene una lista de empleados, estos se
	 * agregan a la misma. 
	 * */
	@Override
	public Optional<Sucursal> guardarSucursal(Sucursal sucursal) {
		logger.info("Solicitud de guardado de sucursal");
		try {
			// Chequeamos si la sucursal tiene empleados
			if (sucursal.getEmpleados() != null) {
				sucursal = this.agregarEmpleados(sucursal).get();
			}
			sucursalRepo.saveAndFlush(sucursal);
			logger.debug("Se guardo correctamente la sucursal");
			return Optional.of(sucursal);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Fallo al guardar la sucursal");
			return Optional.empty();
		}
	}
	
	
	/* 
	 * Actualiza todos los campos de la sucursal EXCEPTO los empleados.
	 * */
	@Override
	public Optional<Sucursal> actualizarSucursal(Sucursal sucursal) {
		logger.info("Solicitud de actualizacion de sucursal");
		try {
			// Chequemos que ya exista
			if (sucursal.getId() != null) {
				Optional<Sucursal> optSucursalBuscada = sucursalRepo.findById(sucursal.getId());
				if (optSucursalBuscada.isPresent()) {
					// Existe en la DB
					logger.debug("Existe la sucursal con id: " + sucursal.getId());
					sucursal.setEmpleados(optSucursalBuscada.get().getEmpleados());
					sucursalRepo.saveAndFlush(sucursal);
					return Optional.of(sucursal);
				} else {
					// No existe en la DB
					logger.error("La sucursal con la id: " + sucursal.getId() + " no existe en la DB");
					throw new IllegalArgumentException(
							"La sucursal con la id: " + sucursal.getId() + " no existe en la DB");
				}
			} else {
				// Id nula
				logger.error("La id recibida es nula");
				throw new IllegalArgumentException("La id recibida es nula");
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("La sucursal no pudo ser actualizada");
			return Optional.empty();
		}
	}

	@Override
	public Optional<Sucursal> getSucursal(Integer idSucursal) {
		logger.info("Solicitud de obtenciion de la sucursal: " + idSucursal);
		try {
			Optional<Sucursal> optSucursal = sucursalRepo.findById(idSucursal);
			// Chequemos si la encontro
			if (optSucursal.isEmpty()) {
				throw new Exception("No se encontro la sucursal con la id: " + idSucursal + " en la DB");
			}
			logger.debug("Se encontro la sucursal con la id: " + idSucursal);
			return optSucursal;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("La id recibida es null");
			return Optional.empty();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No se encontro la sucursal con la id: " + idSucursal + " en la DB");
			return Optional.empty();
		}
	}

	@Override
	public List<Sucursal> getAllSucursal() {
		return sucursalRepo.findAll();
	}

	@Override
	public Optional<Sucursal> borrarSucursal(Sucursal sucursal) {
		logger.info("Solicitud de borrado de la sucursal: " + sucursal.toString());

		try {
			if (getSucursal(sucursal.getId()).isEmpty()) {
				logger.error("No existe la sucursal con id: " + sucursal.getId() + " en la base de datos");
				throw new IllegalArgumentException(
						"No existe la sucursal con id: " + sucursal.getId() + " en la base de datos");
			}

			sucursalRepo.deleteById(sucursal.getId());
			logger.debug("Se borro correctamente la sucursal: " + sucursal.toString());
			return Optional.of(sucursal);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No se pudo remover la sucursal: " + sucursal.toString());
			return Optional.empty();

		}

	}

	/* Permite agregar empleado a una sucursal. 
	 * */
	@Override
	public Optional<Sucursal> addEmpleado(Integer idSucursal, Integer idEmpleado) {
		try {
			Optional<Sucursal> optSucursalBuscada = getSucursal(idSucursal);
			if (optSucursalBuscada.isEmpty()) {
				throw new Exception("La sucursal con el id: " + idSucursal + " no existe.");
			}
			Optional<Empleado> optEmpleadoBuscado = empleadoService.getEmpleado(idEmpleado);
			if (optEmpleadoBuscado.isEmpty()) {
				throw new Exception("El empleado con el id: " + idEmpleado + " no existe.");
			}
			Sucursal sucursal = optSucursalBuscada.get();
			//Chequemos que el empleado no este ya relacionado
			if(sucursal.getEmpleados().contains(optEmpleadoBuscado.get())){
				throw new Exception("El empleado con id: "+idEmpleado+" ya se encuentra asociado a la sucursal");
			}
			sucursal.addEmpleado(optEmpleadoBuscado.get());
			sucursalRepo.saveAndFlush(sucursal);
			return Optional.of(sucursal);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("El emplado con id: " + idEmpleado + " no puedo ser agregado a la sucursal con id: "
					+ idSucursal + ".");
			return Optional.empty();
		}

	}
	
	@Override
	public Optional<Sucursal> removeEmpleado(Integer idSucursal, Integer idEmpleado) {
		try {
			Optional<Sucursal> optSucursalBuscada = getSucursal(idSucursal);
			if (optSucursalBuscada.isEmpty()) {
				throw new Exception("La sucursal con el id: " + idSucursal + " no existe.");
			}
			Optional<Empleado> optEmpleadoBuscado = empleadoService.getEmpleado(idEmpleado);
			if (optEmpleadoBuscado.isEmpty()) {
				throw new Exception("El empleado con el id: " + idEmpleado + " no existe.");
			}
			Sucursal sucursal = optSucursalBuscada.get();
			//Chequemos que el empleado este relacionado
			if(!sucursal.getEmpleados().contains(optEmpleadoBuscado.get())){
				throw new Exception("El empleado con id: "+idEmpleado+" no se encuentra asociado a la sucursal con id: "+idSucursal);
			}
			sucursal.getEmpleados().remove(optEmpleadoBuscado.get());
			sucursalRepo.saveAndFlush(sucursal);
			return Optional.of(sucursal);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("El emplado con id: " + idEmpleado + " no puedo ser agregado a la sucursal con id: "
					+ idSucursal + ".");
			return Optional.empty();
		}
	}
	
	
	/* 
	 * Se llama desde guardarSucursal. Este metodo no se usa para actualizar una 
	 * sucursal con nuevos empleados.
	 * */
	private Optional<Sucursal> agregarEmpleados(Sucursal sucursal) {
		try {
			List<Empleado> aux = new ArrayList<>();
			aux.addAll(sucursal.getEmpleados()); // Copiamos los empleados
			sucursal.clearEmpleados(); // Borramos los empleados porque solo contiene el id de los mismos
			for (Empleado unEmp : aux) {
				Optional<Empleado> optEmpBuscado = empleadoService.getEmpleado(unEmp.getId());
				if (optEmpBuscado.isEmpty()) {
					// Empleado NO encontrado
					throw new Exception("Uno o mas de los empleados asociados a la sucursal no se enocontro en la DB");
				}
				// Empleado encontrado - lo asociamos a la sucursal
				logger.debug("Empleado con id: " + optEmpBuscado.get().getId() + " asociado a la sucursal: "
						+ sucursal.getCuitEmpresa());
				sucursal.addEmpleado(optEmpBuscado.get());

			}
			return Optional.of(sucursal);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Uno o mas de los empleados asociados a la sucursal no se enocontro en la DB");
			return Optional.empty();
		}

	}


	
}
