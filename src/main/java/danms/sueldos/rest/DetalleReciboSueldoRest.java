package danms.sueldos.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danms.sueldos.domain.DetalleRecibo;
import danms.sueldos.services.interfaces.ReciboSueldoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/sueldos/detalleRecibo")
public class DetalleReciboSueldoRest {

	@Autowired
	ReciboSueldoService reciboSueldoService;

	/*------------Detalle Recibo-------------------*/

	@GetMapping(path = "/detalleRecibo/{idDetalle}")
	@ApiOperation(value = "Permite obtener un detalleRecibo dado su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "El detalle recibo se encontro"),
	@ApiResponse(code = 405, message = "El id recibida es nula") })
	
	//TODO: fijarse el codigo de retorno
	public ResponseEntity<DetalleRecibo> getCodigoDetalle(@PathVariable Integer idDetalle) {
		
		if(idDetalle==null) {
			return ResponseEntity.badRequest().build();
		}
		
		
		return ResponseEntity.of(reciboSueldoService.getDetalleRecibo(idDetalle));
	}

	
	@GetMapping(path = "/all")
	@ApiOperation(value = "Permite obtener todos los detalleRecibo")
	@ApiResponses(value = @ApiResponse(code = 200, message = "Se encontraron detalles recibo"))
	public ResponseEntity<List<DetalleRecibo>> getAllDetalleRecibo() {
		return ResponseEntity.ok(reciboSueldoService.getAllDetalleRecibo());
	}
	@PutMapping(path = "/detalleRecibo/{idDetalle}")
	@ApiOperation(value = "Permite actualizar un detalleRecibo. El id del detalle recibo debe estar en el path")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualizado correctamente"),
			@ApiResponse(code = 400, message = "No se pudo actualizar") })
	public ResponseEntity<DetalleRecibo> actualizarCodigoDetalle(@RequestBody DetalleRecibo detalleRecibo,
			@PathVariable Integer idDetalle) {
		// Chequeamos que la sucursal exista
		Optional<DetalleRecibo> optDetalleAActualizar = this.reciboSueldoService.getDetalleRecibo(idDetalle);
		if (optDetalleAActualizar.isPresent()) {
			// El detalle existe
			detalleRecibo.setId(idDetalle);
			return ResponseEntity.of(reciboSueldoService.actualizarDetalleRecibo(detalleRecibo));
		} else {
			// El detalle no existe
			return ResponseEntity.badRequest().build();
		}
	}

}
