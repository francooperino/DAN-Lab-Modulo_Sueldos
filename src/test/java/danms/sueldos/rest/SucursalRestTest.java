package danms.sueldos.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import danms.sueldos.domain.Empleado;
import danms.sueldos.domain.Sucursal;
import danms.sueldos.services.dao.EmpleadoRepository;
import danms.sueldos.services.dao.SucursalRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
class SucursalRestTest {

	private String ENDPOINT_SUCURSAL = "/api/sueldos/sucursal";

	private static final Logger logger = LoggerFactory.getLogger(SucursalRestTest.class);

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	SucursalRepository sucursalRepo;
	
	@Autowired
	EmpleadoRepository empleadoRepo;

	@LocalServerPort
	String puerto;

	@BeforeEach
	void limpiarRepositorios() {
		sucursalRepo.deleteAll();
	}

	@Test
	void guardarSucursales_sinEmpleados() {
		logger.info("[RestAPI]Inicio test: guardarSucursales");
		String server = "http://localhost:" + puerto + ENDPOINT_SUCURSAL;
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");
		// Sucursal 2
		Sucursal s2 = new Sucursal();
		s2.setCiudad("Nogoya");
		s2.setCuitEmpresa("30-289615936");
		s2.setDireccion("San Martin 331");
		// Sucursal 3
		Sucursal s3 = new Sucursal();
		s3.setCiudad("Parana");
		s3.setCuitEmpresa("30-289615936");
		s3.setDireccion("Belgrano 658");
		// ---------------------------------------------------
		HttpEntity<Sucursal> requestSucursal1 = new HttpEntity<>(s1);
		HttpEntity<Sucursal> requestSucursal2 = new HttpEntity<>(s2);
		HttpEntity<Sucursal> requestSucursal3 = new HttpEntity<>(s3);
		// Chequeamos que el retorno sea el correcto
		ResponseEntity<Sucursal> respuesta1 = testRestTemplate.exchange(server, HttpMethod.POST, requestSucursal1,
				Sucursal.class);
		assertTrue(respuesta1.getStatusCode().equals(HttpStatus.OK));
		ResponseEntity<Sucursal> respuesta2 = testRestTemplate.exchange(server, HttpMethod.POST, requestSucursal2,
				Sucursal.class);
		assertTrue(respuesta2.getStatusCode().equals(HttpStatus.OK));
		ResponseEntity<Sucursal> respuesta3 = testRestTemplate.exchange(server, HttpMethod.POST, requestSucursal3,
				Sucursal.class);
		assertTrue(respuesta3.getStatusCode().equals(HttpStatus.OK));
		//
		s1.setId(respuesta1.getBody().getId());
		s2.setId(respuesta2.getBody().getId());
		s3.setId(respuesta3.getBody().getId());

		// Chequeamos que se haya guardado en el repository
		Optional<Sucursal> optRepoS1 = sucursalRepo.findById(s1.getId());
		assertTrue(optRepoS1.isPresent());

		Optional<Sucursal> optRepoS2 = sucursalRepo.findById(s2.getId());
		assertTrue(optRepoS2.isPresent());

		Optional<Sucursal> optRepoS3 = sucursalRepo.findById(s3.getId());
		assertTrue(optRepoS3.isPresent());
		logger.info("[RestAPI]Fin test: guardarSucursales");
	}
	@Test
	void guardarSucursales_conEmpleados() {
		logger.info("[RestAPI]Inicio test: guardarSucursales_conEmpleados");
		String server = "http://localhost:" + puerto + ENDPOINT_SUCURSAL;
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");
		//Empleado 1
		Optional<Empleado> optEmpleado = empleadoRepo.findById(1);
		assertTrue(optEmpleado.isPresent());
		s1.addEmpleado(optEmpleado.get());
		
		// ---------------------------------------------------
		HttpEntity<Sucursal> requestSucursal1 = new HttpEntity<>(s1);
		// Chequeamos que el retorno sea el correcto
		ResponseEntity<Sucursal> respuesta1 = testRestTemplate.exchange(server, HttpMethod.POST, requestSucursal1,
				Sucursal.class);
		
		//
		s1.setId(respuesta1.getBody().getId());
		
		// Chequeamos que se haya guardado en el repository
		Optional<Sucursal> optRepoS1 = sucursalRepo.findById(s1.getId());
		assertTrue(optRepoS1.isPresent());
		assertNotNull(optRepoS1.get().getEmpleados().get(0));
		assertEquals(1,optRepoS1.get().getEmpleados().size());
		logger.info("[RestAPI]Fin test: guardarSucursales_conEmpleados");
	}

