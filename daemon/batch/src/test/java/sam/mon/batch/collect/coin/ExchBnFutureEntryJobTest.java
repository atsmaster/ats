package sam.mon.batch.collect.coin;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import sam.mon.batch.collect.coin.job.ExchBnFutureEntryJobConfig;

@RunWith(SpringRunner.class)
@SpringBatchTest
@ContextConfiguration(classes = {ExchBnFutureEntryJobConfig.class})
public class ExchBnFutureEntryJobTest {

	@Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;


//    @Before
//    public void clearMetadata() {
//        jobRepositoryTestUtils.removeJobExecutions();
//    }

    @Test
    public void testJob() throws Exception {
    	
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        System.out.println(timestamp.toString());
    	
//        // given
//        JobParameters jobParameters = new JobParametersBuilder() 
//        		
//                .addString("requestDate", "20211219090001")
//                .toJobParameters();
//		
//
//        // when
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
//
//        // then
//        Assert.assertEquals(ExitStatus.COMPLETED,
//            jobExecution.getExitStatus());
    }
}
