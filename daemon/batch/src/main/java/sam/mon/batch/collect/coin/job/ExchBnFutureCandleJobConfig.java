package sam.mon.batch.collect.coin.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sam.mon.batch.collect.coin.task.ExchBnFutureCandleTasklet;
import sam.mon.batch.collect.coin.task.ExchBnFutureEntryTasklet;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties
@EnableBatchProcessing
public class ExchBnFutureCandleJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ExchBnFutureEntryTasklet exchBnFutureEntryTasklet;
    private final ExchBnFutureCandleTasklet exchBnFutureCandleTasklet;
    
//    @Bean
//    public Job exchBnFutureCandleJob() {
//    	log.info(">>>>> start exchBnFutureCandleJob");
//        return jobBuilderFactory.get("exchBnFutureCandleJob")
//        		.preventRestart()
//                .start(exchBnFutureEntryStep(null))
//                .next(exchBnFutureCandleStep(null))
//                .build();
//    }

//
//    @Bean
//	@JobScope
//	public Step exchBnFutureEntryStep(@Value("#{jobParameters[requestDate]}") String requestDate) {
//		log.info(">>>>> This is exchBnFutureEntryStep");
//		return stepBuilderFactory
//				.get("sampleStep")
//				.tasklet(exchBnFutureEntryTasklet)
//				.tasklet(exchBnFutureEntryTasklet).build();
//    }
//
//    @Bean
//	@JobScope
//	public Step exchBnFutureCandleStep(@Value("#{jobParameters[requestDate]}") String requestDate) {
//		log.info(">>>>> This is exchBnFutureCandleStep");
//		return stepBuilderFactory
//				.get("exchBnFutureCandleStep")
//				.tasklet(exchBnFutureCandleTasklet).build();
//    }

}
