package sam.mon.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.TestPropertySource;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@EnableJpaAuditing
@ComponentScan(basePackages = {"sam.mon.batch","sam.mon.assemble"})
@TestPropertySource(locations = {"application-test.properties"})
public class TestBatchConfig {

}
