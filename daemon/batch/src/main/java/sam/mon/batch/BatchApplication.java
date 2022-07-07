package sam.mon.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableBatchProcessing
@EnableJpaAuditing
@EntityScan(basePackages = {"sam.mon.assemble"})
@EnableJpaRepositories(basePackages = {"sam.mon.assemble"})
@ComponentScan(basePackages = {"sam.mon.batch","sam.mon.assemble"})
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}
}
