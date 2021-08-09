package danms.sueldos.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import danms.sueldos.domain.CodigoDetalle;
import danms.sueldos.domain.DatoBancario;
import danms.sueldos.domain.DetalleRecibo;
import danms.sueldos.domain.Empleado;
import danms.sueldos.domain.ReciboSueldo;
import danms.sueldos.domain.Sucursal;
import danms.sueldos.services.dao.CodigoDetalleRepository;
import danms.sueldos.services.dao.DatoBancarioRepository;
import danms.sueldos.services.dao.DetalleReciboRepository;
import danms.sueldos.services.dao.EmpleadoRepository;
import danms.sueldos.services.dao.ReciboSueldoRepository;
import danms.sueldos.services.dao.SucursalRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
class ReciboSueldoRestTest {

	private String ENDPOINT_CODIGO_DETALLE = "/api/sueldos/recibosueldo/codigodetalle";

	private String ENDPOINT_RECIBO_SUELDO = "/api/sueldos/recibosueldo";

	private String ENDPOINT_DETALLE_SUELDO = "/api/sueldos/recibosueldo/detalleRecibo";
	
	private static final Logger logger = LoggerFactory.getLogger(SucursalRestTest.class);

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	CodigoDetalleRepository codigoDetalleRepo;

	@Autowired
	ReciboSueldoRepository reciboSueldoRepo;

	@Autowired
	EmpleadoRepository empleadoRepo;

	@Autowired
	SucursalRepository sucursalRepo;

	@Autowired
	DetalleReciboRepository detalleReciboRepo;
	
	@Autowired
	DetalleReciboRepository detalleRepo;
	
	@LocalServerPort
	String puerto;

	@BeforeEach
	void limpiarRepositorios() {
		reciboSueldoRepo.deleteAll();
		detalleRepo.deleteAll();
		codigoDetalleRepo.deleteAll();
	}

	@Test
	void guardarCodigoDetalle() {
		logger.info("[RestAPI]Inicio test: guardarCodigoDetalle");
		String server = "http://localhost:" + puerto + ENDPOINT_CODIGO_DETALLE;
		// Codigo detalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");

		// Codigo detalle 2
		CodigoDetalle cd2 = new CodigoDetalle();
		cd2.setCodigoDetalle(200);
		cd2.setDescripcion("La segunda descripcion");

		// Codigo detalle 3
		CodigoDetalle cd3 = new CodigoDetalle();
		cd3.setCodigoDetalle(300);
		cd3.setDescripcion("La tercer descripcion");
		// Guardamos los datos y chequeamos el retorno
		// ---------------------------------------------------
		HttpEntity<CodigoDetalle> requestCodigoDetalle1 = new HttpEntity<>(cd1);
		HttpEntity<CodigoDetalle> requestCodigoDetalle2 = new HttpEntity<>(cd2);
		HttpEntity<CodigoDetalle> requestCodigoDetalle3 = new HttpEntity<>(cd3);
		// Chequeamos que el retorno sea el correcto
		ResponseEntity<CodigoDetalle> respuesta1 = testRestTemplate.exchange(server, HttpMethod.POST,
				requestCodigoDetalle1, CodigoDetalle.class);
		assertTrue(respuesta1.getStatusCode().equals(HttpStatus.OK));
		ResponseEntity<CodigoDetalle> respuesta2 = testRestTemplate.exchange(server, HttpMethod.POST,
				requestCodigoDetalle2, CodigoDetalle.class);
		assertTrue(respuesta2.getStatusCode().equals(HttpStatus.OK));
		ResponseEntity<CodigoDetalle> respuesta3 = testRestTemplate.exchange(server, HttpMethod.POST,
				requestCodigoDetalle3, CodigoDetalle.class);
		assertTrue(respuesta3.getStatusCode().equals(HttpStatus.OK));
		//
		cd1.setId(respuesta1.getBody().getId());
		cd2.setId(respuesta2.getBody().getId());
		cd3.setId(respuesta3.getBody().getId());

		// Chequeamos que se haya guardado en el repository
		Optional<CodigoDetalle> optRepoCd1 = codigoDetalleRepo.findById(cd1.getId());
		assertTrue(optRepoCd1.isPresent());

		Optional<CodigoDetalle> optRepoCd2 = codigoDetalleRepo.findById(cd2.getId());
		assertTrue(optRepoCd2.isPresent());

		Optional<CodigoDetalle> optRepoCd3 = codigoDetalleRepo.findById(cd3.getId());
		assertTrue(optRepoCd3.isPresent());
		logger.info("[RestAPI]Fin test: guardarCodigoDetalle");
	}

