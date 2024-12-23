package br.com.alura.desafio_screenmatch;

import br.com.alura.desafio_screenmatch.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
