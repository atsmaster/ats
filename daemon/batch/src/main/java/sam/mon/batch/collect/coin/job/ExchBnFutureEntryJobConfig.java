package sam.mon.batch.collect.coin.job;

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
import sam.mon.batch.collect.coin.task.ExchBnFutureEntryTasklet;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ExchBnFutureEntryJobConfig {
	
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ExchBnFutureEntryTasklet exchBnFutureEntryTasklet;

    @Bean
    public Job exchBnFutureEntryJob() {
    	log.info(">>>>> start exchBnFutureEntryJob");
        return jobBuilderFactory.get("exchBnFutureEntryJob")
        		.preventRestart()
                .start(exchBnFutureEntryStep(null))
//                .next(datalabWriteStep(null))
                .build();
    }


    @Bean
	@JobScope
	public Step exchBnFutureEntryStep(@Value("#{jobParameters[requestDate]}") String requestDate) {
		log.info(">>>>> This is exchBnFutureEntryStep");
		return stepBuilderFactory
				.get("exchBnFutureEntryStep")
				.tasklet(exchBnFutureEntryTasklet).build();
    }

}
