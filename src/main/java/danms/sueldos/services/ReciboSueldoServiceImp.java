package danms.sueldos.services;

import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import danms.sueldos.domain.CodigoDetalle;
import danms.sueldos.services.dao.CodigoDetalleRepository;
import danms.sueldos.services.interfaces.ReciboSueldoService;

@Aspect
@Service
public class ReciboSueldoServiceImp implements ReciboSueldoService{
	
	private static final Logger logger = LoggerFactory.getLogger(ReciboSueldoService.class);

	@Autowired
	CodigoDetalleRepository codigoDetalleRepository;
	
	//Metodos CodigoDetalle

	@Override
	public Optional<CodigoDetalle> guardarCodigoDetalle(CodigoDetalle codigoDetalle) {
		logger.info("Solicitud de guardado de codigoDetalle");
		try {
			codigoDetalleRepository.saveAndFlush(codigoDetalle);
			logger.debug("Se guardo correctamente el codigoDetalle");
			return Optional.of(codigoDetalle);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Fallo al guardar codigoDetalle");
			return Optional.empty();
		}
	}

	@Override
	public Optional<CodigoDetalle> actualizarCodigoDetalle(CodigoDetalle codigoDetalle) {
		logger.info("Solicitud de actualizacion codigoDetalle");
		// Chequemos que ya exista
		if (codigoDetalle.getId() != null) {
			logger.debug("El codigoDetalle existe");
			return this.guardarCodigoDetalle(codigoDetalle);
		}
		logger.debug("El codigo detalle no existe, debe estar creado para poder ser actualizado");
		return Optional.empty();
	}

	@Override
	public Optional<CodigoDetalle> getCodigoDetalle(Integer idCodigoDetalle) {
		logger.info("Solicitud de obtencion del codigoDetalle "+idCodigoDetalle);
		try {
			Optional<CodigoDetalle> optCodigoDetalle = codigoDetalleRepository.findById(idCodigoDetalle);
			//Chequemos si lo encontro
			if(optCodigoDetalle.isEmpty()) {
				logger.debug("No se encontro el codigo detalle con el id: "+ idCodigoDetalle);
				return optCodigoDetalle;
			}
			logger.debug("Se encontro el codigo detalle con el id: "+ idCodigoDetalle);
			return optCodigoDetalle;
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("El id recibido es null");
			return Optional.empty();
		}
	}

	@Override
	public List<CodigoDetalle> getAllCodigoDetalle() {
		return codigoDetalleRepository.findAll();
	}

	@Override
	public Optional<CodigoDetalle> borrarCodigoDetalle(CodigoDetalle codigoDetalle) {
		logger.info("Solicitud de borrado del codigoDetalle: "+codigoDetalle.toString());
		
		try {
			if(getCodigoDetalle(codigoDetalle.getId()).isEmpty()) {
				throw new IllegalArgumentException("No existe el codigo detalle con id: "+codigoDetalle.getId()+" en la base de datos");
				}
			codigoDetalleRepository.deleteById(codigoDetalle.getId());
			logger.debug("Se borro correctamente el codigo detalle: "+ codigoDetalle.toString());
			return Optional.of(codigoDetalle);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("El id recibido es null");
			return Optional.empty();
		}
	}
	
	
	
	//Metodos DetalleRecibo
	
	
	//Metodos ReciboSueldo

}
