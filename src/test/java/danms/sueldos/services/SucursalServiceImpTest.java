package danms.sueldos.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import danms.sueldos.domain.Sucursal;
import danms.sueldos.services.dao.SucursalRepository;
import danms.sueldos.services.interfaces.SucursalService;

@SpringBootTest
@ActiveProfiles("testing")
class SucursalServiceImpTest {

	private static final 
	Logger logger = LoggerFactory.getLogger(SucursalServiceImpTest.class);
	
	@Autowired
	SucursalService sucursalService;

	@Autowired
	SucursalRepository sucursalRepo;

	@BeforeEach
	void limpiarRepositorios() {
		logger.info("Borrar todo los repositorios");
		sucursalRepo.deleteAll();
	}

	@Test
	void guardarSucursales() {
		logger.info("Inicio test: guardarSucursales");
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

		// Chequeamos que el retorno sea el correcto
		Optional<Sucursal> optS1 = sucursalService.guardarSucursal(s1);
		assertTrue(optS1.isPresent());

		Optional<Sucursal> optS2 = sucursalService.guardarSucursal(s2);
		assertTrue(optS2.isPresent());

		Optional<Sucursal> optS3 = sucursalService.guardarSucursal(s3);
		assertTrue(optS3.isPresent());

		// Chequeamos que se haya guardado en el repository
		Optional<Sucursal> optRepoS1 = sucursalRepo.findById(optS1.get().getId());
		assertTrue(optRepoS1.isPresent());

		Optional<Sucursal> optRepoS2 = sucursalRepo.findById(optS2.get().getId());
		assertTrue(optRepoS2.isPresent());

		Optional<Sucursal> optRepoS3 = sucursalRepo.findById(optS3.get().getId());
		assertTrue(optRepoS3.isPresent());
		logger.info("Fin test: guardarSucursales");
	}

	@Test
	void actualizarSolicitud() {
		logger.info("Inicio test: actualizarSolicitud");
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");

		// Chequeamos que el retorno sea el correcto
		Optional<Sucursal> optS1 = sucursalService.guardarSucursal(s1);
		assertTrue(optS1.isPresent());

		// ----Modificamos la sucursal
		s1 = optS1.get();
		// Nuevos campos:
		String nuevaCiudad = "xx de Septiembre";
		String nuevaDireccion = "9 de Julio 1880";
		String nuevaCuitEmpresa = "20-48000000";
		s1.setCiudad(nuevaCiudad);
		s1.setCuitEmpresa(nuevaCuitEmpresa);
		s1.setDireccion(nuevaDireccion);

		// ----Actualizacmos
		optS1 = sucursalService.actualizarSucursal(s1);

		// ----Comprobamos actualizacion en el retorno
		assertEquals(nuevaCiudad, optS1.get().getCiudad());
		assertEquals(nuevaDireccion, optS1.get().getDireccion());
		assertEquals(nuevaCuitEmpresa, optS1.get().getCuitEmpresa());

		// Comprabamos actualizacion en el repositorio
		Optional<Sucursal> optRepoS1 = sucursalRepo.findById(optS1.get().getId());
		assertTrue(optRepoS1.isPresent());
		assertEquals(nuevaCiudad, optRepoS1.get().getCiudad());
		assertEquals(nuevaDireccion, optRepoS1.get().getDireccion());
		assertEquals(nuevaCuitEmpresa, optRepoS1.get().getCuitEmpresa());
		logger.info("Fin test: actualizarSolicitud");
	}

	@Test
	void actualizarSucursal_IdNull() {
		logger.info("Inicio test: actualizarSucursal_IdNull");
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");

		// Chequeamos que el retorno sea el correcto
		Optional<Sucursal> optS1 = sucursalService.guardarSucursal(s1);
		assertTrue(optS1.isPresent());
		// ----Modificamos la sucursal
		s1 = optS1.get();
		s1.setId(null); // seteamos en null
		// ----Actualizacmos
		optS1 = sucursalService.actualizarSucursal(s1);
		assertTrue(optS1.isEmpty());
		logger.info("Fin test: actualizarSucursal_IdNull");
	}

