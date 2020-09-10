package cl.luaresp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cl.luaresp")
public class ProgramingExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgramingExerciseApplication.class, args);
	}

}
