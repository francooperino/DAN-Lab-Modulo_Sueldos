package danms.sueldos.rest;

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
import org.springframework.web.bind.annotation.RestController;

import danms.sueldos.domain.Sucursal;
import danms.sueldos.services.interfaces.SucursalService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/sueldos/sucursal")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class SucursalRest {

	@Autowired
	SucursalService sucursalService;

	@PostMapping
	@ApiOperation(value = "Permite crear una sucursal")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Guardado correctamente"),
			@ApiResponse(code = 400, message = "No se pudo guardar") })
	public ResponseEntity<Sucursal> crearSucursal(@RequestBody Sucursal sucursalN) {
		return ResponseEntity.of(sucursalService.guardarSucursal(sucursalN));
	}

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Permite obtener una sucursal dada la id como variable de path.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "La sucursal se encontro"),
			@ApiResponse(code = 405, message = "El id recibida es nula") })
	public ResponseEntity<Sucursal> getSucursal(@PathVariable Integer id) {
		return ResponseEntity.of(sucursalService.getSucursal(id));
	}

	@GetMapping
	@ApiOperation(value = "Permite obtener todos las sucursales")
	public ResponseEntity<List<Sucursal>> getAllSucursal() {
		return ResponseEntity.ok(sucursalService.getAllSucursal());
	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Borrar una sucursal")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Borrado correctamente"),
			@ApiResponse(code = 400, message = "La sucursal no existe, ingrese id valida"),
			@ApiResponse(code = 404, message = "Hubo un error al borrar") })
	public ResponseEntity<Sucursal> borrarSucursal(@PathVariable Integer id) {
		Optional<Sucursal> optSucursalABorrar = this.sucursalService.getSucursal(id);
		if (optSucursalABorrar.isPresent()) {
			Optional<Sucursal> optSucursalBorrada = this.sucursalService.borrarSucursal(optSucursalABorrar.get());
			if (optSucursalBorrada.isPresent()) {
				// Se borro correctamente
				return ResponseEntity.of(optSucursalBorrada);
			} else {
				// Error al intentar borrar
				return ResponseEntity.notFound().build();
			}
		} else {
			// El cliente no existe
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Permite actualizar una sucursal. El id debe estar en el path")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualizado correctamente"),
			@ApiResponse(code = 400, message = "No se pudo actualizar") })
	public ResponseEntity<Sucursal> actualizarSucursal(@RequestBody Sucursal sucursal, @PathVariable Integer id) {
		// Chequeamos que la sucursal exista
		Optional<Sucursal> optSucursalAActualizar = this.sucursalService.getSucursal(id);
		if (optSucursalAActualizar.isPresent()) {
			//La sucursal existe
			sucursal.setId(id);
			return ResponseEntity.of(sucursalService.actualizarSucursal(sucursal));
		} 
		else {
			//La sucursal no existe
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping(path = "{idSucursal}/empleado/{idEmpleado}")
	@ApiOperation(value = "Permite agregar un empleado a una sucursal")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Agregado correctamente"),
			@ApiResponse(code = 400, message = "No se pudo agregar") })
	public ResponseEntity<Sucursal> addEmpleadoASucursal(@PathVariable Integer idSucursal,@PathVariable Integer idEmpleado) {
		// Chequeamos que la sucursal exista
		Optional<Sucursal> optSucursalAActualizar = this.sucursalService.getSucursal(idSucursal);
		if (optSucursalAActualizar.isPresent()) {
			//La sucursal existe
			return ResponseEntity.of(sucursalService.addEmpleado(idSucursal, idEmpleado));
		} 
		else {
			//La sucursal no existe
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping(path = "{idSucursal}/empleado/{idEmpleado}")
	@ApiOperation(value = "Permite borrar un empleado de una sucursal")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Borrado correctamente"),
			@ApiResponse(code = 400, message = "No se pudo borrar") })
	public ResponseEntity<Sucursal> removeEmpleadoASucursal(@PathVariable Integer idSucursal,@PathVariable Integer idEmpleado) {
		// Chequeamos que la sucursal exista
		Optional<Sucursal> optSucursalAActualizar = this.sucursalService.getSucursal(idSucursal);
		if (optSucursalAActualizar.isPresent()) {
			//La sucursal existe
			return ResponseEntity.of(sucursalService.removeEmpleado(idSucursal, idEmpleado));
		} 
		else {
			//La sucursal no existe
			return ResponseEntity.badRequest().build();
		}
	}
	

}
