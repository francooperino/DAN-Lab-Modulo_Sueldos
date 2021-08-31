package danms.sueldos.services.interfaces;

public interface JasperReportService {
	public String generarReciboSueldo(Integer id_recibo);
	
	public String generarPathDestinoFile(Integer idRecibo);
	
	
}
