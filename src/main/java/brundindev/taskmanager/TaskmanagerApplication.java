/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
		System.out.println("--------------------------------");
		System.out.println("Web desplegada en: http://localhost:8080/");
		System.out.println("Base de datos H2 en: http://localhost:8080/h2-console");
		System.out.println("--------------------------------");
	}

}
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */