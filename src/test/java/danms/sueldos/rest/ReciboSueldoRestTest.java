package danms.sueldos.rest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
class ReciboSueldoRestTest {
	
	private String ENDPOINT_CODIGO_DETALLE = "/api/sueldos/recibosueldo/codigodetalle";

	private static final Logger logger = LoggerFactory.getLogger(SucursalRestTest.class);

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
