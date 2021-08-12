package danms.sueldos.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danms.sueldos.domain.ReciboSueldo;
import danms.sueldos.services.interfaces.JasperReportService;
import danms.sueldos.services.interfaces.ReciboSueldoService;


@RestController
@RequestMapping("/api/sueldos/reportes")
public class JasperReportRest {
	
	@Autowired
	ReciboSueldoService reciboSueldoService;
	
	@Autowired
	JasperReportService jasperReportService;

	@GetMapping("/recibosueldo/{idRecibo}")
	public ResponseEntity<?> getReporteReciboSueldo(@PathVariable Integer idRecibo) {
		//Revisamos si el recibo de sueldo existe en la DB
		Optional<ReciboSueldo> optReciboBuscado = reciboSueldoService.getReciboSueldo(idRecibo);
		if(optReciboBuscado.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		//Recibo existe genero report
		jasperReportService.generarReciboSueldo(idRecibo);
		return ResponseEntity.ok().build();
	}
}