	@Test
	void actualizarSucursal_NoExistente() {
		logger.info("Inicio test: actualizarSucursal_NoExistente");
		Sucursal s1 = new Sucursal();
		s1.setId(444);
		//Nunca guardamos la solicitud
		
		// ----Modificamos la sucursal
		s1.setDireccion("Assault 123");
		// ----Actualizacmos
		Optional<Sucursal> optS1 = sucursalService.actualizarSucursal(s1);
		assertTrue(optS1.isEmpty());
		logger.info("Fin test: actualizarSucursal_NoExistente");
	}

	@Test
	void getSucursal() {
		logger.info("Inicio test: getSucursal");
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");

		// Chequeamos que el retorno sea el correcto
		Optional<Sucursal> optS1 = sucursalService.guardarSucursal(s1);
		assertTrue(optS1.isPresent());

		// Obetenemos la sucursal
		Optional<Sucursal> optRepoS1 = sucursalService.getSucursal(optS1.get().getId());
		assertTrue(optRepoS1.isPresent());
		logger.info("Fin test: getSucursal");
	}
	@Test
	void getSucursal_IdNula() {
		logger.info("Inicio test: getSucursal_IdNula");
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");
		s1.setId(null); // Id nula

		// Obetenemos la sucursal
		Optional<Sucursal> optRepoS1 = sucursalService.getSucursal(s1.getId());
		assertTrue(optRepoS1.isEmpty());
		logger.info("Fin test: getSucursal_IdNula");
	}
	@Test
	void getSucursal_NoExiste() {
		logger.info("Inicio test: getSucursal_NoExiste");
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setId(4999);
		
		// Obetenemos la sucursal
		Optional<Sucursal> optRepoS1 = sucursalService.getSucursal(s1.getId());
		assertTrue(optRepoS1.isEmpty());
		logger.info("Fin test: getSucursal_NoExiste");
	}

	@Test
	void getAllSucursal() {
		logger.info("Inicio test: getAllSucursal");
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

		// Chequeamos que el retorno sea el correcto
		Optional<Sucursal> optS1 = sucursalService.guardarSucursal(s1);
		assertTrue(optS1.isPresent());

		Optional<Sucursal> optS2 = sucursalService.guardarSucursal(s2);
		assertTrue(optS2.isPresent());

		Optional<Sucursal> optS3 = sucursalService.guardarSucursal(s3);
		assertTrue(optS3.isPresent());

		// Obetenemos la sucursales
		List<Sucursal> listaSucursales = sucursalService.getAllSucursal();
		assertEquals(3, listaSucursales.size());
		logger.info("Fin test: getAllSucursal");
	}

	@Test
	void borrarSucursal() {
		logger.info("Inicio test: borrarSucursal");
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");

		// Chequeamos que el retorno sea el correcto
		Optional<Sucursal> optS1 = sucursalService.guardarSucursal(s1);
		assertTrue(optS1.isPresent());

		// Borramos la sucursal
		sucursalService.borrarSucursal(optS1.get());

		// Obetenemos la sucursal
		Optional<Sucursal> optRepoS1 = sucursalRepo.findById(optS1.get().getId());
		assertTrue(optRepoS1.isEmpty());
		logger.info("Fin test: borrarSucursal");
	}

	@Test
	void borrarSucursal_NoExiste() {
		logger.info("Inicio test: borrarSucursal_NoExiste");
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setCiudad("Lucas Gonzalez");
		s1.setCuitEmpresa("30-289615936");
		s1.setDireccion("Hernandez 321");

		// Chequeamos que el retorno sea el correcto
		Optional<Sucursal> optS1 = sucursalService.guardarSucursal(s1);
		assertTrue(optS1.isPresent());

		// Borramos la sucursal
		optS1.get().setId(4500);
		Optional<Sucursal> optSucursalBorrada = sucursalService.borrarSucursal(optS1.get());
		assertTrue(optSucursalBorrada.isEmpty());
		logger.info("Fin test: borrarSucursal_NoExiste");
	}
	@Test
	void borrarSucursal_IDNull() {
		logger.info("Inicio test: borrarSucursal_IDNull");
		// Sucursal 1
		Sucursal s1 = new Sucursal();
		s1.setId(null);
		Optional<Sucursal> optSucursalBorrada = sucursalService.borrarSucursal(s1);
		assertTrue(optSucursalBorrada.isEmpty());
		logger.info("Fin test: borrarSucursal_IDNull");
	}

}
