package danms.sueldos.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import danms.sueldos.domain.DatoBancario;
import danms.sueldos.domain.Empleado;
import danms.sueldos.services.interfaces.EmpleadoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/sueldos/empleado")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class EmpleadoRest {

	@Autowired
	EmpleadoService empleadoService;

	// --------------------Empleados

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Permite obtener un empleado dado el id como variable de path.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "El empleado se encontro"),
			@ApiResponse(code = 405, message = "El id recibida es nula") })
	public ResponseEntity<Empleado> getEmpleado(@PathVariable Integer id) {
		return ResponseEntity.of(empleadoService.getEmpleado(id));
	}

	@GetMapping(path="/empleadosNoAsociadosSucursal")
	@ApiOperation(value = "Permite obtener todos los empleados que no estan asociados a nunguna sucursal.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = ""),
			@ApiResponse(code = 405, message = "") })
	public ResponseEntity<List<Empleado>> getEmpleadoNoAsociadoASucursal() {
		return ResponseEntity.ok(empleadoService.getAllEmpleadoNoAsociadoASucursal());
	}
	
	
	@GetMapping
	@ApiOperation(value = "Permite obtener todos los empleados registrados")
	public ResponseEntity<List<Empleado>> getProducto() {
		return ResponseEntity.ok(empleadoService.getAllEmpleado());
	}

	// --------------------Dato bancario

	@PostMapping(path = "/datobancario")
	@ApiOperation(value = "Permite crear un dato bancario debemos pasar en el json el id del empleado al cual pertence el mismo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Guardado correctamente"),
			@ApiResponse(code = 400, message = "El id de empleado asosiado al dato bancario es null o no existe"),
			@ApiResponse(code = 404, message = "No se pudo guardar") })
	public ResponseEntity<DatoBancario> crearDatoBancario(@RequestBody DatoBancario datoBancarioN) {
		// Chequeamos que el empleado sea distinto de null
		if (datoBancarioN.getEmpleado() == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.of(empleadoService.guardarDatoBancario(datoBancarioN));

	}

	@GetMapping(path = "/datobancario")
	@ApiOperation(value = "Permite obtener todos los datos bancarios. Ademas permite obtener para un empleado (dada su id)."
						+ "---Ej de uso:/datobancario o /datobancario?idEmpleado=1")
	public ResponseEntity<List<DatoBancario>> getDatoBancarioPorIdEmpleado(
			@RequestParam(required = false) Integer idEmpleado) {
		if(idEmpleado == null) {
			return ResponseEntity.ok(empleadoService.getAllDatoBancario());
		}
		// Si se ingresa el parametro idEmpleado
		List<DatoBancario> respuesta1 = new ArrayList<>();
		respuesta1.addAll(empleadoService.getDatoBancarioPorIdEmpleado(idEmpleado));
		return ResponseEntity.ok(respuesta1);
	}

	@GetMapping(path = "/datobancario/{idDatoBancario}")
	@ApiOperation(value = "Permite obtener el dato bancario dado su id.")
	public ResponseEntity<DatoBancario> getDatoBancario(@PathVariable Integer idDatoBancario) {
		if (idDatoBancario == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.of(empleadoService.getDatoBancario(idDatoBancario));
	}
	
	@DeleteMapping(path = "/datobancario/{id}")
	@ApiOperation(value = "Borrar un dato bancario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Borrado correctamente"),
			@ApiResponse(code = 400, message = "El dato bancario no existe, ingrese id valida"),
			@ApiResponse(code = 404, message = "Hubo un error al borrar") })
	public ResponseEntity<DatoBancario> borrarDatoBancario(@PathVariable Integer id) {
		Optional<DatoBancario> optDatoBancarioABorrar = this.empleadoService.getDatoBancario(id);
		if (optDatoBancarioABorrar.isPresent()) {
			Optional<DatoBancario> optDatoBancarioBorrado = this.empleadoService.borrarDatoBancario(optDatoBancarioABorrar.get());
			if (optDatoBancarioBorrado.isPresent()) {
				// Se borro correctamente
				return ResponseEntity.of(optDatoBancarioBorrado);
			} else {
				// Error al intentar borrar
				return ResponseEntity.notFound().build();
			}
		} else {
			// El dato bancario no existe
			return ResponseEntity.badRequest().build();
		}
	}
	@PutMapping(path = "/datobancario/{id}")
	@ApiOperation(value = "Permite actualizar un dato bancario. El id debe estar en el path")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualizado correctamente"),
			@ApiResponse(code = 400, message = "No se pudo actualizar") })
	public ResponseEntity<DatoBancario> actualizarSucursal(@RequestBody DatoBancario datoBancario, @PathVariable Integer id) {
		// Chequeamos que el dato bancario exista
		datoBancario.setId(id);
		Optional<DatoBancario> optDatoBancarioAActualizar = this.empleadoService.getDatoBancario(id);
		if (optDatoBancarioAActualizar.isPresent()) {
			//El dato bancario existe
			return ResponseEntity.of(empleadoService.actualizarDatoBancario(datoBancario));
		} 
		else {
			//El dato bancario no existe
			return ResponseEntity.badRequest().build();
		}
	}
}
