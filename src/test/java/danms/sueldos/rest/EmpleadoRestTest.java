package danms.sueldos.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import danms.sueldos.domain.DatoBancario;
import danms.sueldos.domain.Empleado;
import danms.sueldos.domain.Sucursal;
import danms.sueldos.services.dao.DatoBancarioRepository;
import danms.sueldos.services.dao.EmpleadoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
public class EmpleadoRestTest {

	private String ENDPOINT_EMPLEADO = "/api/sueldos/empleado";

	private String ENDPOINT_EMPLEADO_DATOBANCARIO = "/api/sueldos/empleado/datobancario";

	private static final Logger logger = LoggerFactory.getLogger(SucursalRestTest.class);

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	EmpleadoRepository empleadoRepo;

	@Autowired
	DatoBancarioRepository datoBancarioRepo;

	@LocalServerPort
	String puerto;

	@BeforeEach
	void limpiarRepositorios() {
		datoBancarioRepo.deleteAll();
	}

	/*---------------------Empleado----------------------------------*/

	@Test
	void getEmpleado() {
		logger.info("[RestAPI]Inicio test: getSucursal");
		String server = "http://localhost:" + puerto + ENDPOINT_EMPLEADO;
		// Re-configuramos la ruta del path
		server += "/" + 1;

		// Obetenemos el empleado
		ResponseEntity<Empleado> respuesta = testRestTemplate.exchange(server, HttpMethod.GET, null, Empleado.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		assertEquals(1, respuesta.getBody().getId());
		assertEquals("cindyEntes@gmail.com", respuesta.getBody().getEmail());
		logger.info("[RestAPI]Fin test: getSucursal");
	}

	@Test
	void getAllEmpleado() {
		logger.info("[RestAPI]Inicio test: getAllEmpleado");
		String server = "http://localhost:" + puerto + ENDPOINT_EMPLEADO;

		// Obetemos todas por el rest api
		ResponseEntity<Empleado[]> respuesta = testRestTemplate.getForEntity(server, Empleado[].class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		assertTrue(respuesta.getBody().length == 3);
		logger.info("[RestAPI]Fin test: getAllEmpleado");

	}

	/*---------------------Dato bancario----------------------------------*/

	@Test
	void guardarDatoBancario() {
		logger.info("[RestAPI]Inicio test: guardarDatoBancario");
		String server = "http://localhost:" + puerto + ENDPOINT_EMPLEADO_DATOBANCARIO;
		// EMPLEADOS NECESARIOS:
		Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
		assertTrue(optEmpleado1.isPresent());
		Optional<Empleado> optEmpleado2 = empleadoRepo.findById(2);
		assertTrue(optEmpleado2.isPresent());
		Optional<Empleado> optEmpleado3 = empleadoRepo.findById(3);
		assertTrue(optEmpleado3.isPresent());
		// ---------------------------------------------------
		// DATO BANCARIO 1
		DatoBancario datoBancario1 = new DatoBancario();
		datoBancario1.setNombreBanco("Banco Entre Rios");
		datoBancario1.setNumeroCuenta("256335889956215823");
		datoBancario1.setEmpleado(optEmpleado1.get());
		// DATO BANCARIO 2
		DatoBancario datoBancario2 = new DatoBancario();
		datoBancario2.setNombreBanco("Banco Santader");
		datoBancario2.setNumeroCuenta("25435567889903");
		datoBancario2.setEmpleado(optEmpleado2.get());
		// DATO BANCARIO 3
		DatoBancario datoBancario3 = new DatoBancario();
		datoBancario3.setNombreBanco("Banco Galicia");
		datoBancario3.setNumeroCuenta("25633423567215823");
		datoBancario3.setEmpleado(optEmpleado3.get());
		// Guardamos los datos y chequeamos el retorno
		// ---------------------------------------------------
		HttpEntity<DatoBancario> requestDatoBancario1 = new HttpEntity<>(datoBancario1);
		HttpEntity<DatoBancario> requestDatoBancario2 = new HttpEntity<>(datoBancario2);
		HttpEntity<DatoBancario> requestDatoBancario3 = new HttpEntity<>(datoBancario3);
		// Chequeamos que el retorno sea el correcto
		ResponseEntity<DatoBancario> respuesta1 = testRestTemplate.exchange(server, HttpMethod.POST,
				requestDatoBancario1, DatoBancario.class);
		assertTrue(respuesta1.getStatusCode().equals(HttpStatus.OK));
		ResponseEntity<DatoBancario> respuesta2 = testRestTemplate.exchange(server, HttpMethod.POST,
				requestDatoBancario2, DatoBancario.class);
		assertTrue(respuesta2.getStatusCode().equals(HttpStatus.OK));
		ResponseEntity<DatoBancario> respuesta3 = testRestTemplate.exchange(server, HttpMethod.POST,
				requestDatoBancario3, DatoBancario.class);
		assertTrue(respuesta3.getStatusCode().equals(HttpStatus.OK));
		//
		datoBancario1.setId(respuesta1.getBody().getId());
		datoBancario2.setId(respuesta2.getBody().getId());
		datoBancario3.setId(respuesta3.getBody().getId());

		// Chequeamos que se haya guardado en el repository
		Optional<DatoBancario> optRepoD1 = datoBancarioRepo.findById(datoBancario1.getId());
		assertTrue(optRepoD1.isPresent());

		Optional<DatoBancario> optRepoD2 = datoBancarioRepo.findById(datoBancario2.getId());
		assertTrue(optRepoD2.isPresent());

		Optional<DatoBancario> optRepoD3 = datoBancarioRepo.findById(datoBancario3.getId());
		assertTrue(optRepoD3.isPresent());
		logger.info("[RestAPI]Fin test: guardarDatoBancario");
	}

	@Test
	void guardarDatoBancario_BadRequest_NoTieneAsociadoEmpleado() {
		logger.info("[RestAPI]Inicio test: guardarDatoBancario_BadRequest_NoTieneAsociadoEmpleado");
		String server = "http://localhost:" + puerto + ENDPOINT_EMPLEADO_DATOBANCARIO;
		// ---------------------------------------------------
		// DATO BANCARIO 1
		DatoBancario datoBancario1 = new DatoBancario();
		datoBancario1.setNombreBanco("Banco Entre Rios");
		datoBancario1.setNumeroCuenta("256335889956215823");
		datoBancario1.setEmpleado(null);
		// Guardamos los datos y chequeamos el retorno
		// ---------------------------------------------------
		HttpEntity<DatoBancario> requestDatoBancario1 = new HttpEntity<>(datoBancario1);
		// Chequeamos que el retorno sea el correcto
		ResponseEntity<DatoBancario> respuesta1 = testRestTemplate.exchange(server, HttpMethod.POST,
				requestDatoBancario1, DatoBancario.class);
		assertTrue(respuesta1.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		logger.info("[RestAPI]Fin test: guardarDatoBancario_BadRequest_NoTieneAsociadoEmpleado");
	}

	@Test
	void getDatoBancario() {
		logger.info("[RestAPI]Inicio test: getDatoBancario");
		String server = "http://localhost:" + puerto + ENDPOINT_EMPLEADO_DATOBANCARIO;
		// EMPLEADOS NECESARIOS:
		Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
		assertTrue(optEmpleado1.isPresent());
		// DATO BANCARIO 1
		DatoBancario datoBancario1 = new DatoBancario();
		datoBancario1.setNombreBanco("Banco Entre Rios");
		datoBancario1.setNumeroCuenta("256335889956215823");
		datoBancario1.setEmpleado(optEmpleado1.get());

		// -------------------Guardamos el dato bancario------------------------------
		// Chequeamos que se guarde
		DatoBancario repoDatoB1 = datoBancarioRepo.save(datoBancario1);
		assertNotNull(repoDatoB1);
		// Re-configuramos la ruta del path
		server += "/" + repoDatoB1.getId();

		// Obetenemos el dato bancario
		ResponseEntity<DatoBancario> respuesta = testRestTemplate.exchange(server, HttpMethod.GET, null,
				DatoBancario.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		assertEquals(repoDatoB1.getId(), respuesta.getBody().getId());
		assertEquals(repoDatoB1.getNombreBanco(), respuesta.getBody().getNombreBanco());
		logger.info("[RestAPI]Fin test: getDatoBancario");
	}

	@Test
	void getDatoBancario_porIdEmpleado() {
		logger.info("[RestAPI]Inicio test: getDatoBancario_porIdEmpleado");
		String server = "http://localhost:" + puerto + ENDPOINT_EMPLEADO_DATOBANCARIO;
		// EMPLEADOS NECESARIOS:
		Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
		assertTrue(optEmpleado1.isPresent());
		Optional<Empleado> optEmpleado2 = empleadoRepo.findById(2);
		assertTrue(optEmpleado2.isPresent());
		// DATO BANCARIO 1
		DatoBancario datoBancario1 = new DatoBancario();
		datoBancario1.setNombreBanco("Banco Entre Rios");
		datoBancario1.setNumeroCuenta("256335889956215823");
		datoBancario1.setEmpleado(optEmpleado1.get());
		// DATO BANCARIO 2
		DatoBancario datoBancario2 = new DatoBancario();
		datoBancario2.setNombreBanco("Banco Santader");
		datoBancario2.setNumeroCuenta("25435567889903");
		datoBancario2.setEmpleado(optEmpleado1.get());
		// DATO BANCARIO 3
		DatoBancario datoBancario3 = new DatoBancario();
		datoBancario3.setNombreBanco("Banco Galicia");
		datoBancario3.setNumeroCuenta("25633423567215823");
		datoBancario3.setEmpleado(optEmpleado2.get());

		// -------------------Guardamos el dato bancario------------------------------
		// Chequeamos que se guarde
		DatoBancario repoDatoB1 = datoBancarioRepo.save(datoBancario1);
		assertNotNull(repoDatoB1);
		datoBancario1 = repoDatoB1;
		DatoBancario repoDatoB2 = datoBancarioRepo.save(datoBancario2);
		assertNotNull(repoDatoB2);
		datoBancario2 = repoDatoB2;
		DatoBancario repoDatoB3 = datoBancarioRepo.save(datoBancario3);
		assertNotNull(repoDatoB3);
		datoBancario3 = repoDatoB3;
		// Re-configuramos la ruta del path
		String server1 = server + "?idEmpleado=1";
		String server2 = server + "?idEmpleado=2";

		// Obetenemos el dato bancario para el empleado 1
		ResponseEntity<DatoBancario[]> respuesta1 = testRestTemplate.getForEntity(server1, DatoBancario[].class);
		logger.debug("Se retornaron: " + respuesta1.getBody().length + " datos bancarios para el empleado 1");
		assertTrue(respuesta1.getStatusCode().equals(HttpStatus.OK));
		assertTrue(respuesta1.getBody().length == 2);

		// Obetenemos el dato bancario para el empleado 2
		ResponseEntity<DatoBancario[]> respuesta2 = testRestTemplate.getForEntity(server2, DatoBancario[].class);
		logger.debug("Se retornaron: " + respuesta1.getBody().length + " datos bancarios para el empleado 2");
		assertTrue(respuesta2.getStatusCode().equals(HttpStatus.OK));
		assertTrue(respuesta2.getBody().length == 1);
		logger.info("[RestAPI]Fin test: getDatoBancario_porIdEmpleado");
	}

	@Test
	void getAllDatoBancario() {
		logger.info("[RestAPI]Inicio test: getAllDatoBancario");
		String server = "http://localhost:" + puerto + ENDPOINT_EMPLEADO_DATOBANCARIO;
		// EMPLEADOS NECESARIOS:
		Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
		assertTrue(optEmpleado1.isPresent());
		Optional<Empleado> optEmpleado2 = empleadoRepo.findById(2);
		assertTrue(optEmpleado2.isPresent());
		// DATO BANCARIO 1
		DatoBancario datoBancario1 = new DatoBancario();
		datoBancario1.setNombreBanco("Banco Entre Rios");
		datoBancario1.setNumeroCuenta("256335889956215823");
		datoBancario1.setEmpleado(optEmpleado1.get());
		// DATO BANCARIO 2
		DatoBancario datoBancario2 = new DatoBancario();
		datoBancario2.setNombreBanco("Banco Santader");
		datoBancario2.setNumeroCuenta("25435567889903");
		datoBancario2.setEmpleado(optEmpleado1.get());
		// DATO BANCARIO 3
		DatoBancario datoBancario3 = new DatoBancario();
		datoBancario3.setNombreBanco("Banco Galicia");
		datoBancario3.setNumeroCuenta("25633423567215823");
		datoBancario3.setEmpleado(optEmpleado2.get());

		// -------------------Guardamos el dato bancario------------------------------
		// Chequeamos que se guarde
		DatoBancario repoDatoB1 = datoBancarioRepo.save(datoBancario1);
		assertNotNull(repoDatoB1);
		datoBancario1 = repoDatoB1;
		DatoBancario repoDatoB2 = datoBancarioRepo.save(datoBancario2);
		assertNotNull(repoDatoB2);
		datoBancario2 = repoDatoB2;
		DatoBancario repoDatoB3 = datoBancarioRepo.save(datoBancario3);
		assertNotNull(repoDatoB3);
		datoBancario3 = repoDatoB3;
		// Obetenemos el dato bancario para el empleado 1
		ResponseEntity<DatoBancario[]> respuesta1 = testRestTemplate.getForEntity(server, DatoBancario[].class);
		assertTrue(respuesta1.getStatusCode().equals(HttpStatus.OK));
		assertTrue(respuesta1.getBody().length == 3);
		logger.info("[RestAPI]Fin test: getAllDatoBancario");
	}

	@Test
	void borrarDatoBancario() {
		logger.info("[RestAPI]Inicio test: borrarDatoBancario");
		String server = "http://localhost:" + puerto + ENDPOINT_EMPLEADO_DATOBANCARIO;
		// EMPLEADOS NECESARIOS:
		Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
		assertTrue(optEmpleado1.isPresent());
		// DATO BANCARIO 1
		DatoBancario datoBancario1 = new DatoBancario();
		datoBancario1.setNombreBanco("Banco Entre Rios");
		datoBancario1.setNumeroCuenta("256335889956215823");
		datoBancario1.setEmpleado(optEmpleado1.get());
		// -------------------Guardamos el dato bancario------------------------------
		// Chequeamos que se guarde
		DatoBancario repoDatoB1 = datoBancarioRepo.save(datoBancario1);
		assertNotNull(repoDatoB1);
		datoBancario1 = repoDatoB1;

		// Actualizamos el path
		server += "/" + repoDatoB1.getId();
		// Borramos la sucursal
		ResponseEntity<DatoBancario> respuesta = testRestTemplate.exchange(server, HttpMethod.DELETE, null,
				DatoBancario.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));

		// Verificamos si se borro
		Optional<DatoBancario> optDebeSerEmpty = datoBancarioRepo.findById(datoBancario1.getId());
		assertTrue(optDebeSerEmpty.isEmpty());
		logger.info("[RestAPI]Fin test: borrarDatoBancario");

	}

	@Test
	void borrarDatoBancario_badRequest_datoBancarioInexistente() {
		logger.info("[RestAPI]Inicio test: borrarDatoBancario");
		String server = "http://localhost:" + puerto + ENDPOINT_EMPLEADO_DATOBANCARIO;
		// Actualizamos el path
		server += "/" + 9998441;
		// Borramos la sucursal
		ResponseEntity<DatoBancario> respuesta = testRestTemplate.exchange(server, HttpMethod.DELETE, null,
				DatoBancario.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		logger.info("[RestAPI]Fin test: borrarDatoBancario");

	}

	@Test
	void actualizarDatoBancario() {
		logger.info("[RestAPI]Inicio test: actualizarSucursal");
		String server = "http://localhost:" + puerto + ENDPOINT_EMPLEADO_DATOBANCARIO;
		// EMPLEADOS NECESARIOS:
		Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
		assertTrue(optEmpleado1.isPresent());
		// DATO BANCARIO 1
		DatoBancario datoBancario1 = new DatoBancario();
		datoBancario1.setNombreBanco("Banco Entre Rios");
		datoBancario1.setNumeroCuenta("256335889956215823");
		datoBancario1.setEmpleado(optEmpleado1.get());
		// -------------------Guardamos el dato bancario------------------------------
		// Chequeamos que se guarde
		DatoBancario repoDatoB1 = datoBancarioRepo.save(datoBancario1);
		assertNotNull(repoDatoB1);
		// Actualizamos valores
		datoBancario1 = repoDatoB1;
		server += "/" + datoBancario1.getId();
		// Cambioamos datos para actualizar luego
		datoBancario1.setNombreBanco("Banco de Hierro");
		// La actualizamos
		HttpEntity<DatoBancario> requestDatoBancario = new HttpEntity<>(datoBancario1);
		ResponseEntity<DatoBancario> respuesta = testRestTemplate.exchange(server, HttpMethod.PUT, requestDatoBancario,
				DatoBancario.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		// Chequeamos en el repo los cambios
		Optional<DatoBancario> optDatoBancarioActualizado = datoBancarioRepo.findById(datoBancario1.getId());
		assertEquals(datoBancario1.getNombreBanco(), optDatoBancarioActualizado.get().getNombreBanco());
		logger.info("[RestAPI]Fin test: actualizarSucursal");
	}

	@Test
	void actualizarDatoBancario_noExistente() {
		logger.info("[RestAPI]Inicio test: actualizarDatoBancario_noExistente");
		String server = "http://localhost:" + puerto + ENDPOINT_EMPLEADO_DATOBANCARIO;
		server += "/" + 999514;
		DatoBancario dB1 = new DatoBancario(); // La actualizamos
		HttpEntity<DatoBancario> requestDatoBancario = new HttpEntity<>(dB1);
		ResponseEntity<DatoBancario> respuesta = testRestTemplate.exchange(server, HttpMethod.PUT, requestDatoBancario,
				DatoBancario.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		logger.info("[RestAPI]Fin test: actualizarDatoBancario_noExistente");

	}

}