	@Test
	void getSucursal() {
		logger.info("[RestAPI]Inicio test: getSucursal");
		String server = "http://localhost:" + puerto + ENDPOINT_SUCURSAL;
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");
		// -------------------Guardamos la sucursal------------------------------
		// Chequeamos que se guarde
		Sucursal repoSucur1 = sucursalRepo.save(s1);
		assertNotNull(repoSucur1);
		// Re-configuramos la ruta del path
		server += "/" + repoSucur1.getId();

		// Obetenemos la sucursal
		HttpEntity<Sucursal> requestSucursal = new HttpEntity<>(s1);
		ResponseEntity<Sucursal> respuesta = testRestTemplate.exchange(server, HttpMethod.GET, requestSucursal,
				Sucursal.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		assertEquals(repoSucur1.getId(), respuesta.getBody().getId());
		assertEquals(repoSucur1.getCuitEmpresa(), respuesta.getBody().getCuitEmpresa());
		logger.info("[RestAPI]Fin test: getSucursal");
	}

	@Test
	void getAllSucursal() {
		logger.info("[RestAPI]Inicio test: getAllSucursal");
		String server = "http://localhost:" + puerto + ENDPOINT_SUCURSAL;
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");
		// Sucursal 2
		Sucursal s2 = new Sucursal();
		s2.setCiudad("Nogoya");
		s2.setCuitEmpresa("30-289615936");
		s2.setDireccion("San Martin 331");
		// Las guardamos
		Sucursal repoS1 = sucursalRepo.save(s1);
		assertNotNull(repoS1);

		Sucursal repoS2 = sucursalRepo.save(s2);
		assertNotNull(repoS2);
		// Obetemos todas por el rest api
		ResponseEntity<Sucursal[]> respuesta = testRestTemplate.getForEntity(server, Sucursal[].class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		assertTrue(respuesta.getBody().length == 2);
		logger.info("[RestAPI]Fin test: getAllSucursal");

	}

	@Test
	void borrarSucursal() {
		logger.info("[RestAPI]Inicio test: borrarSucursal");
		String server = "http://localhost:" + puerto + ENDPOINT_SUCURSAL;
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");
		// La guardamos
		Sucursal repoS1 = sucursalRepo.save(s1);
		assertNotNull(repoS1);
		// Actualizamos el path
		server += "/" + repoS1.getId();
		// Borramos la sucursal
		ResponseEntity<Sucursal> respuesta = testRestTemplate.exchange(server, HttpMethod.DELETE, null, Sucursal.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));

		// Verificamos si se borro
		Optional<Sucursal> optDebeSerEmpty = sucursalRepo.findById(repoS1.getId());
		assertTrue(optDebeSerEmpty.isEmpty());
		logger.info("[RestAPI]Fin test: borrarSucursal");

	}

	@Test
	void borrarSucursal_Inexistente() {
		logger.info("[RestAPI]Inicio test: borrarSucursal_Inexistente");
		String server = "http://localhost:" + puerto + ENDPOINT_SUCURSAL;
		// Actualizamos el path
		server += "/" + 23665;
		// Borramos la sucursal
		ResponseEntity<Sucursal> respuesta = testRestTemplate.exchange(server, HttpMethod.DELETE, null, Sucursal.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		logger.info("[RestAPI]Fin test: borrarSucursal_Inexistente");

	}

	@Test
	void actualizarSucursal() {
		logger.info("[RestAPI]Inicio test: actualizarSucursal");
		String server = "http://localhost:" + puerto + ENDPOINT_SUCURSAL;
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");
		// La guardamos
		Sucursal repoS1 = sucursalRepo.save(s1);
		assertNotNull(repoS1);
		//Actualizamos valores
		s1 = repoS1;
		server += "/"+s1.getId();
		//Cambioamos datos para actualizar luego
		s1.setCiudad("Nogoya");
		//La actualizamos
		HttpEntity<Sucursal> requestSucursal = new HttpEntity<>(s1);
		ResponseEntity<Sucursal> respuesta = testRestTemplate.exchange(server, HttpMethod.PUT, requestSucursal,
				Sucursal.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		//Chequeamos en el repo los cambios
		Optional<Sucursal> optSucursalActualizada = sucursalRepo.findById(s1.getId());
		assertEquals(s1.getCiudad(), optSucursalActualizada.get().getCiudad());
		logger.info("[RestAPI]Fin test: actualizarSucursal");

	}
	@Test
	void actualizarSucursal_noExistente() {
		logger.info("[RestAPI]Inicio test: actualizarSucursal_noExistente");
		String server = "http://localhost:" + puerto + ENDPOINT_SUCURSAL;
		server += "/"+999514;
		Sucursal s1 = new Sucursal();
		//La actualizamos
		HttpEntity<Sucursal> requestSucursal = new HttpEntity<>(s1);
		ResponseEntity<Sucursal> respuesta = testRestTemplate.exchange(server, HttpMethod.PUT, requestSucursal,
				Sucursal.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		logger.info("[RestAPI]Fin test: actualizarSucursal_noExistente");

	}
	
	@Disabled
	void actualizarSucursal_agregarEmpleado() {
		logger.info("[RestAPI]Inicio test: actualizarSucursal_agregarEmpleado");
		String server = "http://localhost:" + puerto + ENDPOINT_SUCURSAL;
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");
		// La guardamos
		Sucursal repoS1 = sucursalRepo.save(s1);
		assertNotNull(repoS1);
		//Actualizamos valores
		s1 = repoS1;
		//Chequeamos si el empleado existe en la DB
		Optional<Empleado> optEmp = empleadoRepo.findById(1);
		assertTrue(optEmp.isPresent());
		//re configuramos el path
		server += "/"+s1.getId()+"/empleado/"+1; //asociamos la sucursal con el empleado con id=1
	

		ResponseEntity<Sucursal> respuesta = testRestTemplate.exchange(server, HttpMethod.PUT, null,
				Sucursal.class);
		assertTrue(respuesta.getStatusCode().equals(HttpStatus.OK));
		//Chequeamos en el repo los cambios
		Optional<Sucursal> optSucursalActualizada = sucursalRepo.findById(s1.getId());
		assertTrue(optSucursalActualizada.get().getEmpleados().size() > 0);
		logger.info("[RestAPI]Fin test: actualizarSucursal_agregarEmpleado");

	}

}
