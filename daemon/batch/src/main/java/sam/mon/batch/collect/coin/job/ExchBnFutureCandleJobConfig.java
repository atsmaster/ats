package sam.mon.batch.collect.coin.job;

import java.sql.Timestamp;

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
import sam.mon.batch.collect.coin.task.ExchBnFutureCandleTasklet;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ExchBnFutureCandleJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ExchBnFutureCandleTasklet exchBnFutureCandleTasklet;
    
    
    @Bean
    public Job exchBnFutureCandleJob() {
    	log.info(">>>>> start exchBnFutureCandleJob");
        return jobBuilderFactory.get("exchBnFutureCandleJob")
        		.preventRestart()
                .start(exchBnFutureCandleStep(null, null))
                .build();
    }

    @Bean
	@JobScope
	public Step exchBnFutureCandleStep(@Value("#{jobParameters[requestDate]}") String requestDate, @Value("#{jobParameters[endTime]}") Timestamp endTime) {
		log.info(">>>>> This is exchBnFutureCandleStep");
		return stepBuilderFactory
				.get("exchBnFutureCandleStep")
				.tasklet(exchBnFutureCandleTasklet).build();
    }
    
}
