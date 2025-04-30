/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.config;

import java.util.Properties;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EntityScan(basePackages = "brundindev.taskmanager.model")
@EnableJpaRepositories(basePackages = "brundindev.taskmanager.repository")
public class HibernateConfig {
    
    /**
     * Configuración del adaptador JPA para Hibernate
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        return adapter;
    }
    
    /**
     * Propiedades adicionales de Hibernate para optimizar el rendimiento
     */
    @Bean
    public Properties hibernateProperties() {
        Properties properties = new Properties();
        
        // Configuración de segundo nivel de caché
        properties.setProperty("hibernate.cache.use_second_level_cache", "false");
        
        // Configuración de statement batching para mejorar el rendimiento
        properties.setProperty("hibernate.jdbc.batch_size", "50");
        properties.setProperty("hibernate.order_inserts", "true");
        properties.setProperty("hibernate.order_updates", "true");
        
        // Estrategia de nombrado físico para mejorar compatibilidad con bases de datos
        properties.setProperty("hibernate.physical_naming_strategy", 
                "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        
        // Lazy loading optimizado
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        
        // Mejorar logs SQL
        properties.setProperty("hibernate.format_sql", "true");
        
        return properties;
    }
} 
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */