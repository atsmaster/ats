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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import sam.mon.batch.TestBatchLegacyConfig;
import sam.mon.batch.collect.coin.job.ExchBnFutureEntryJobConfig;
import sam.mon.batch.collect.coin.task.ExchBnFutureEntryTasklet;

@RunWith(SpringRunner.class)
//@SpringBatchTest
@SpringBootTest(classes = { TestBatchLegacyConfig.class, ExchBnFutureEntryJobConfig.class, ExchBnFutureEntryTasklet.class})

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
                .addString("requestDate", "20211219090001")
                .toJobParameters();		

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assert.assertEquals(ExitStatus.COMPLETED,
            jobExecution.getExitStatus());
    }
}
