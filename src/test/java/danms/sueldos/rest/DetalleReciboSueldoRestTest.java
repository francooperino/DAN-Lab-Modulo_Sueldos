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
import danms.sueldos.domain.DetalleRecibo;
import danms.sueldos.services.dao.CodigoDetalleRepository;
import danms.sueldos.services.dao.DetalleReciboRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
class DetalleReciboSueldoRestTest {

	private String ENDPOINT_DETALLE_SUELDO = "/api/sueldos/detalleRecibo";

	private static final Logger logger = LoggerFactory.getLogger(SucursalRestTest.class);

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	CodigoDetalleRepository codigoDetalleRepo;

	@Autowired
	DetalleReciboRepository detalleRepo;

	@LocalServerPort
	String puerto;

	@BeforeEach
	void limpiarRepositorios() {
		detalleRepo.deleteAll();
		codigoDetalleRepo.deleteAll();
	}

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
		server += "/" + "detalleRecibo/" + dr1.getId();

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

		// Re-configuramos la ruta del path
		server += "/" + "all";

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
		server += "/" + "/detalleRecibo/" + dr1.getId();

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
