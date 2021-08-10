package danms.sueldos.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

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

	@Autowired
	SucursalRepository sucursalRepo;

	@Autowired
	EmpleadoRepository empleadoRepo;

	@Autowired
	DatoBancarioRepository datoBancarioRepo;

	@LocalServerPort
	String puerto;

	@BeforeEach
	void limpiarRepositorios() {
		reciboRepo.deleteAll();
		detalleReciboRepo.deleteAll();
		codigoDetalleRepo.deleteAll();
	}

	@Nested
	@DisplayName("Tests codigo Detalle")
	class codigoDetalleTest {
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
	}

	@Nested
	@DisplayName("Tests detalle recibo")
	class detalleReciboTest {
		@Test
		// Test para Detalle recibo
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

			// detalleRecibo 3
			DetalleRecibo dr3 = new DetalleRecibo();
			dr3.setCodigoDetalle(cd3);
			dr3.setHaber(400.0);
			dr3.setDeduccion(null);
			dr3.setPorcentaje(40.0);
			// ---------------------------------------------------
			/*
			 * ReciboSueldo rs = new ReciboSueldo(); rs.setTotalBruto(1500.0);
			 * rs.setTotalNeto(130.0); rs.setLugarDePago("ACA SE PAGA");
			 * rs.setSucursal(null); rs.setPagado(true);
			 * rs.setFechaEmision(Date.valueOf("2021-07-21"));
			 * rs.setFechaDePago(Date.valueOf("2021-07-15")); rs.setDeducciones(15000000.1);
			 * rs.setNumeroRecibo(70);
			 * 
			 * rs.addDetalleRecibo(dr1); rs.addDetalleRecibo(dr2); rs.addDetalleRecibo(dr3);
			 */

			// Chequeamos que el retorno sea el correcto
			Optional<DetalleRecibo> optDr1 = reciboSueldoService.guardarDetalleRecibo(dr1);
			assertTrue(optDr1.isPresent());

			Optional<DetalleRecibo> optDr2 = reciboSueldoService.guardarDetalleRecibo(dr2);
			assertTrue(optDr2.isPresent());

			Optional<DetalleRecibo> optDr3 = reciboSueldoService.guardarDetalleRecibo(dr3);
			assertTrue(optDr3.isPresent());

			// rs = reciboRepo.saveAndFlush(rs);

			// Chequeamos que se haya guardado en el repository
			Optional<DetalleRecibo> optRepoDr1 = detalleReciboRepo.findById(optDr1.get().getId());
			assertTrue(optRepoDr1.isPresent());

			Optional<DetalleRecibo> optRepoDr2 = detalleReciboRepo.findById(optDr2.get().getId());
			assertTrue(optRepoDr2.isPresent());

			Optional<DetalleRecibo> optRepoDr3 = detalleReciboRepo.findById(optDr3.get().getId());
			assertTrue(optRepoDr3.isPresent());

		}

		@Test
		// Test para DetalleRecibo
		void actualizarDetalleRecibo() {
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

			// Chequeamos que el retorno sea el correcto
			Optional<DetalleRecibo> optDr1 = reciboSueldoService.guardarDetalleRecibo(dr1);
			assertTrue(optDr1.isPresent());

			// ----Modificamos el detalleRecibo

			// Nuevos campos:
			Double nuevoHaber = null;
			Double nuevaDeduccion = 300.0;
			Double nuevoPorcentaje = 20.0;

			// Seteamos los nuevos campos
			dr1.setCodigoDetalle(cd2);
			dr1.setHaber(nuevoHaber);
			dr1.setDeduccion(nuevaDeduccion);
			dr1.setPorcentaje(nuevoPorcentaje);

			// ----Actualizamos
			optDr1 = reciboSueldoService.actualizarDetalleRecibo(dr1);

			// ----Comprobamos actualizacion en el retorno
			assertTrue(optDr1.isPresent());
			assertEquals(nuevoHaber, optDr1.get().getHaber());
			assertEquals(nuevaDeduccion, optDr1.get().getDeduccion());
			assertEquals(nuevoPorcentaje, optDr1.get().getPorcentaje());
			assertEquals(cd2.getCodigoDetalle(), optDr1.get().getCodigoDetalle().getCodigoDetalle());

			// Comprabamos actualizacion en el repositorio
			Optional<DetalleRecibo> optRepoDr1 = detalleReciboRepo.findById(optDr1.get().getId());
			assertTrue(optRepoDr1.isPresent());
			assertEquals(nuevoHaber, optRepoDr1.get().getHaber());
			assertEquals(nuevaDeduccion, optRepoDr1.get().getDeduccion());
			assertEquals(nuevoPorcentaje, optRepoDr1.get().getPorcentaje());
			assertEquals(cd2.getCodigoDetalle(), optRepoDr1.get().getCodigoDetalle().getCodigoDetalle());

		}

		@Test
		// Test para DetalleRecibo
		void getDetalleRecibo() {
			// codigoDetalle 1
			CodigoDetalle cd1 = new CodigoDetalle();
			cd1.setCodigoDetalle(100);
			cd1.setDescripcion("La primer descripcion");

			codigoDetalleRepo.save(cd1);

			// detalleRecibo 1
			DetalleRecibo dr1 = new DetalleRecibo();
			dr1.setCodigoDetalle(cd1);
			dr1.setHaber(1500.00);
			dr1.setDeduccion(null);
			dr1.setPorcentaje(10.5);

			// Chequeamos que el retorno sea el correcto
			Optional<DetalleRecibo> optDr1 = reciboSueldoService.guardarDetalleRecibo(dr1);
			assertTrue(optDr1.isPresent());

			// Obetenemos el detalleRecibo
			Optional<DetalleRecibo> optRepoDr1 = detalleReciboRepo.findById(optDr1.get().getId());
			assertTrue(optRepoDr1.isPresent());

		}

		@Test // Test para detalleRecibo
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

			// ---------------------------------------------------

			// Chequeamos que el retorno sea el correcto
			Optional<DetalleRecibo> optDr1 = reciboSueldoService.guardarDetalleRecibo(dr1);
			assertTrue(optDr1.isPresent());

			Optional<DetalleRecibo> optDr2 = reciboSueldoService.guardarDetalleRecibo(dr2);
			assertTrue(optDr2.isPresent());

			Optional<DetalleRecibo> optDr3 = reciboSueldoService.guardarDetalleRecibo(dr3);
			assertTrue(optDr3.isPresent());

			// Obetenemos los detalles Recibo
			List<DetalleRecibo> listaDetalleRecibo = reciboSueldoService.getAllDetalleRecibo();
			assertEquals(3, listaDetalleRecibo.size());
		}

		@Test
		// Test para codigoDetalle
		void borrarDetalleRecibo() {
			// codigoDetalle 1
			CodigoDetalle cd1 = new CodigoDetalle();
			cd1.setCodigoDetalle(100);
			cd1.setDescripcion("La primer descripcion");

			codigoDetalleRepo.save(cd1);

			// detalleRecibo 1
			DetalleRecibo dr1 = new DetalleRecibo();
			dr1.setCodigoDetalle(cd1);
			dr1.setHaber(1500.00);
			dr1.setDeduccion(null);
			dr1.setPorcentaje(10.5);

			// Chequeamos que el retorno sea el correcto
			Optional<DetalleRecibo> optDr1 = reciboSueldoService.guardarDetalleRecibo(dr1);
			assertTrue(optDr1.isPresent());

			// Obetenemos el detalleRecibo
			Optional<DetalleRecibo> optRepoDr1 = detalleReciboRepo.findById(optDr1.get().getId());
			assertTrue(optRepoDr1.isPresent());

			// Borramos el codigo detalle
			reciboSueldoService.borrarDetalleRecibo(dr1);

			// Obetenemos el codigo detalle
			Optional<DetalleRecibo> optD = reciboSueldoService.getDetalleRecibo(optRepoDr1.get().getId());
			assertTrue(optD.isEmpty());
		}

		@Test
		// Test para DetalleRecibo
		void borrarDetalleReciboNoExistente() {
			// codigoDetalle 1
			CodigoDetalle cd1 = new CodigoDetalle();
			cd1.setCodigoDetalle(100);
			cd1.setDescripcion("La primer descripcion");

			codigoDetalleRepo.save(cd1);
			// detalleRecibo 1
			DetalleRecibo dr1 = new DetalleRecibo();
			dr1.setId(9999999);
			dr1.setCodigoDetalle(cd1);
			dr1.setHaber(1500.00);
			dr1.setDeduccion(null);
			dr1.setPorcentaje(10.5);

			// Borramos el codigo detalle
			Optional<DetalleRecibo> opDr1 = reciboSueldoService.borrarDetalleRecibo(dr1);

			// Obetenemos el codigoDetalle
			assertTrue(opDr1.isEmpty());

		}
	}

	@Nested
	@DisplayName("Tests recibo")
	class reciboTest {

		@Test
		// Test para Recibo de sueldo
		void guardarReciboSueldo() {
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

			// detalleRecibo 3
			DetalleRecibo dr3 = new DetalleRecibo();
			dr3.setCodigoDetalle(cd3);
			dr3.setHaber(400.0);
			dr3.setDeduccion(null);
			dr3.setPorcentaje(40.0);

			// Sucursal 1
			Sucursal s1 = new Sucursal();
			s1.setCiudad("Lucas Gonzalez");
			s1.setCuitEmpresa("30-289615936");
			s1.setDireccion("Hernandez 321");

			sucursalRepo.saveAndFlush(s1);

			Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
			assertTrue(optEmpleado1.isPresent());

			// DATO BANCARIO 1
			DatoBancario datoBancario1 = new DatoBancario();
			datoBancario1.setNombreBanco("Banco Entre Rios");
			datoBancario1.setNumeroCuenta("256335889956215823");
			datoBancario1.setEmpleado(optEmpleado1.get());

			datoBancarioRepo.saveAndFlush(datoBancario1);

			ReciboSueldo rs = new ReciboSueldo();
			rs.setTotalBruto(1500.0);
			rs.setTotalNeto(130.0);
			rs.setLugarDePago("ACA SE PAGA");
			rs.setSucursal(s1);
			rs.setPagado(true);
			rs.setFechaEmision(Date.valueOf("2021-07-21"));
			rs.setFechaDePago(Date.valueOf("2021-07-15"));
			rs.setDeducciones(15000.1);
			rs.setNumeroRecibo(70);
			rs.setEmpleado(optEmpleado1.get());

			rs.addDetalleRecibo(dr1);
			rs.addDetalleRecibo(dr2);
			rs.addDetalleRecibo(dr3);

			// guardamos los detalle recibo

			detalleReciboRepo.saveAndFlush(dr1);
			detalleReciboRepo.saveAndFlush(dr2);
			detalleReciboRepo.saveAndFlush(dr3);

			// Guardamos el recibo de sueldo

			Optional<ReciboSueldo> reciboOpt = reciboSueldoService.guardarReciboSueldo(rs);

			assertTrue(reciboOpt.isPresent());

		}

		@Test
		void actualizarReciboSueldo() {

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

			sucursalRepo.saveAndFlush(s1);
			sucursalRepo.saveAndFlush(s2);

			// Empleados
			Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
			assertTrue(optEmpleado1.isPresent());

			Optional<Empleado> optEmpleado2 = empleadoRepo.findById(2);
			assertTrue(optEmpleado2.isPresent());

			// DATO BANCARIO 1
			DatoBancario datoBancario1 = new DatoBancario();
			datoBancario1.setNombreBanco("Banco Entre Rios");
			datoBancario1.setNumeroCuenta("256335889956215823");
			datoBancario1.setEmpleado(optEmpleado1.get());

			datoBancarioRepo.saveAndFlush(datoBancario1);

			// DATO BANCARIO 2
			DatoBancario datoBancario2 = new DatoBancario();
			datoBancario2.setNombreBanco("Banco Santader");
			datoBancario2.setNumeroCuenta("25435567889903");
			datoBancario2.setEmpleado(optEmpleado2.get());

			datoBancarioRepo.saveAndFlush(datoBancario2);

			ReciboSueldo rs = new ReciboSueldo();
			rs.setTotalBruto(1500.0);
			rs.setTotalNeto(130.0);
			rs.setLugarDePago("ACA SE PAGA");
			rs.setSucursal(s1);
			rs.setPagado(true);
			rs.setFechaEmision(Date.valueOf("2021-07-21"));
			rs.setFechaDePago(Date.valueOf("2021-07-15"));
			rs.setDeducciones(15000.1);
			rs.setNumeroRecibo(70);
			rs.setEmpleado(optEmpleado1.get());

			rs.addDetalleRecibo(dr1);

			// Chequeamos que el retorno sea el correcto

			detalleReciboRepo.saveAndFlush(dr1);

			// Guardamos el recibo de sueldo

			Optional<ReciboSueldo> reciboOptOld = reciboSueldoService.guardarReciboSueldo(rs);

			assertTrue(reciboOptOld.isPresent());

			// Nuevos campos:
			Double nuevoTotalBruto = 0.0;
			Double nuevoTotalNeto = 140.0;
			String nuevoLugarDePago = "Se cobra aca";
			Boolean nuevoPagado = false;
			Date nuevoFechaEmision = Date.valueOf("2021-06-08");
			Date nuevoFechaDePago = Date.valueOf("2021-06-17");
			Double nuevoDeducciones = 8000.0;
			int nuevoNumeroRecibo = 80;

			rs.setTotalBruto(nuevoTotalBruto);
			rs.setTotalNeto(nuevoTotalNeto);
			rs.setLugarDePago(nuevoLugarDePago);
			rs.setPagado(nuevoPagado);
			rs.setFechaEmision(nuevoFechaEmision);
			rs.setFechaDePago(nuevoFechaDePago);
			rs.setDeducciones(nuevoDeducciones);
			rs.setNumeroRecibo(nuevoNumeroRecibo);
			rs.setEmpleado(optEmpleado2.get());
			rs.setSucursal(s2);

			// ----Actualizamos
			Optional<ReciboSueldo> optDr1New = reciboSueldoService.actualizarReciboSueldo(rs);

			// ----Comprobamos actualizacion en el retorno
			assertTrue(optDr1New.isPresent());
			assertEquals(nuevoTotalBruto, optDr1New.get().getTotalBruto());
			assertEquals(nuevoTotalNeto, optDr1New.get().getTotalNeto());
			assertEquals(nuevoLugarDePago, optDr1New.get().getLugarDePago());
			assertEquals(nuevoPagado, optDr1New.get().getPagado());
			assertEquals(nuevoFechaEmision, optDr1New.get().getFechaEmision());
			assertEquals(nuevoFechaDePago, optDr1New.get().getFechaDePago());
			assertEquals(nuevoDeducciones, optDr1New.get().getDeducciones());
			assertEquals(nuevoNumeroRecibo, optDr1New.get().getNumeroRecibo());
			assertEquals(optEmpleado2.get().getId(), optDr1New.get().getEmpleado().getId());
			assertEquals(s2.getId(), optDr1New.get().getSucursal().getId());

			// Comprabamos actualizacion en el repositorio
			Optional<ReciboSueldo> optRepoR1 = reciboRepo.findById(optDr1New.get().getId());
			assertTrue(optRepoR1.isPresent());
			assertEquals(nuevoTotalBruto, optRepoR1.get().getTotalBruto());
			assertEquals(nuevoTotalNeto, optRepoR1.get().getTotalNeto());
			assertEquals(nuevoLugarDePago, optRepoR1.get().getLugarDePago());
			assertEquals(nuevoPagado, optRepoR1.get().getPagado());
			assertEquals(nuevoFechaEmision, optRepoR1.get().getFechaEmision());
			assertEquals(nuevoFechaDePago, optRepoR1.get().getFechaDePago());
			assertEquals(nuevoDeducciones, optRepoR1.get().getDeducciones());
			assertEquals(nuevoNumeroRecibo, optRepoR1.get().getNumeroRecibo());
			assertEquals(optEmpleado2.get().getId(), optRepoR1.get().getEmpleado().getId());
			assertEquals(s2.getId(), optRepoR1.get().getSucursal().getId());

		}

		@Test
		// Test para DetalleRecibo
		void getReciboSueldo() {
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

			// detalleRecibo 3
			DetalleRecibo dr3 = new DetalleRecibo();
			dr3.setCodigoDetalle(cd3);
			dr3.setHaber(400.0);
			dr3.setDeduccion(null);
			dr3.setPorcentaje(40.0);

			// Sucursal 1
			Sucursal s1 = new Sucursal();
			s1.setCiudad("Lucas Gonzalez");
			s1.setCuitEmpresa("30-289615936");
			s1.setDireccion("Hernandez 321");

			sucursalRepo.saveAndFlush(s1);

			Optional<Empleado> optEmpleado1 = empleadoRepo.findById(1);
			assertTrue(optEmpleado1.isPresent());

			// DATO BANCARIO 1
			DatoBancario datoBancario1 = new DatoBancario();
			datoBancario1.setNombreBanco("Banco Entre Rios");
			datoBancario1.setNumeroCuenta("256335889956215823");
			datoBancario1.setEmpleado(optEmpleado1.get());

			datoBancarioRepo.saveAndFlush(datoBancario1);

			ReciboSueldo rs = new ReciboSueldo();
			rs.setTotalBruto(1500.0);
			rs.setTotalNeto(130.0);
			rs.setLugarDePago("ACA SE PAGA");
			rs.setSucursal(s1);
			rs.setPagado(true);
			rs.setFechaEmision(Date.valueOf("2021-07-21"));
			rs.setFechaDePago(Date.valueOf("2021-07-15"));
			rs.setDeducciones(15000.1);
			rs.setNumeroRecibo(70);
			rs.setEmpleado(optEmpleado1.get());

			rs.addDetalleRecibo(dr1);
			rs.addDetalleRecibo(dr2);
			rs.addDetalleRecibo(dr3);

			// Chequeamos que el retorno sea el correcto

			detalleReciboRepo.saveAndFlush(dr1);
			detalleReciboRepo.saveAndFlush(dr2);
			detalleReciboRepo.saveAndFlush(dr3);

			// Guardamos el recibo de sueldo

			reciboRepo.saveAndFlush(rs);

			// Obetenemos el recibo de sueldo
			Optional<ReciboSueldo> optRepoRs1 = reciboSueldoService.getReciboSueldo(rs.getId());
			assertTrue(optRepoRs1.isPresent());

		}

		@Test
		void getAllReciboSueldo() {
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

			// detalleRecibo 3
			DetalleRecibo dr3 = new DetalleRecibo();
			dr3.setCodigoDetalle(cd3);
			dr3.setHaber(400.0);
			dr3.setDeduccion(null);
			dr3.setPorcentaje(40.0);

			// Sucursal 1
			Sucursal s1 = new Sucursal();
			s1.setCiudad("Lucas Gonzalez");
			s1.setCuitEmpresa("30-289615936");
			s1.setDireccion("Hernandez 321");

			sucursalRepo.saveAndFlush(s1);

			Optional<Empleado> optEmpleado1 = empleadoRepo.findById(2);
			assertTrue(optEmpleado1.isPresent());

			// DATO BANCARIO 1
			DatoBancario datoBancario1 = new DatoBancario();
			datoBancario1.setNombreBanco("Banco Entre Rios");
			datoBancario1.setNumeroCuenta("256335889956215823");
			datoBancario1.setEmpleado(optEmpleado1.get());

			datoBancarioRepo.saveAndFlush(datoBancario1);

			ReciboSueldo rs = new ReciboSueldo();
			rs.setTotalBruto(1500.0);
			rs.setTotalNeto(130.0);
			rs.setLugarDePago("ACA SE PAGA");
			rs.setSucursal(s1);
			rs.setPagado(true);
			rs.setFechaEmision(Date.valueOf("2021-07-21"));
			rs.setFechaDePago(Date.valueOf("2021-07-15"));
			rs.setDeducciones(15000.1);
			rs.setNumeroRecibo(70);
			rs.setEmpleado(optEmpleado1.get());
			rs.addDetalleRecibo(dr1);
			rs.addDetalleRecibo(dr2);

			ReciboSueldo rs2 = new ReciboSueldo();
			rs2.setTotalBruto(150.0);
			rs2.setTotalNeto(100.0);
			rs2.setLugarDePago("Lugar de pago2");
			rs2.setSucursal(s1);
			rs2.setPagado(false);
			rs2.setFechaEmision(Date.valueOf("2020-07-21"));
			rs2.setFechaDePago(Date.valueOf("2020-07-15"));
			rs2.setDeducciones(3000.1);
			rs2.setNumeroRecibo(85);
			rs2.setEmpleado(optEmpleado1.get());

			rs2.addDetalleRecibo(dr3);

			// Chequeamos que el retorno sea el correcto

			detalleReciboRepo.saveAndFlush(dr1);
			detalleReciboRepo.saveAndFlush(dr2);
			detalleReciboRepo.saveAndFlush(dr3);

			reciboRepo.saveAndFlush(rs);
			reciboRepo.saveAndFlush(rs2);

			// Obetenemos los recibo de sueldo
			List<ReciboSueldo> listaRecibo = reciboSueldoService.getAllReciboSueldo();
			assertEquals(2, listaRecibo.size());
		}

		@Test
		// Test para Recibo
		void borrarRecibo() {

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

			// detalleRecibo 3
			DetalleRecibo dr3 = new DetalleRecibo();
			dr3.setCodigoDetalle(cd3);
			dr3.setHaber(400.0);
			dr3.setDeduccion(null);
			dr3.setPorcentaje(40.0);

			// Sucursal 1
			Sucursal s1 = new Sucursal();
			s1.setCiudad("Lucas Gonzalez");
			s1.setCuitEmpresa("30-289615936");
			s1.setDireccion("Hernandez 321");

			sucursalRepo.saveAndFlush(s1);

			Optional<Empleado> optEmpleado1 = empleadoRepo.findById(3);
			assertTrue(optEmpleado1.isPresent());

			// DATO BANCARIO 1
			DatoBancario datoBancario1 = new DatoBancario();
			datoBancario1.setNombreBanco("Banco Entre Rios");
			datoBancario1.setNumeroCuenta("256335889956215823");
			datoBancario1.setEmpleado(optEmpleado1.get());

			datoBancarioRepo.saveAndFlush(datoBancario1);

			ReciboSueldo rs = new ReciboSueldo();
			rs.setTotalBruto(1500.0);
			rs.setTotalNeto(130.0);
			rs.setLugarDePago("ACA SE PAGA");
			rs.setSucursal(s1);
			rs.setPagado(true);
			rs.setFechaEmision(Date.valueOf("2021-07-21"));
			rs.setFechaDePago(Date.valueOf("2021-07-15"));
			rs.setDeducciones(15000.1);
			rs.setNumeroRecibo(70);
			rs.setEmpleado(optEmpleado1.get());

			rs.addDetalleRecibo(dr1);
			rs.addDetalleRecibo(dr2);
			rs.addDetalleRecibo(dr3);

			// guardamos los detalle recibo

			detalleReciboRepo.saveAndFlush(dr1);
			detalleReciboRepo.saveAndFlush(dr2);
			detalleReciboRepo.saveAndFlush(dr3);

			// Guardamos el recibo de sueldo

			reciboRepo.saveAndFlush(rs);

			// Borramos el codigo detalle
			Optional<ReciboSueldo> opRs1 = reciboSueldoService.borrarReciboSueldo(rs);

			assertTrue(opRs1.isPresent());

			assertTrue(reciboRepo.findById(opRs1.get().getId()).isEmpty());

		}

		@Test
		// Test para Recibo
		void borrarReciboNoExistente() {

			ReciboSueldo rs = new ReciboSueldo();
			rs.setId(999999);

			// Borramos el codigo detalle
			Optional<ReciboSueldo> opRs1 = reciboSueldoService.borrarReciboSueldo(rs);

			assertTrue(opRs1.isEmpty());
		}

		@Test
		void generarRecibosSueldo() {
			Sucursal s1 = new Sucursal();
			// Busco empleados
			Optional<Empleado> optEmp1 = empleadoRepo.findById(1);
			Optional<Empleado> optEmp2 = empleadoRepo.findById(2);
			Optional<Empleado> optEmp3 = empleadoRepo.findById(3);
			assertTrue(optEmp1.isPresent());
			assertTrue(optEmp2.isPresent());
			assertTrue(optEmp3.isPresent());
			// Agregamos los empleados
			s1.addEmpleado(optEmp1.get());
			s1.addEmpleado(optEmp2.get());
			s1.addEmpleado(optEmp3.get());
			s1 = sucursalRepo.save(s1);
			assertNotNull(s1);
			// Creamos codigos detalles
			CodigoDetalle cod1 = new CodigoDetalle();
			CodigoDetalle cod2 = new CodigoDetalle();
			CodigoDetalle cod3 = new CodigoDetalle();
			cod1.setHaber(500000.00);
			cod2.setPorcentaje(2.50);
			cod3.setPorcentaje(7.50);
			codigoDetalleRepo.save(cod1);
			codigoDetalleRepo.save(cod2);
			codigoDetalleRepo.save(cod3);
			assertNotNull(cod1);
			assertNotNull(cod2);
			assertNotNull(cod3);
			// Creamos datos bancarios
			DatoBancario datoBancario1 = new DatoBancario();
			DatoBancario datoBancario2 = new DatoBancario();
			DatoBancario datoBancario3 = new DatoBancario();
			datoBancario1.setEmpleado(optEmp1.get());
			datoBancario2.setEmpleado(optEmp2.get());
			datoBancario3.setEmpleado(optEmp3.get());
			datoBancario1 = datoBancarioRepo.save(datoBancario1);
			assertNotNull(datoBancario1);
			datoBancario2 = datoBancarioRepo.save(datoBancario2);
			assertNotNull(datoBancario2);
			datoBancario3 = datoBancarioRepo.save(datoBancario3);
			assertNotNull(datoBancario3);
			//
			reciboSueldoService.generarRecibosSueldos();
			// Buscamos el recibo en la DB
			List<ReciboSueldo> listaRecibo = reciboRepo.findAll();
			assertEquals(3, listaRecibo.size());
			assertEquals(50000.00, listaRecibo.get(0).getDeducciones());
			assertEquals(450000.00, listaRecibo.get(0).getTotalNeto());

		}

	}

}
