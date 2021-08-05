package danms.sueldos.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danms.sueldos.domain.CodigoDetalle;
import danms.sueldos.services.interfaces.ReciboSueldoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/sueldos/recibosueldo")
public class ReciboSueldoRest {
	
	@Autowired
	ReciboSueldoService reciboSueldoService;
	
	/*------------Codigo detalle-------------------*/
	
	@PostMapping(path="/codigodetalle")
	@ApiOperation(value = "Permite crear un codigo detalle")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Guardado correctamente"),
			@ApiResponse(code = 400, message = "No se pudo guardar") })
	public ResponseEntity<CodigoDetalle> crearCodigoDetalle(@RequestBody CodigoDetalle codigoDetalleN) {
		return ResponseEntity.of(reciboSueldoService.guardarCodigoDetalle(codigoDetalleN));
	}
	
	@GetMapping(path = "/codigodetalle/{codigoDetalle}")
	@ApiOperation(value = "Permite obtener un codigo detalle dada su codigo detalle. Aclaracion: codigoDetalle distinto a idDetalle")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "El codigo detalle se encontro"),
			@ApiResponse(code = 405, message = "El id recibida es nula") })
	public ResponseEntity<CodigoDetalle> getCodigoDetalle(@PathVariable Integer codigoDetalle) {
		return ResponseEntity.of(reciboSueldoService.getCodigoDetalle(codigoDetalle));
	}
	
	@GetMapping(path="/codigodetalle")
	@ApiOperation(value = "Permite obtener todos los codigos detalle")
	public ResponseEntity<List<CodigoDetalle>> getAllCodigoDetalle() {
		return ResponseEntity.ok(reciboSueldoService.getAllCodigoDetalle());
	}
	
	@DeleteMapping(path = "/codigodetalle/{codigoDetalle}")
	@ApiOperation(value = "Borrar un codigo detalle. El codigo detalle se debe pasar en el path")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Borrado correctamente"),
			@ApiResponse(code = 400, message = "El codigo detalle no existe, ingrese id valida"),
			@ApiResponse(code = 404, message = "Hubo un error al borrar") })
	public ResponseEntity<CodigoDetalle> borrarSucursal(@PathVariable Integer codigoDetalle) {
		Optional<CodigoDetalle> optCodigoDetalleABorrar = this.reciboSueldoService.getCodigoDetalle(codigoDetalle);
		if (optCodigoDetalleABorrar.isPresent()) {
			Optional<CodigoDetalle> optCodigoDetalleBorrada = this.reciboSueldoService.borrarCodigoDetalle(optCodigoDetalleABorrar.get());
			if (optCodigoDetalleBorrada.isPresent()) {
				// Se borro correctamente
				return ResponseEntity.of(optCodigoDetalleBorrada);
			} else {
				// Error al intentar borrar
				return ResponseEntity.notFound().build();
			}
		} else {
			// El cliente no existe
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping(path = "/codigodetalle/{id}")
	@ApiOperation(value = "Permite actualizar un codigo detalle. El id del codigo detalle debe estar en el path")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualizado correctamente"),
			@ApiResponse(code = 400, message = "No se pudo actualizar") })
	public ResponseEntity<CodigoDetalle> actualizarCodigoDetalle(@RequestBody CodigoDetalle codigoDetalle, @PathVariable Integer id) {
		// Chequeamos que la sucursal exista
		Optional<CodigoDetalle> optCodigoDetalleAActualizar = this.reciboSueldoService.getCodigoDetalle(codigoDetalle.getCodigoDetalle());
		if (optCodigoDetalleAActualizar.isPresent()) {
			//La sucursal existe
			codigoDetalle.setId(id);
			return ResponseEntity.of(reciboSueldoService.actualizarCodigoDetalle(codigoDetalle));
		} 
		else {
			//La sucursal no existe
			return ResponseEntity.badRequest().build();
		}
	}
	
	
	
	
}
