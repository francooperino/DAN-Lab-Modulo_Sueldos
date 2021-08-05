package danms.sueldos.rest;

import static org.junit.jupiter.api.Assertions.*;

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
import danms.sueldos.domain.Empleado;
import danms.sueldos.services.dao.CodigoDetalleRepository;
import danms.sueldos.services.dao.DatoBancarioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
class ReciboSueldoRestTest {

	private String ENDPOINT_CODIGO_DETALLE = "/api/sueldos/recibosueldo/codigodetalle";

	private static final Logger logger = LoggerFactory.getLogger(SucursalRestTest.class);

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	CodigoDetalleRepository codigoDetalleRepo;

	@LocalServerPort
	String puerto;

	@BeforeEach
	void limpiarRepositorios() {
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
		ResponseEntity<CodigoDetalle> respuesta = testRestTemplate.exchange(server, HttpMethod.PUT, requestCodigoDetalle,
				CodigoDetalle.class);
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

}