	@Test
	void getCodigoDetalle() {
		logger.info("[RestAPI]Inicio test: getCodigoDetalle");
		String server = "http://localhost:" + puerto + ENDPOINT_CODIGO_DETALLE;
		// Codigo detalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");
		// Guardamos en el repo
		CodigoDetalle cdGuardado = codigoDetalleRepo.save(cd1);
		assertNotNull(cdGuardado);

		// Re-configuramos la ruta del path
		server += "/" + cdGuardado.getCodigoDetalle();

		// Obetenemos el empleado
		ResponseEntity<CodigoDetalle> respuesta = testRestTemplate.exchange(server, HttpMethod.GET, null,
				CodigoDetalle.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));

		assertEquals(cdGuardado.getId(), respuesta.getBody().getId());
		assertEquals(cdGuardado.getDescripcion(), respuesta.getBody().getDescripcion());
		assertEquals(cdGuardado.getCodigoDetalle(), respuesta.getBody().getCodigoDetalle());
		logger.info("[RestAPI]Fin test: getCodigoDetalle");
	}

	@Test
	void getAllCodigoDetalle() {
		logger.info("[RestAPI]Inicio test: getCodigoDetalle");
		String server = "http://localhost:" + puerto + ENDPOINT_CODIGO_DETALLE;

		// Codigo detalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");
		// Codigo detalle 2
		CodigoDetalle cd2 = new CodigoDetalle();
		cd2.setCodigoDetalle(200);
		cd2.setDescripcion("La segunda descripcion");

		// Guardamos en el repo
		CodigoDetalle cdGuardado1 = codigoDetalleRepo.save(cd1);
		assertNotNull(cdGuardado1);
		CodigoDetalle cdGuardado2 = codigoDetalleRepo.save(cd2);
		assertNotNull(cdGuardado2);

		// Obetenemos los codigo detalle
		ResponseEntity<CodigoDetalle[]> respuesta = testRestTemplate.getForEntity(server, CodigoDetalle[].class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		assertTrue(respuesta.getBody().length == 2);
		logger.info("[RestAPI]Fin test: getCodigoDetalle");
	}

	@Test
	void borrarCodigoDetalle() {
		logger.info("[RestAPI]Inicio test: borrarCodigoDetalle");
		String server = "http://localhost:" + puerto + ENDPOINT_CODIGO_DETALLE;

		// Codigo detalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");

		// -------------------Guardamos el codigo detalle------------------------------
		// Chequeamos que se guarde
		CodigoDetalle cdGuardado1 = codigoDetalleRepo.save(cd1);
		assertNotNull(cdGuardado1);
		cd1 = cdGuardado1;

		// Actualizamos el path
		server += "/" + cd1.getCodigoDetalle();
		// Borramos la sucursal
		ResponseEntity<CodigoDetalle> respuesta = testRestTemplate.exchange(server, HttpMethod.DELETE, null,
				CodigoDetalle.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));

		// Verificamos si se borro
		Optional<CodigoDetalle> optDebeSerEmpty = codigoDetalleRepo.findById(cd1.getId());
		assertTrue(optDebeSerEmpty.isEmpty());
		logger.info("[RestAPI]Fin test: borrarCodigoDetalle");

	}

	@Test
	void borrarCodigoDetalle_badRequest_codigoDetalleInexistente() {
		logger.info("[RestAPI]Inicio test: borrarCodigoDetalle_badRequest_codigoDetalleInexistente");
		String server = "http://localhost:" + puerto + ENDPOINT_CODIGO_DETALLE;
		// Actualizamos el path
		server += "/" + 9998441;
		// Borramos la sucursal
		ResponseEntity<DatoBancario> respuesta = testRestTemplate.exchange(server, HttpMethod.DELETE, null,
				DatoBancario.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		logger.info("[RestAPI]Fin test: borrarCodigoDetalle_badRequest_codigoDetalleInexistente");

	}

	@Test
	void actualizarCodigoDetalle() {
		logger.info("[RestAPI]Inicio test: actualizarCodigoDetalle");
		String server = "http://localhost:" + puerto + ENDPOINT_CODIGO_DETALLE;
		// Codigo detalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");

		// -------------------Guardamos el codigo detalle------------------------------
		// Chequeamos que se guarde
		CodigoDetalle cdGuardado1 = codigoDetalleRepo.save(cd1);
		assertNotNull(cdGuardado1);
		// Actualizamos valores
		cd1 = cdGuardado1;
		server += "/" + cd1.getId();

		// Cambioamos datos para actualizar luego
		cd1.setDescripcion("Descripcion cambiada");

		// La actualizamos
		HttpEntity<CodigoDetalle> requestCodigoDetalle = new HttpEntity<>(cd1);
		ResponseEntity<CodigoDetalle> respuesta = testRestTemplate.exchange(server, HttpMethod.PUT,
				requestCodigoDetalle, CodigoDetalle.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		// Chequeamos en el repo los cambios
		Optional<CodigoDetalle> optCodigoDetalleActualizado = codigoDetalleRepo.findById(cd1.getId());
		assertEquals(cd1.getDescripcion(), optCodigoDetalleActualizado.get().getDescripcion());
		logger.info("[RestAPI]Fin test: actualizarCodigoDetalle");
	}

	@Test
	void actualizarDatoBancario_noExistente() {
		logger.info("[RestAPI]Inicio test: actualizarDatoBancario_noExistente");
		String server = "http://localhost:" + puerto + ENDPOINT_CODIGO_DETALLE;
		server += "/" + 999514;
		CodigoDetalle cd1 = new CodigoDetalle(); // La actualizamos
		HttpEntity<CodigoDetalle> requestDatoBancario = new HttpEntity<>(cd1);
		ResponseEntity<CodigoDetalle> respuesta = testRestTemplate.exchange(server, HttpMethod.PUT, requestDatoBancario,
				CodigoDetalle.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		logger.info("[RestAPI]Fin test: actualizarDatoBancario_noExistente");

	}

	/*-------------------Recibo de sueldo--------------------------*/
	@Test
	void getReciboSueldo() {
		logger.info("[RestAPI]Inicio test: getReciboSueldo");
		String server = "http://localhost:" + puerto + ENDPOINT_RECIBO_SUELDO;
		// Recibo sueldo 1
		ReciboSueldo rs = new ReciboSueldo();
		rs.setTotalBruto(1500.0);
		rs.setTotalNeto(130.0);
		rs.setLugarDePago("ACA SE PAGA");
		rs.setSucursal(null);
		rs.setPagado(true);
		rs.setFechaEmision(Date.valueOf("2021-07-21"));
		rs.setFechaDePago(Date.valueOf("2021-07-15"));
		rs.setDeducciones(15000000.1);
		rs.setNumeroRecibo(70);
		// Lo guardamos
		ReciboSueldo rsGuardado = reciboSueldoRepo.save(rs);
		assertNotNull(rsGuardado);
		// Re-configuramos la ruta del path
		server += "/" + rsGuardado.getId();

		// Obetenemos el empleado
		ResponseEntity<ReciboSueldo> respuesta = testRestTemplate.exchange(server, HttpMethod.GET, null,
				ReciboSueldo.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));

		assertEquals(rsGuardado.getId(), respuesta.getBody().getId());
		assertEquals(rsGuardado.getTotalBruto(), respuesta.getBody().getTotalBruto());
		assertEquals(rsGuardado.getNumeroRecibo(), respuesta.getBody().getNumeroRecibo());
		logger.info("[RestAPI]Fin test: getReciboSueldo");
	}

	@Test
	void getAllReciboSueldo() {
		logger.info("[RestAPI]Inicio test: getAllReciboSueldo");
		String server = "http://localhost:" + puerto + ENDPOINT_RECIBO_SUELDO;
		// Recibo sueldo 1
		ReciboSueldo rs = new ReciboSueldo();
		rs.setTotalBruto(1500.0);
		rs.setTotalNeto(130.0);
		rs.setLugarDePago("ACA SE PAGA");
		rs.setSucursal(null);
		rs.setPagado(true);
		rs.setFechaEmision(Date.valueOf("2021-07-21"));
		rs.setFechaDePago(Date.valueOf("2021-07-15"));
		rs.setDeducciones(15000000.1);
		rs.setNumeroRecibo(70);
		// Recibo sueldo 2
		ReciboSueldo rs2 = new ReciboSueldo();
		rs2.setTotalBruto(1544.0);
		rs2.setTotalNeto(89.0);
		rs2.setLugarDePago("Lugar de cobro");
		rs2.setSucursal(null);
		rs2.setPagado(true);
		rs2.setFechaEmision(Date.valueOf("2021-09-21"));
		rs2.setFechaDePago(Date.valueOf("2021-09-15"));
		rs2.setDeducciones(15000.1);
		rs2.setNumeroRecibo(71);
		// SUCURSAL
		Sucursal sucursal = new Sucursal();
		sucursal = sucursalRepo.save(sucursal); // Guardamos la sucursal
		assertNotNull(sucursal);
		rs.setSucursal(sucursal);
		rs2.setSucursal(sucursal);

		// EMPLEADO
		Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
		assertTrue(optEmpleado1.isPresent());
		rs.setEmpleado(optEmpleado1.get());
		rs2.setEmpleado(optEmpleado1.get());
		// CODIGO DETALLE
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");
		cd1 = codigoDetalleRepo.saveAndFlush(cd1);
		assertNotNull(cd1);
		// DETALLE RECIBO
		DetalleRecibo dr1 = new DetalleRecibo();
		dr1 = detalleReciboRepo.save(dr1);
		assertNotNull(dr1);
		dr1.setCodigoDetalle(cd1);
		rs.addDetalleRecibo(dr1);
		rs.addDetalleRecibo(dr1);
		// Lo guardamos
		ReciboSueldo rsGuardado = reciboSueldoRepo.save(rs);
		assertNotNull(rsGuardado);
		ReciboSueldo rsGuardado2 = reciboSueldoRepo.save(rs2);
		assertNotNull(rsGuardado2);

		// Obetenemos el empleado
		ResponseEntity<ReciboSueldo[]> respuesta = testRestTemplate.getForEntity(server, ReciboSueldo[].class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		assertTrue(respuesta.getBody().length == 2);
		logger.info("[RestAPI]Fin test: getAllReciboSueldo");
	}

	@Test
	void actualizarReciboSueldo() {
		logger.info("[RestAPI]Inicio test: actualizarReciboSueldo");
		String server = "http://localhost:" + puerto + ENDPOINT_RECIBO_SUELDO;
		// Recibo sueldo 1
		ReciboSueldo rs = new ReciboSueldo();
		rs.setTotalBruto(1500.0);
		rs.setTotalNeto(130.0);
		rs.setLugarDePago("ACA SE PAGA");
		rs.setSucursal(null);
		rs.setPagado(true);
		rs.setFechaEmision(Date.valueOf("2021-07-21"));
		rs.setFechaDePago(Date.valueOf("2021-07-15"));
		rs.setDeducciones(15000000.1);
		rs.setNumeroRecibo(70);
		// SUCURSAL
		Sucursal sucursal = new Sucursal();
		sucursal = sucursalRepo.save(sucursal); // Guardamos la sucursal
		assertNotNull(sucursal);
		rs.setSucursal(sucursal);

		// EMPLEADO
		Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
		assertTrue(optEmpleado1.isPresent());
		rs.setEmpleado(optEmpleado1.get());

		// CODIGO DETALLE
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");
		cd1 = codigoDetalleRepo.saveAndFlush(cd1);
		assertNotNull(cd1);
		// DETALLE RECIBO
		DetalleRecibo dr1 = new DetalleRecibo();
		dr1 = detalleReciboRepo.save(dr1);
		assertNotNull(dr1);
		dr1.setCodigoDetalle(cd1);
		rs.addDetalleRecibo(dr1);

		// -------------------Guardamos el recibo------------------------------
		// Chequeamos que se guarde
		ReciboSueldo rsGuardado = reciboSueldoRepo.save(rs);
		assertNotNull(rsGuardado);

		// Actualizamos valores
		rs = rsGuardado;
		server += "/" + rs.getId();

		// Cambioamos datos para actualizar luego
		rs.setNumeroRecibo(99);
		rs.setLugarDePago("Aca hay que tener cuidado con los chorros de la salida");

		// La actualizamos
		HttpEntity<ReciboSueldo> requestReciboSueldo = new HttpEntity<>(rs);
		ResponseEntity<ReciboSueldo> respuesta = testRestTemplate.exchange(server, HttpMethod.PUT, requestReciboSueldo,
				ReciboSueldo.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));

		// Chequeamos en el repo los cambios
		Optional<ReciboSueldo> optReciboSueldoActualizado = reciboSueldoRepo.findById(rs.getId());
		assertEquals(rs.getNumeroRecibo(), optReciboSueldoActualizado.get().getNumeroRecibo());
		assertEquals(rs.getLugarDePago(), optReciboSueldoActualizado.get().getLugarDePago());
		logger.info("[RestAPI]Fin test: actualizarReciboSueldo");
	}
	
	/*-----------------------------Detalle recibo--------------------------------------*/
	
	@Test
	void getDetalleRecibo() {
		// codigoDetalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");

		// ---------------------------------------------------

		cd1 = codigoDetalleRepo.saveAndFlush(cd1);

		// detalleRecibo 1
		DetalleRecibo dr1 = new DetalleRecibo();
		dr1.setCodigoDetalle(cd1);
		dr1.setHaber(1500.00);
		dr1.setDeduccion(null);
		dr1.setPorcentaje(10.5);

		detalleRepo.saveAndFlush(dr1);

		logger.info("[RestAPI]Inicio test: getDetalleSueldo");
		String server = "http://localhost:" + puerto + ENDPOINT_DETALLE_SUELDO;

		// Re-configuramos la ruta del path
		server += "/" + dr1.getId();

		// Detalle Recibo
		ResponseEntity<DetalleRecibo> respuesta = testRestTemplate.exchange(server, HttpMethod.GET, null,
				DetalleRecibo.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));

		assertEquals(dr1.getId(), respuesta.getBody().getId());
		assertEquals(dr1.getDeduccion(), respuesta.getBody().getDeduccion());
		assertEquals(dr1.getHaber(), respuesta.getBody().getHaber());
		assertEquals(dr1.getPorcentaje(), respuesta.getBody().getPorcentaje());
		assertEquals(dr1.getCodigoDetalle().getCodigoDetalle(),
				respuesta.getBody().getCodigoDetalle().getCodigoDetalle());
		assertEquals(dr1.getCodigoDetalle().getDescripcion(), respuesta.getBody().getCodigoDetalle().getDescripcion());
		logger.info("[RestAPI]Fin test: getDetalleRecibo");
	}

	@Test
	void getAllDetalleRecibo() {
		// codigoDetalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");

		// codigoDetalle 2
		CodigoDetalle cd2 = new CodigoDetalle();
		cd2.setCodigoDetalle(200);
		cd2.setDescripcion("La segunda descripcion");

		// codigoDetalle 3
		CodigoDetalle cd3 = new CodigoDetalle();
		cd3.setCodigoDetalle(300);
		cd3.setDescripcion("La tercer descripcion");

		// ---------------------------------------------------

		cd1 = codigoDetalleRepo.saveAndFlush(cd1);
		cd2 = codigoDetalleRepo.saveAndFlush(cd2);
		cd3 = codigoDetalleRepo.saveAndFlush(cd3);

		// detalleRecibo 1
		DetalleRecibo dr1 = new DetalleRecibo();
		dr1.setCodigoDetalle(cd1);
		dr1.setHaber(1500.00);
		dr1.setDeduccion(null);
		dr1.setPorcentaje(10.5);

		// detalleRecibo 2
		DetalleRecibo dr2 = new DetalleRecibo();
		dr2.setCodigoDetalle(cd2);
		dr2.setHaber(null);
		dr2.setDeduccion(300.0);
		dr2.setPorcentaje(15.0);

		// detalleRecibo 2
		DetalleRecibo dr3 = new DetalleRecibo();
		dr3.setCodigoDetalle(cd3);
		dr3.setHaber(400.0);
		dr3.setDeduccion(null);
		dr3.setPorcentaje(40.0);

		// Guardamos en el repo

		detalleRepo.saveAndFlush(dr1);
		detalleRepo.saveAndFlush(dr2);
		detalleRepo.saveAndFlush(dr3);

		logger.info("[RestAPI]Inicio test: getAllDetalleRecibo");
		String server = "http://localhost:" + puerto + ENDPOINT_DETALLE_SUELDO;

		// Obetenemos los codigo detalle
		ResponseEntity<DetalleRecibo[]> respuesta = testRestTemplate.getForEntity(server, DetalleRecibo[].class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		assertTrue(respuesta.getBody().length == 3);
		logger.info("[RestAPI]Fin test: getAllDetalleRecibo");
	}

	@Test
	void actualizarDetalleRecibo() {
		logger.info("[RestAPI]Inicio test: actualizarDetalleRecibo");
		String server = "http://localhost:" + puerto + ENDPOINT_DETALLE_SUELDO;
		// codigoDetalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");

		// codigoDetalle 2
		CodigoDetalle cd2 = new CodigoDetalle();
		cd2.setCodigoDetalle(200);
		cd2.setDescripcion("La segunda descripcion");

		// ---------------------------------------------------

		cd1 = codigoDetalleRepo.saveAndFlush(cd1);
		cd2 = codigoDetalleRepo.saveAndFlush(cd2);

		// detalleRecibo 1
		DetalleRecibo dr1 = new DetalleRecibo();
		dr1.setCodigoDetalle(cd1);
		dr1.setHaber(1500.00);
		dr1.setDeduccion(null);
		dr1.setPorcentaje(10.5);

		detalleRepo.saveAndFlush(dr1);

		// Re-configuramos la ruta del path
		server += "/" + dr1.getId();

		// Actualizamos parametros
		
		dr1.setCodigoDetalle(cd2);
		dr1.setDeduccion(1000.0);
		dr1.setHaber(null);
		dr1.setPorcentaje(8.3);

		
		// La actualizamos
		HttpEntity<DetalleRecibo> requestDetalleRecibo = new HttpEntity<>(dr1);
		ResponseEntity<DetalleRecibo> respuesta = testRestTemplate.exchange(server, HttpMethod.PUT, 
				requestDetalleRecibo,DetalleRecibo.class);
		
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		assertEquals(dr1.getId(), respuesta.getBody().getId());
		assertEquals(dr1.getDeduccion(), respuesta.getBody().getDeduccion());
		assertEquals(dr1.getHaber(), respuesta.getBody().getHaber());
		assertEquals(dr1.getPorcentaje(), respuesta.getBody().getPorcentaje());
		assertEquals(dr1.getCodigoDetalle().getCodigoDetalle(),
				respuesta.getBody().getCodigoDetalle().getCodigoDetalle());
		assertEquals(dr1.getCodigoDetalle().getDescripcion(), respuesta.getBody().getCodigoDetalle().getDescripcion());
	

		// Chequeamos en el repo los cambios
		Optional<DetalleRecibo> optCodigoDetalleActualizado = detalleRepo.findById(dr1.getId());
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		assertEquals(dr1.getId(), optCodigoDetalleActualizado.get().getId());
		assertEquals(dr1.getDeduccion(), optCodigoDetalleActualizado.get().getDeduccion());
		assertEquals(dr1.getHaber(), optCodigoDetalleActualizado.get().getHaber());
		assertEquals(dr1.getPorcentaje(), optCodigoDetalleActualizado.get().getPorcentaje());
		assertEquals(dr1.getCodigoDetalle().getCodigoDetalle(),
				optCodigoDetalleActualizado.get().getCodigoDetalle().getCodigoDetalle());
		assertEquals(dr1.getCodigoDetalle().getDescripcion(),
				optCodigoDetalleActualizado.get().getCodigoDetalle().getDescripcion());
		logger.info("[RestAPI]Fin test: actualizarDetalleRecibo");
	}

}
