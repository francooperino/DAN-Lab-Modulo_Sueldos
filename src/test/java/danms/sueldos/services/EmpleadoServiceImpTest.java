package danms.sueldos.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;


import danms.sueldos.domain.DatoBancario;
import danms.sueldos.domain.Empleado;
import danms.sueldos.services.dao.DatoBancarioRepository;
import danms.sueldos.services.dao.EmpleadoRepository;
import danms.sueldos.services.interfaces.EmpleadoService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
class EmpleadoServiceImpTest {

	private static final Logger logger = LoggerFactory.getLogger(EmpleadoServiceImpTest.class);

	@Autowired
	EmpleadoService empleadoService;

	@Autowired
	EmpleadoRepository empleadoRepo;

	@Autowired
	DatoBancarioRepository datoBancarioRepo;

	@LocalServerPort
	String puerto;

	
	@Test
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

	/* Test de datos bancarios */
	@Test
	void guardar_datoBancario() {
		logger.info("Inicio test: guardar_datoBancario");
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
		Optional<DatoBancario> optDatoBancario1 = empleadoService.guardarDatoBancario(datoBancario1);
		assertTrue(optDatoBancario1.isPresent());
		Optional<DatoBancario> optDatoBancario2 = empleadoService.guardarDatoBancario(datoBancario2);
		assertTrue(optDatoBancario2.isPresent());
		Optional<DatoBancario> optDatoBancario3 = empleadoService.guardarDatoBancario(datoBancario3);
		assertTrue(optDatoBancario3.isPresent());
		// Chequeamos el repositorio
		Optional<DatoBancario> optDatoBancarioRepo1 = datoBancarioRepo.findById(optDatoBancario1.get().getId());
		assertTrue(optDatoBancarioRepo1.isPresent());
		Optional<DatoBancario> optDatoBancarioRepo2 = datoBancarioRepo.findById(optDatoBancario2.get().getId());
		assertTrue(optDatoBancarioRepo2.isPresent());
		Optional<DatoBancario> optDatoBancarioRepo3 = datoBancarioRepo.findById(optDatoBancario3.get().getId());
		assertTrue(optDatoBancarioRepo3.isPresent());

		logger.info("Fin test: guardar_datoBancario");
	}

	@Test
	void actualizar_datoBancario() {
		logger.info("Inicio test: actualizar_datoBancario");
		// EMPLEADOS NECESARIOS:
		Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
		assertTrue(optEmpleado1.isPresent());
		// ---------------------------------------------------
		// DATO BANCARIO 1
		DatoBancario datoBancario1 = new DatoBancario();
		datoBancario1.setNombreBanco("Banco Nacion");
		datoBancario1.setNumeroCuenta("2563432564676215823");
		datoBancario1.setEmpleado(optEmpleado1.get());

		// Chequeamos que el retorno sea el correcto
		Optional<DatoBancario> optDB1 = empleadoService.guardarDatoBancario(datoBancario1);
		assertTrue(optDB1.isPresent());

		// ----Modificamos la sucursal
		datoBancario1 = optDB1.get();
		// Nuevos campos:
		String nuevaNombreBanco = "Banco Sin Plata";
		String nuevaNumeroCuenta = "00000000000000000";
		datoBancario1.setNombreBanco(nuevaNombreBanco);
		datoBancario1.setNumeroCuenta(nuevaNumeroCuenta);

		// ----Actualizacmos
		Optional<DatoBancario> optDBActualizado1 = empleadoService.actualizarDatoBancario(datoBancario1);
		assertTrue(optDBActualizado1.isPresent());
		// ----Comprobamos actualizacion en el retorno
		assertEquals(nuevaNombreBanco, optDBActualizado1.get().getNombreBanco());
		assertEquals(nuevaNumeroCuenta, optDBActualizado1.get().getNumeroCuenta());

		// Comprabamos actualizacion en el repositorio
		Optional<DatoBancario> optRepoDB1 = datoBancarioRepo.findById(optDBActualizado1.get().getId());
		assertTrue(optRepoDB1.isPresent());
		assertEquals(nuevaNombreBanco, optRepoDB1.get().getNombreBanco());
		assertEquals(nuevaNumeroCuenta, optRepoDB1.get().getNumeroCuenta());
		logger.info("Fin test: actualizar_datoBancario");
	}

	@Test
	void getDatoBancario() {
		logger.info("Inicio test: getDatoBancario");
		// EMPLEADOS NECESARIOS:
		Optional<Empleado> optEmpleado = empleadoRepo.findById(1);
		assertTrue(optEmpleado.isPresent());
		// ---------------------------------------------------
		// DATO BANCARIO 1
		DatoBancario datoBancario = new DatoBancario();
		datoBancario.setNombreBanco("Banco de Oro");
		datoBancario.setNumeroCuenta("32344556676215823");
		datoBancario.setEmpleado(optEmpleado.get());

		// Chequeamos que el retorno sea el correcto
		Optional<DatoBancario> optDB = empleadoService.guardarDatoBancario(datoBancario);
		assertTrue(optDB.isPresent());
		// Lo buscamos
		Optional<DatoBancario> optDBBuscado = empleadoService.getDatoBancario(optDB.get().getId());
		assertTrue(optDBBuscado.isPresent());
		logger.info("Fin test: getDatoBancario");
	}

	@Test
	void getAll_DatoBancario() {
		logger.info("Inicio test: getDatoBancario");
		// EMPLEADOS NECESARIOS:
		Optional<Empleado> optEmpleado = empleadoRepo.findById(1);
		assertTrue(optEmpleado.isPresent());
		// ---------------------------------------------------
		// DATO BANCARIO 1
		DatoBancario datoBancario = new DatoBancario();
		datoBancario.setNombreBanco("Banco de Plata");
		datoBancario.setNumeroCuenta("3234455662347623");
		datoBancario.setEmpleado(optEmpleado.get());
		// Chequeamos que el retorno sea el correcto
		Optional<DatoBancario> optDB = empleadoService.guardarDatoBancario(datoBancario);
		assertTrue(optDB.isPresent());

		List<DatoBancario> lista = empleadoService.getAllDatoBancario();
		assertNotNull(lista);
		assertTrue(lista.size() > 0);
	}

	@Test
	void borrarDatoBancario() {
		logger.info("Inicio test: borrarDatoBancario");
		// EMPLEADOS NECESARIOS:
		Optional<Empleado> optEmpleado = empleadoRepo.findById(1);
		assertTrue(optEmpleado.isPresent());
		// ---------------------------------------------------
		// DATO BANCARIO 1
		DatoBancario datoBancario = new DatoBancario();
		datoBancario.setNombreBanco("Banco de Bronce");
		datoBancario.setNumeroCuenta("323435dsd6777623");
		datoBancario.setEmpleado(optEmpleado.get());
		// Chequeamos que el retorno sea el correcto
		Optional<DatoBancario> optDB = empleadoService.guardarDatoBancario(datoBancario);
		assertTrue(optDB.isPresent());

		// Borramos el dato bancario
		empleadoService.borrarDatoBancario(optDB.get());

		// Obetenemos la sucursal
		Optional<DatoBancario> optRepoDB1 = datoBancarioRepo.findById(optDB.get().getId());
		assertTrue(optRepoDB1.isEmpty());
		logger.info("Fin test: borrarDatoBancario");
	}
}
