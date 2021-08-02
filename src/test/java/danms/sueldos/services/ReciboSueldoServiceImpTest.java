package danms.sueldos.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import danms.sueldos.domain.CodigoDetalle;
import danms.sueldos.domain.DetalleRecibo;
import danms.sueldos.domain.ReciboSueldo;
import danms.sueldos.services.dao.CodigoDetalleRepository;
import danms.sueldos.services.dao.DetalleReciboRepository;
import danms.sueldos.services.dao.ReciboSueldoRepository;
import danms.sueldos.services.interfaces.ReciboSueldoService;


@ActiveProfiles("testing")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReciboSueldoServiceImpTest {

	@Autowired
	ReciboSueldoService reciboSueldoService;

	@Autowired
	CodigoDetalleRepository codigoDetalleRepo;

	@Autowired
	DetalleReciboRepository detalleReciboRepo;
	
	@Autowired
	ReciboSueldoRepository reciboRepo;
	
	@LocalServerPort
	String puerto;
	

	
	@BeforeEach
	void limpiarRepositorios() {
		reciboRepo.deleteAll();
		detalleReciboRepo.deleteAll();
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

		// Borramos el codigoDetalle
		Optional<CodigoDetalle> opcod = reciboSueldoService.borrarCodigoDetalle(cd1);

		// Obetenemos el codigoDetalle
		assertTrue(opcod.isEmpty());

	}

	/*
	 * @Autowired ReciboSueldoService reciboSueldoService;
	 * 
	 * @Autowired CodigoDetalleRepository codigoDetalleRepo;
	 * 
	 * @Autowired DetalleReciboRepository detalleReciboRepo;
	 */
	@Test
	//Test para Detalle recibo
	void guardarDetalleRecibo() {

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
		// ---------------------------------------------------
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
		
		rs.addDetalleRecibo(dr1);
		rs.addDetalleRecibo(dr2);
		rs.addDetalleRecibo(dr3);
		
		
		
		// Chequeamos que el retorno sea el correcto
		Optional<DetalleRecibo> optDr1 = reciboSueldoService.guardarDetalleRecibo(dr1);
		assertTrue(optDr1.isPresent());

		Optional<DetalleRecibo> optDr2 = reciboSueldoService.guardarDetalleRecibo(dr2);
		assertTrue(optDr2.isPresent());

		Optional<DetalleRecibo> optDr3 = reciboSueldoService.guardarDetalleRecibo(dr3);
		assertTrue(optDr3.isPresent());
		
		rs = reciboRepo.saveAndFlush(rs);
		

		// Chequeamos que se haya guardado en el repository
		Optional<DetalleRecibo> optRepoDr1 = detalleReciboRepo.findById(optDr1.get().getId());
		assertTrue(optRepoDr1.isPresent());

		Optional<DetalleRecibo> optRepoDr2 = detalleReciboRepo.findById(optDr2.get().getId());
		assertTrue(optRepoDr2.isPresent());

		Optional<DetalleRecibo> optRepoDr3 = detalleReciboRepo.findById(optDr3.get().getId());
		assertTrue(optRepoDr3.isPresent());

	}
/*
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
	*/
	
	
}
