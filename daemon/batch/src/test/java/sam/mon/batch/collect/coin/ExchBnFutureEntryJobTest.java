package sam.mon.batch.collect.coin;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import sam.mon.batch.TestBatchConfig;
import sam.mon.batch.collect.coin.job.ExchBnFutureEntryJobConfig;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {ExchBnFutureEntryJobConfig.class, TestBatchConfig.class })
public class ExchBnFutureEntryJobTest {

	@Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

//    @Autowired
//    private JobRepositoryTestUtils jobRepositoryTestUtils;
//
//
//    @Before
//    public void clearMetadata() {
//        jobRepositoryTestUtils.removeJobExecutions();
//    }

	
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
	