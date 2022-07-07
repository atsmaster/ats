package sam.mon.batch.collect.coin;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import sam.mon.assemble.api.coin.binance.BnFutureApi;
import sam.mon.assemble.api.coin.binance.impl.ApiRequestImpl;
import sam.mon.assemble.repo.coin.binance.TbBnFutureExchangeInfoEntryHistRepo;
import sam.mon.assemble.repo.coin.binance.TbBnFutureExchangeInfoEntryRepo;
import sam.mon.batch.TestBatchConfig;
import sam.mon.batch.collect.coin.job.ExchBnFutureEntryJobConfig;
import sam.mon.batch.collect.coin.task.ExchBnFutureEntryTasklet;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = { TestBatchConfig.class, ExchBnFutureEntryJobConfig.class, ExchBnFutureEntryTasklet.class
		, TbBnFutureExchangeInfoEntryRepo.class, TbBnFutureExchangeInfoEntryHistRepo.class, ApiRequestImpl.class, BnFutureApi.class})
@EntityScan(basePackages = {"sam.mon.batch", "sam.mon.assemble"})
@EnableJpaRepositories(basePackages = {"sam.mon.batch", "sam.mon.assemble"})

//@DataJpaTest
@EnableJpaAuditing
public class ExchBnFutureEntryJobTest {

	@Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;


    @Before
    public void clearMetadata() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void testJob() throws Exception {
    	
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        System.out.println(timestamp.toString());
    	
        JobParameters jobParameters = new JobParametersBuilder()         		
                .addString("requestDate", timestamp.toString())
                .toJobParameters();		

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assert.assertEquals(ExitStatus.COMPLETED,
            jobExecution.getExitStatus());
    }
}
	