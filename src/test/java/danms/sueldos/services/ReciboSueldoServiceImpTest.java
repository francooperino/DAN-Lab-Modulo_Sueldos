package danms.sueldos.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import danms.sueldos.domain.CodigoDetalle;
import danms.sueldos.services.dao.CodigoDetalleRepository;
import danms.sueldos.services.interfaces.ReciboSueldoService;

@SpringBootTest
@ActiveProfiles("testing")
class ReciboSueldoServiceImpTest {

	@Autowired
	ReciboSueldoService reciboSueldoService;

	@Autowired
	CodigoDetalleRepository codigoDetalleRepo;

	@BeforeEach
	void limpiarRepositorios() {
		codigoDetalleRepo.deleteAll();
	}

	@Test
	// Test para codigoDetalle
	void guardarcodigoDetalle() {
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

		// Chequeamos que el retorno sea el correcto
		Optional<CodigoDetalle> optCd1 = reciboSueldoService.guardarCodigoDetalle(cd1);
		assertTrue(optCd1.isPresent());

		Optional<CodigoDetalle> optCd2 = reciboSueldoService.guardarCodigoDetalle(cd2);
		assertTrue(optCd2.isPresent());

		Optional<CodigoDetalle> optCd3 = reciboSueldoService.guardarCodigoDetalle(cd3);
		assertTrue(optCd3.isPresent());

		// Chequeamos que se haya guardado en el repository
		Optional<CodigoDetalle> optRepoCd1 = codigoDetalleRepo.findById(optCd1.get().getId());
		assertTrue(optRepoCd1.isPresent());

		Optional<CodigoDetalle> optRepoCd2 = codigoDetalleRepo.findById(optCd2.get().getId());
		assertTrue(optRepoCd2.isPresent());

		Optional<CodigoDetalle> optRepoCd3 = codigoDetalleRepo.findById(optCd3.get().getId());
		assertTrue(optRepoCd3.isPresent());

	}

	@Test
	// Test para codigoDetalle
	void actualizarCodigoDetalle() {
		// codigoDetalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");

		// Chequeamos que el retorno sea el correcto
		Optional<CodigoDetalle> optCd1 = reciboSueldoService.guardarCodigoDetalle(cd1);
		assertTrue(optCd1.isPresent());

		// ----Modificamos el codigoDetalle
		cd1 = optCd1.get();
		// Nuevos campos:
		String nuevaDescripcion = "Ahora soy otra descripcion";
		Integer nuevoCodigoDetalle = 150;
		cd1.setDescripcion(nuevaDescripcion);
		cd1.setCodigoDetalle(nuevoCodigoDetalle);

		// ----Actualizamos
		optCd1 = reciboSueldoService.actualizarCodigoDetalle(cd1);

		// ----Comprobamos actualizacion en el retorno
		assertEquals(nuevaDescripcion, optCd1.get().getDescripcion());
		assertEquals(nuevoCodigoDetalle, optCd1.get().getCodigoDetalle());

		// Comprabamos actualizacion en el repositorio
		Optional<CodigoDetalle> optRepoCd1 = codigoDetalleRepo.findById(optCd1.get().getId());
		assertTrue(optRepoCd1.isPresent());
		assertEquals(nuevaDescripcion, optRepoCd1.get().getDescripcion());
		assertEquals(nuevoCodigoDetalle, optRepoCd1.get().getCodigoDetalle());

	}

	@Test
	// Test para codigoDetalle
	void getCodigoDetalle() {
		// codigoDetalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");

		// Chequeamos que el retorno sea el correcto
		Optional<CodigoDetalle> optCd1 = reciboSueldoService.guardarCodigoDetalle(cd1);
		assertTrue(optCd1.isPresent());

		// Obetenemos la sucursal
		Optional<CodigoDetalle> optRepoCs1 = codigoDetalleRepo.findById(optCd1.get().getId());
		assertTrue(optRepoCs1.isPresent());

	}

	@Test
	// Test para codigoDetalle
	void getAllCodigoDetalle() {
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

		// Chequeamos que el retorno sea el correcto
		Optional<CodigoDetalle> optCd1 = reciboSueldoService.guardarCodigoDetalle(cd1);
		assertTrue(optCd1.isPresent());

		Optional<CodigoDetalle> optCd2 = reciboSueldoService.guardarCodigoDetalle(cd2);
		assertTrue(optCd2.isPresent());

		Optional<CodigoDetalle> optCd3 = reciboSueldoService.guardarCodigoDetalle(cd3);
		assertTrue(optCd3.isPresent());

		// Obetenemos la sucursales
		List<CodigoDetalle> listadCodigoDetalles = reciboSueldoService.getAllCodigoDetalle();
		assertEquals(3, listadCodigoDetalles.size());
	}

	@Test
	// Test para codigoDetalle
	void borrarCodigoDetalle() {
		// codigoDetalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setCodigoDetalle(100);
		cd1.setDescripcion("La primer descripcion");

		// Chequeamos que el retorno sea el correcto
		Optional<CodigoDetalle> optCd1 = reciboSueldoService.guardarCodigoDetalle(cd1);
		assertTrue(optCd1.isPresent());

		// Borramos el codigo detalle
		reciboSueldoService.borrarCodigoDetalle(optCd1.get());

		// Obetenemos el codigo detalle
		Optional<CodigoDetalle> optRepoCd1 = reciboSueldoService.getCodigoDetalle(optCd1.get().getId());
		assertTrue(optRepoCd1.isEmpty());

	}
	
	@Test
	// Test para codigoDetalle
	void borrarCodigoDetalleNoExistente() {
		// codigoDetalle 1
		CodigoDetalle cd1 = new CodigoDetalle();
		cd1.setId(99999);
		cd1.setCodigoDetalle(10000);
		cd1.setDescripcion("Descripcion");

		// Borramos la sucursal
		Optional<CodigoDetalle> opcod = reciboSueldoService.borrarCodigoDetalle(cd1);

		// Obetenemos la sucursal
		assertTrue(opcod.isEmpty());

	}

}
