package danms.sueldos.services;

import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import danms.sueldos.domain.CodigoDetalle;
import danms.sueldos.domain.DetalleRecibo;
import danms.sueldos.domain.Empleado;
import danms.sueldos.domain.ReciboSueldo;
import danms.sueldos.domain.Sucursal;
import danms.sueldos.services.dao.CodigoDetalleRepository;
import danms.sueldos.services.dao.DetalleReciboRepository;
import danms.sueldos.services.dao.ReciboSueldoRepository;
import danms.sueldos.services.interfaces.ReciboSueldoService;

@Aspect
@Service
public class ReciboSueldoServiceImp implements ReciboSueldoService {

	private static final Logger logger = LoggerFactory.getLogger(ReciboSueldoService.class);

	@Autowired
	EmpleadoServiceImp empleadoService;

	@Autowired
	SucursalServiceImp sucursalService;

	@Autowired
	CodigoDetalleRepository codigoDetalleRepository;

	@Autowired
	DetalleReciboRepository detalleReciboRepository;

	@Autowired
	ReciboSueldoRepository reciboSueldoRepository;

	// Metodos CodigoDetalle

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
		// TODO: rearmar este metodo para consultar si ademas existe en la bdd
		if (codigoDetalle.getId() != null) {
			logger.debug("El codigoDetalle existe");
			return this.guardarCodigoDetalle(codigoDetalle);
		}
		logger.debug("El codigo detalle no existe, debe estar creado para poder ser actualizado");
		return Optional.empty();
	}

	@Override
	public Optional<CodigoDetalle> getCodigoDetalle(Integer CodigoDetalle) {
		logger.info("Solicitud de obtencion del codigoDetalle " + CodigoDetalle);
		try {
			Optional<CodigoDetalle> optCodigoDetalle = codigoDetalleRepository.findByCodigoDetalle(CodigoDetalle);
			// Chequemos si lo encontro
			if (optCodigoDetalle.isEmpty()) {
				logger.debug("No se encontro el codigo detalle con codigo: " + CodigoDetalle);
				return optCodigoDetalle;
			}
			logger.debug("Se encontro el codigo detalle con codigo: " + CodigoDetalle);
			return optCodigoDetalle;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("El codigo recibido es null");
			return Optional.empty();
		}
	}

	@Override
	public List<CodigoDetalle> getAllCodigoDetalle() {
		return codigoDetalleRepository.findAll();
	}

	@Override
	public Optional<CodigoDetalle> borrarCodigoDetalle(CodigoDetalle codigoDetalle) {
		logger.info("Solicitud de borrado del codigoDetalle: " + codigoDetalle.toString());

		try {
			if (getCodigoDetalle(codigoDetalle.getCodigoDetalle()).isEmpty()) {
				throw new IllegalArgumentException(
						"No existe el codigo detalle con codigo: " + codigoDetalle.getId() + " en la base de datos");
			}
			codigoDetalleRepository.deleteById(codigoDetalle.getId());
			logger.debug("Se borro correctamente el codigo detalle: " + codigoDetalle.toString());
			return Optional.of(codigoDetalle);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("El id recibido es null");
			return Optional.empty();
		}
	}

	// Metodos DetalleRecibo

	@Override
	public Optional<DetalleRecibo> guardarDetalleRecibo(DetalleRecibo detalleRecibo) {
		logger.info("Solicitud de guardado de detalleRecibo");

		logger.info("Verificamos si existe el codigo detalle: " + detalleRecibo.getCodigoDetalle().toString());

		// Si existe el codigo detalle guardamos directamente el detalle, sino guardamos
		// primero el codigoDetalle
		Optional<CodigoDetalle> optCodigo = codigoDetalleRepository
				.findByCodigoDetalle(detalleRecibo.getCodigoDetalle().getCodigoDetalle());
		// TODO: Revisar esto, puede estar mal
		try {
			if (optCodigo.isPresent()) {
				detalleRecibo.setCodigoDetalle(optCodigo.get());
				detalleReciboRepository.saveAndFlush(detalleRecibo);
				logger.debug("Se guardo correctamente el detalle: " + detalleRecibo.toString());
				return Optional.of(detalleRecibo);
			} else {
				logger.debug("Fallo al guardar el codigoDetalle " + detalleRecibo.getCodigoDetalle().toString());
				throw new IllegalArgumentException("No existe el codigo: "
						+ detalleRecibo.getCodigoDetalle().getCodigoDetalle() + " en la base de datos");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Fallo al guardar DetalleRecibo:" + detalleRecibo.toString());
			return Optional.empty();
		}
	}

	@Override
	public List<DetalleRecibo> guardarListaDetalleRecibo(List<DetalleRecibo> listaDetalleRecibo) {
		
		return detalleReciboRepository.saveAllAndFlush(listaDetalleRecibo);
	}

	@Override
	public Optional<DetalleRecibo> actualizarDetalleRecibo(DetalleRecibo detalleRecibo) {

		logger.info("Solicitud de actualizacion detalleRecibo");
		logger.info("Verificamos si existe el codigo detalle: " + detalleRecibo.getCodigoDetalle().toString());

		// Si existe el codigo detalle actualizamos directamente el detalle, sino
		// lanzamos excepcion
		Optional<CodigoDetalle> optCodigo = codigoDetalleRepository
				.findByCodigoDetalle(detalleRecibo.getCodigoDetalle().getCodigoDetalle());
		try {
			if (optCodigo.isPresent()) {
				detalleRecibo.setCodigoDetalle(optCodigo.get());
				if (detalleRecibo.getId() != null) {
					logger.debug("El id de detalleRecibo es distinto de null");
					if (getDetalleRecibo(detalleRecibo.getId()).isPresent()) {
						logger.debug("El id de detalleRecibo existe en la BDD:" + detalleRecibo.getId());
						logger.debug("Se guardo correctamente el detalle: " + detalleRecibo.toString());
						return this.guardarDetalleRecibo(detalleRecibo);

					} else {
						// DetalleRecibo ID no existe en la BD
						logger.debug("El detalle recibo con id: " + detalleRecibo.getId() + " no existe en la BDD");
						throw new Exception(
								"No existe el detalle recibo : " + detalleRecibo.toString() + " en la base de datos");
					}

				} else {
					// DetalleRecibo ID es null
					logger.debug("El detalle recibo tiene id NULL");
					throw new Exception("El id detalle es NULL: " + detalleRecibo.toString());

				}
			} else {
				logger.debug("Fallo al guardar el codigoDetalle " + detalleRecibo.getCodigoDetalle().toString());
				throw new IllegalArgumentException("No existe el codigo: "
						+ detalleRecibo.getCodigoDetalle().getCodigoDetalle() + " en la base de datos");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Fallo al actualizar DetalleRecibo:" + detalleRecibo.toString());
			return Optional.empty();
		}

	}

	@Override
	public Optional<DetalleRecibo> getDetalleRecibo(Integer idDetalleRecibo) {
		logger.info("Solicitud de obtencion del detalleRecibo " + idDetalleRecibo);
		try {
			Optional<DetalleRecibo> optDetalleRecibo = detalleReciboRepository.findById(idDetalleRecibo);
			// Chequemos si lo encontro
			if (optDetalleRecibo.isEmpty()) {
				logger.debug("No se encontro el codigo detalle con id: " + idDetalleRecibo);
				return optDetalleRecibo;
			}
			logger.debug("Se encontro el codigo detalle con id: " + optDetalleRecibo);
			return optDetalleRecibo;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("El id recibido de DetallRecibo es null");
			return Optional.empty();
		}
	}

	@Override
	public List<DetalleRecibo> getAllDetalleRecibo() {
		return detalleReciboRepository.findAll();
	}

	@Override
	public Optional<DetalleRecibo> borrarDetalleRecibo(DetalleRecibo detalleRecibo) {
		logger.info("Solicitud de borrado del DetalleRecibo: " + detalleRecibo.toString());

		try {
			if (getDetalleRecibo(detalleRecibo.getId()).isEmpty()) {
				throw new IllegalArgumentException(
						"No existe el codigo detalle con id: " + detalleRecibo.getId() + " en la base de datos");
			}
			detalleReciboRepository.deleteById(detalleRecibo.getId());
			logger.debug("Se borro correctamente el detalleRexibo: " + detalleRecibo.toString());
			return Optional.of(detalleRecibo);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("El id recibido es null");
			return Optional.empty();
		}
	}

	@Override
	public Optional<ReciboSueldo> guardarReciboSueldo(ReciboSueldo reciboSueldo) {
		// Guardar detalles pedido ver si es empty
		// Empleado solo lectura
		// Sucursal ya tiene que estar creado

		logger.info("Solicitud de guardado de reciboSueldo");

		try {

			List<DetalleRecibo> listDetRec = guardarListaDetalleRecibo(reciboSueldo.getListaDetalleRecibo());

			if (listDetRec.size() == reciboSueldo.getListaDetalleRecibo().size()) {
				Optional<Empleado> optEmpleado = empleadoService.getEmpleado(reciboSueldo.getEmpleado().getId());
				if (optEmpleado.isPresent()) {
					Optional<Sucursal> optSucursal = sucursalService.getSucursal(reciboSueldo.getSucursal().getId());
					if (optSucursal.isPresent()) {
						reciboSueldo.setEmpleado(optEmpleado.get());
						reciboSueldo.setSucursal(optSucursal.get());
						reciboSueldo.setListaDetalleRecibo(listDetRec);
						
						reciboSueldoRepository.saveAndFlush(reciboSueldo);
						logger.debug("Se guardo correctamente el recibo de sueldo: " + reciboSueldo.toString());
						return Optional.of(reciboSueldo);
					} else {
						logger.debug("Fallo al guardar el recibo de sueldo " + reciboSueldo.toString());
						throw new IllegalArgumentException("No existe la sucursal: "
								+ reciboSueldo.getSucursal().toString() + " en la base de datos");
					}
				} else {
					logger.debug("Fallo al guardar el recibo de sueldo " + reciboSueldo.toString());
					throw new IllegalArgumentException(
							"No existe el empleado: " + reciboSueldo.getEmpleado().toString() + " en la base de datos");

				}
			} else {
				logger.debug("Fallo al guardar el recibo de sueldo " + reciboSueldo.toString());
				throw new IllegalArgumentException(
						"No se pudieron guardar los detalles recibo " + reciboSueldo.getListaDetalleRecibo().toString() + " en la base de datos");


			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Fallo al guardar recibo sueldo:" + reciboSueldo.toString());
			return Optional.empty();
		}

	}

	@Override
	public Optional<ReciboSueldo> actualizarReciboSueldo(ReciboSueldo reciboSueldo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ReciboSueldo> getReciboSueldo(Integer idReciboSueldo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReciboSueldo> getAllReciboSueldo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ReciboSueldo> borrarReciboSueldo(ReciboSueldo reciboSueldo) {
		// TODO Auto-generated method stub
		return null;
	}

	// Metodos ReciboSueldo

}
