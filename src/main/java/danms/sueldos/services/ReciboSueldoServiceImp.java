package danms.sueldos.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import danms.sueldos.domain.CodigoDetalle;
import danms.sueldos.domain.DatoBancario;
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
		logger.info("Solicitud de actualizacion de dato bancario");
		try {
			Optional<CodigoDetalle> optCodigoDetalleBuscado = codigoDetalleRepository.findById(codigoDetalle.getId());
			if (optCodigoDetalleBuscado.isPresent()) {
				// Existe en la DB
				logger.debug("Existe el codigo detalle con codigo detalle: " + codigoDetalle.getCodigoDetalle()
						+ "e id: " + codigoDetalle.getId());
				codigoDetalle.setId(optCodigoDetalleBuscado.get().getId());
				return this.guardarCodigoDetalle(codigoDetalle);
			} else {
				// No existe en la DB
				logger.error("El codigo detalle con codigo detalle: " + codigoDetalle.getCodigoDetalle()
						+ " no existe en la DB");
				throw new IllegalArgumentException("El codigo detalle con codigo detalle: "
						+ codigoDetalle.getCodigoDetalle() + " no existe en la DB");
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("El codigo detalle no pudo ser actualizado");
			return Optional.empty();
		}
	}

	@Override
	public Optional<CodigoDetalle> getCodigoDetalle(Integer codigoDetalle) {
		logger.info("Solicitud de obtencion del codigoDetalle " + codigoDetalle);
		try {
			Optional<CodigoDetalle> optCodigoDetalle = codigoDetalleRepository.findByCodigoDetalle(codigoDetalle);
			// Chequemos si lo encontro
			if (optCodigoDetalle.isEmpty()) {
				logger.debug("No se encontro el codigo detalle con codigo: " + codigoDetalle);
				return optCodigoDetalle;
			}
			logger.debug("Se encontro el codigo detalle con codigo: " + codigoDetalle);
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
				logger.debug("No se encontro el detalleRecibo con id: " + idDetalleRecibo);
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
				throw new IllegalArgumentException("No se pudieron guardar los detalles recibo "
						+ reciboSueldo.getListaDetalleRecibo().toString() + " en la base de datos");

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Fallo al guardar recibo sueldo:" + reciboSueldo.toString());
			return Optional.empty();
		}

	}

	@Override
	public Optional<ReciboSueldo> actualizarReciboSueldo(ReciboSueldo reciboSueldo) {

		logger.info("Solicitud de actualizacion recibo de sueldo");
		logger.info("Verificamos si existe el recibo de sueldo: " + reciboSueldo.toString());

		// Si existe el recibo sueldo actualizamos directamente, sino
		// lanzamos excepcion
		Optional<ReciboSueldo> optRecibo = reciboSueldoRepository.findById(reciboSueldo.getId());
		try {
			if (reciboSueldo.getId() != null) {
				if (optRecibo.isPresent()) {
					// Que exista empleado
					if (empleadoService.getEmpleado(reciboSueldo.getEmpleado().getId()).isPresent()) {
						// Que exista sucursal
						if (sucursalService.getSucursal(reciboSueldo.getSucursal().getId()).isPresent()) {

							// Setear todo y actualizar
							reciboSueldo.setListaDetalleRecibo(optRecibo.get().getListaDetalleRecibo());
							reciboSueldoRepository.saveAndFlush(reciboSueldo);
							return this.guardarReciboSueldo(reciboSueldo);

						} else {
							// No existe la nueva sucursal
							logger.debug("la sucursal a usar en el recibo no existe en la bdd");
							throw new IllegalArgumentException("No existe la sucursal : "
									+ reciboSueldo.getSucursal().toString() + " en la base de datos");
						}

					} else {
						// No existe el nuevo empleado
						logger.debug("El empleado a usar en el recibo no existe en la bdd");
						throw new IllegalArgumentException("No existe el empleado: "
								+ reciboSueldo.getEmpleado().toString() + " en la base de datos");
					}

				} else {
					logger.debug("El Recibo a actualizar no existe en la base de datos");
					throw new IllegalArgumentException(
							"No existe el recibo: " + reciboSueldo.toString() + " en la base de datos");
				}
			} else {
				// Recibo ID es null
				logger.debug("El Recibo tiene id NULL");
				throw new Exception("El id Recibo es NULL: " + reciboSueldo.toString());

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Fallo al actualizar Recibo:" + reciboSueldo.toString());
			return Optional.empty();
		}

	}

	@Override
	public Optional<ReciboSueldo> getReciboSueldo(Integer idReciboSueldo) {
		logger.info("Solicitud de obtencion de recibo de sueldo " + idReciboSueldo);
		try {
			Optional<ReciboSueldo> optReciboSueldo = reciboSueldoRepository.findById(idReciboSueldo);
			// Chequemos si lo encontro
			if (optReciboSueldo.isEmpty()) {
				logger.debug("No se encontro el recibo de sueldo con id: " + idReciboSueldo);
				return optReciboSueldo;
			}
			logger.debug("Se encontro el codigo detalle con id: " + optReciboSueldo);
			return optReciboSueldo;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("El id recibido de recibo de sueldo es null");
			return Optional.empty();
		}
	}

	@Override
	public List<ReciboSueldo> getAllReciboSueldo() {
		return reciboSueldoRepository.findAll();
	}

	@Override
	public Optional<ReciboSueldo> borrarReciboSueldo(ReciboSueldo reciboSueldo) {
		logger.info("Solicitud de borrado del Recibo: " + reciboSueldo.toString());

		try {
			if (this.getReciboSueldo(reciboSueldo.getId()).isEmpty()) {
				throw new IllegalArgumentException(
						"No existe el recibo con id: " + reciboSueldo.getId() + " en la base de datos");
			}
			for (DetalleRecibo dt : reciboSueldo.getListaDetalleRecibo()) {
				if (dt.getId() != null) {
					this.borrarDetalleRecibo(dt);
				}
			}
			reciboSueldoRepository.deleteById(reciboSueldo.getId());
			logger.debug("Se borro correctamente el recibo: " + reciboSueldo.toString());
			return Optional.of(reciboSueldo);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("El id recibido es null");
			return Optional.empty();
		}
	}

	@Override
	public void generarRecibosSueldos() {
		logger.info("Generacion de recibos para la sucursales");
		// Debo generar los recibos por cada empleado de cada sucursal
		List<Sucursal> listaSucursales = sucursalService.getAllSucursal();
		for (Sucursal unaSucursal : listaSucursales) {
			logger.info("Generacion de recibos sueldos para la sucursal con id: " + unaSucursal.getId());
			List<Empleado> listaEmpleados = unaSucursal.getEmpleados();
			// Por cada empleado generamos el recibo
			for (Empleado unEmpleado : listaEmpleados) {
				logger.info("Generacion de recibos para empleado con id: " + unEmpleado.getId());
				Optional<ReciboSueldo> optReciboGenerado = generarReciboSueldo(unaSucursal, unEmpleado);
				this.guardarReciboSueldo(optReciboGenerado.get());
			}
		}
	}

	@Override
	public Optional<ReciboSueldo> generarReciboSueldo(Sucursal sucursal, Empleado empleado) {
		//--------------Datos basicos
		Date fechaActual = Date.valueOf(LocalDate.now()); // Fecha actual
		Date fechaDePago = Date.valueOf(LocalDate.now().plusDays(10)); // 10 dias de la fecha actual
		
		ReciboSueldo reciboSueldo = new ReciboSueldo();
		reciboSueldo.setSucursal(sucursal);
		reciboSueldo.setEmpleado(empleado);
		reciboSueldo.setNumeroRecibo(123); //TODO: Ver
		reciboSueldo.setFechaEmision(fechaActual);
		reciboSueldo.setLugarDePago("Santa Fe");
		reciboSueldo.setFechaDePago(fechaDePago);
		reciboSueldo.setPagado(false);
		//Dato bancario del empleado
		List<DatoBancario> listaDatoBancario = empleadoService.getDatoBancarioPorIdEmpleado(empleado.getId());
		reciboSueldo.setDatoBancario(listaDatoBancario.get(0));
		//--------------Datos complementarios
		//Variables auxiliares
		Double totalBruto = 0.0;
		Double totalDeducciones = 0.0;
		
		//Debemos crear un detalle recibo por cada codigo detalle
		List<CodigoDetalle> listaCodigosDetalles = this.getAllCodigoDetalle();
		DetalleRecibo detalleRecibo;
		for(CodigoDetalle unCod: listaCodigosDetalles) {
			detalleRecibo = new DetalleRecibo();
			detalleRecibo.setCodigoDetalle(unCod);
			//Chequeamos haber --> Es solo el sueldo (simplificacion de la solucion)
			if(unCod.getHaber() != null) {
				detalleRecibo.setHaber(unCod.getHaber());
				totalBruto = unCod.getHaber(); 
				detalleRecibo.setDeduccion(0.0);
				detalleRecibo.setPorcentaje(0.0);
			}
			else {
				//Si no hay haber, hay deducciones
				detalleRecibo.setDeduccion(totalBruto * (unCod.getPorcentaje()/100));
				detalleRecibo.setPorcentaje(unCod.getPorcentaje());
				totalDeducciones += totalBruto * (unCod.getPorcentaje()/100);
				detalleRecibo.setHaber(0.0);
			}
			//Agrego el detalle al recibo
			reciboSueldo.addDetalleRecibo(detalleRecibo);
		}
		//Valores faltantes
		reciboSueldo.setTotalBruto(totalBruto);
		reciboSueldo.setTotalNeto(totalBruto-totalDeducciones); //Total neto
		reciboSueldo.setDeducciones(totalDeducciones);
		return Optional.of(reciboSueldo);
	}
}
