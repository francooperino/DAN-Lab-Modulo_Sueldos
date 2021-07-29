package danms.sueldos.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import danms.sueldos.domain.Sucursal;
import danms.sueldos.services.dao.SucursalRepository;
import danms.sueldos.services.interfaces.SucursalService;

@Aspect
@Service
public class SucursalServiceImp implements SucursalService {

	private static final Logger logger = LoggerFactory.getLogger(SucursalServiceImp.class);

	@Autowired
	SucursalRepository sucursalRepo;

	@Override
	public Optional<Sucursal> guardarSucursal(Sucursal sucursal) {
		logger.info("Solicitud de guardado de sucursal");
		try {
			sucursalRepo.saveAndFlush(sucursal);
			logger.debug("Se guardo correctamente la sucursal");
			return Optional.of(sucursal);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Fallo al guardar la sucursal");
			return Optional.empty();
		}
	}

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
					return this.guardarSucursal(sucursal);
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
				throw new Exception("No se encontro la sucursal con la id: " + idSucursal+ " en la DB");
			}
			logger.debug("Se encontro la sucursal con la id: " + idSucursal);
			return optSucursal;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("La id recibida es null");
			return Optional.empty();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No se encontro la sucursal con la id: " + idSucursal+ " en la DB");
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
}
