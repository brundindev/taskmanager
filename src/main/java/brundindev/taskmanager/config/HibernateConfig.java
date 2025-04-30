/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "brundindev.taskmanager.model")
@EnableJpaRepositories(basePackages = "brundindev.taskmanager.repository")
public class HibernateConfig {

} 
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */