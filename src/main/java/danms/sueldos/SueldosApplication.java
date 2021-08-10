package danms.sueldos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SueldosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SueldosApplication.class, args);
	}

}
