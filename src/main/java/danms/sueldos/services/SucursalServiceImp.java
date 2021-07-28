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
		// Chequemos que ya exista
		if (sucursal.getId() != null) {
			logger.debug("La sucursal existe");
			return this.guardarSucursal(sucursal);
		}
		logger.debug("La sucursal no existe por lo tanto se debe crear primero");
		return Optional.empty();
	}

	@Override
	public Optional<Sucursal> getSucursal(Integer idSucursal) {
		logger.info("Solicitud de obtenciion de la sucursal: "+idSucursal);
		try {
			Optional<Sucursal> optSucursal = sucursalRepo.findById(idSucursal);
			//Chequemos si la encontro
			if(optSucursal.isEmpty()) {
				logger.debug("No se encontro la sucursal con la id: "+ idSucursal);
				return optSucursal;
			}
			logger.debug("Se encontro la sucursal con la id: "+ idSucursal);
			return optSucursal;
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("La id recibida es null");
			return Optional.empty();
		}
	}

	@Override
	public List<Sucursal> getAllSucursal() {
		return sucursalRepo.findAll();
	}

	@Override
	public Optional<Sucursal> borrarSucursal(Sucursal sucursal) {
		logger.info("Solicitud de borrado de la sucursal: "+sucursal.getId());
		try {
			sucursalRepo.deleteById(sucursal.getId());
			logger.debug("Se borro correctamente la sucursal con la id: "+ sucursal.getId());
			return Optional.of(sucursal);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("La id recibida es null");
			return Optional.empty();
		}
	}

}
