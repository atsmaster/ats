package sam.mon.batch.sample.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SampleJobConfig {
    
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final SampleTasklet sampleTasklet;
    
    @Bean
    public Job sampleJob() {
    	log.info(">>>>> sampleJob");
        return jobBuilderFactory.get("sampleJob")
        		.preventRestart()
        		.start(sampleStep(null))
//                .start(datalabCrawlStep(null))
//                .next(datalabWriteStep(null))
                .build();
    }

    
    @Bean
	@JobScope
	public Step sampleStep(@Value("#{jobParameters[requestDate]}") String requestDate) {
		log.info(">>>>> This is sampleStep");
		return stepBuilderFactory
				.get("sampleStep")
				.tasklet(sampleTasklet).build();
    }
}
