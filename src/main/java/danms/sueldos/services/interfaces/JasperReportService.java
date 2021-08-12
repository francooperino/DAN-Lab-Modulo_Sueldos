package danms.sueldos.services.interfaces;

public interface JasperReportService {
	public Boolean generarReciboSueldo(Integer id_recibo);
	
	public String generarPathDestinoFile(Integer idRecibo);
	
	
}
