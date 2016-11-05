package net.alagris.src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:/Beans.xml")
public class ThisGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThisGameApplication.class, args);
	}
}
