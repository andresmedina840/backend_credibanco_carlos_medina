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