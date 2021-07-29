package danms.sueldos.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import danms.sueldos.domain.Empleado;
import danms.sueldos.services.dao.EmpleadoRepository;
import danms.sueldos.services.interfaces.EmpleadoService;


@SpringBootTest
/*Permite pedir a JUnit que cree solo una instancia de la clase de prueba y 
 * la reutilice entre pruebas. */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//
@ActiveProfiles("testing")
class EmpleadoServiceImpTest {
	
	private static final 
	Logger logger = LoggerFactory.getLogger(EmpleadoServiceImpTest.class);
	
	@Autowired
	EmpleadoService empleadoService;

	@Autowired
	EmpleadoRepository empleadoRepo;
	


	@BeforeAll
	void limpiarRepositoriosInicial() {
		logger.info("!!Borrado del repository empleados!!");
		empleadoRepo.deleteAll();
	}
	@AfterAll
	void limpiarRepositoriosFinal() {
		logger.info("!!Borrado del repository empleados!!");
		empleadoRepo.deleteAll();
	}

	@Test
	@Sql("/insert-data-testing1.sql")
	void getEmpleado() {
		logger.info("Inicio test: getEmpleado");
		// Obetenemos la sucursal
		Optional<Empleado> optEmp1 = empleadoService.getEmpleado(1);
		assertTrue(optEmp1.isPresent());
		logger.info("Fin test: getEmpleado");
	}
	
	@Test
	void getEmpleado_IdNula() {
		logger.info("Inicio test: getEmpleado_IdNula");
		Optional<Empleado> optEmp = empleadoService.getEmpleado(null);
		assertTrue(optEmp.isEmpty());
		logger.info("Fin test: getEmpleado_IdNula");
	}
	
	@Test
	void getEmpleado_NoExiste() {
		logger.info("Inicio test: getEmpleado_NoExiste");
		// Sucursal 1
		Empleado e1 = new Empleado();
		e1.setId(4999);
		
		// Obetenemos la sucursal
		Optional<Empleado> optEmp1 = empleadoService.getEmpleado(e1.getId());
		assertTrue(optEmp1.isEmpty());
		logger.info("Fin test: getEmpleado_NoExiste");
	}
	
	@Test
	void getAllEmpleado() {
		logger.info("Inicio test: getAllEmpleado");
		// Obetenemos la sucursales
		List<Empleado> listaEmpleados = empleadoService.getAllEmpleado();
		assertEquals(3, listaEmpleados.size());
		logger.info("Fin test: getAllEmpleado");
	}
	
	

}
