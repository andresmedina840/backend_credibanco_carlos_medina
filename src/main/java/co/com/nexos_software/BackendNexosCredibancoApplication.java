package co.com.nexos_software;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BackendNexosCredibancoApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().load();

		System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
		System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
		System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));

		Locale locale = Locale.of("es", "CO");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", locale);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));

		SimpleDateFormat sdf = new SimpleDateFormat("EEEEE dd/MM/yyyy hh:mm:ss a", locale);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));

		SpringApplication.run(BackendNexosCredibancoApplication.class, args);

		System.out.println(
				"Funcionando backend - Credibanco: " + dateFormat.format(new Date()) + " - " + sdf.format(new Date()));
	}

}
