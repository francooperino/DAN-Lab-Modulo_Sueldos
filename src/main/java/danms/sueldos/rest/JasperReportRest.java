package danms.sueldos.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danms.sueldos.domain.ReciboSueldo;
import danms.sueldos.services.interfaces.JasperReportService;
import danms.sueldos.services.interfaces.ReciboSueldoService;


@RestController
@RequestMapping("/api/sueldos/reportes")
@CrossOrigin("*")
public class JasperReportRest {
	
	@Autowired
	ReciboSueldoService reciboSueldoService;
	
	@Autowired
	JasperReportService jasperReportService;

	@GetMapping("/recibosueldo/{idRecibo}")
	public ResponseEntity<byte[]> getReporteReciboSueldo(@PathVariable Integer idRecibo) {
		//Revisamos si el recibo de sueldo existe en la DB
		Optional<ReciboSueldo> optReciboBuscado = reciboSueldoService.getReciboSueldo(idRecibo);
		if(optReciboBuscado.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		//Recibo existe genero report
		String pdfGenerado = jasperReportService.generarReciboSueldo(idRecibo);
		byte[] pdfContents;
		Path path = Paths.get(pdfGenerado);
		if(pdfGenerado==null) {
			return ResponseEntity.internalServerError().build();
		}else {	 	
			 	try {
			 		pdfContents = Files.readAllBytes(path);
			 	} catch (IOException e) {
			 		e.printStackTrace();
			 		return ResponseEntity.internalServerError().build();
			 	}
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_PDF);
			    // Here you have to set the actual filename of your pdf
			    String filename = "Recibo-id_"+idRecibo+".pdf";
			    headers.setContentDispositionFormData(filename, filename);
			    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			    ResponseEntity<byte[]> response = new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
			    return response;
		}
	}
}
