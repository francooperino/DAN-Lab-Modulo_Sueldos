package danms.sueldos.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import danms.sueldos.domain.ReciboSueldo;
import danms.sueldos.services.interfaces.JasperReportService;
import danms.sueldos.services.interfaces.ReciboSueldoService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class JasperReportServiceImp implements JasperReportService {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	ReciboSueldoService reciboSueldoService;
	
	@Value("${jasperImagesSources}")
	private String rutaImagenesReporte;

	@Value("${jasperDestinoReportes}") //La obtenemos del app-properties
	private String destFileName;
	@Value("${jasperSourceTemplateReportReciboSueldo}") //La obtenemos del app-properties
	private String pathPlantillaJasper;

	private static final Logger logger = LoggerFactory.getLogger(JasperReportServiceImp.class);

	private Connection connection;

	@Override
	public String generarReciboSueldo(Integer id_recibo) {
		logger.info("Generacion del reporte del recibo de sueldo con id: " + id_recibo);
		String path = null;
		try {
			// 1. compile template ".jrxml" file
			JasperReport jasperReport;
			jasperReport = getJasperReport();
			// 2. parameters "empty"
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("nro_recibo", id_recibo);
			parameters.put("RutasImagenes",rutaImagenesReporte);
			// 3. datasource "java object"
			connection = dataSource.getConnection();
			JasperPrint jasperPrint;
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
			
			path= generarPathDestinoFile(id_recibo);
			JasperExportManager.exportReportToPdfFile(jasperPrint,path);
			return path;
		} catch (SQLException e) {
			logger.error("Ocurrio un error al obtener la conexion a la DB");
			logger.error("No se puedo generar el reporte");
			e.printStackTrace();
			return path;
		} catch (FileNotFoundException e) {
			logger.error("No se pudo abrir el archivo en la path: " + pathPlantillaJasper);
			logger.error("No se puedo generar el reporte");
			e.printStackTrace();
			return path;
		} catch (JRException e) {
			logger.error("No se puedo generar el reporte");
			e.printStackTrace();
			return path;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Fallo al cerrar la conexion con la DB");
				e.printStackTrace();
			}
		}

	}

	private JasperReport getJasperReport() throws FileNotFoundException, JRException {
		/*Codigo para generar el jasper report desde un .jrxml*/
		//File template = ResourceUtils.getFile(pathPlantillaJasper);
		//return JasperCompileManager.compileReport(template.getAbsolutePath());
		/*Codigo para generar el jasper report desde un .jasper (Quiero creer que es mas rapido porque ya esta compilado)*/
		File template = ResourceUtils.getFile(pathPlantillaJasper);
		JasperReport rpt = (JasperReport)JRLoader.loadObject(template);
		return rpt;
	}
	
	@Override
	public String generarPathDestinoFile(Integer idRecibo) {
		String pathFinal = destFileName;
		ReciboSueldo recibo = reciboSueldoService.getReciboSueldo(idRecibo).get();
		//Formateamos el mes de la fecha de emision:
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
	    Integer month = Integer.parseInt(dateFormat.format(recibo.getFechaEmision()));
		//Contruimos el nombre del archivo final
		pathFinal += "/recibo_idEmp"+recibo.getEmpleado().getId()
				+"_fechaEmision_"+month+".pdf";
		//
		return pathFinal;
	}
}
