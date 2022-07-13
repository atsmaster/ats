package sam.mon.batch;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
public class JustTest {

	@Test
	public void testSum() {

		List<BigDecimal> lstBd = new ArrayList<BigDecimal>();
		List<BigDecimal> lstBdA = new ArrayList<BigDecimal>();
		
		PriorityQueue<BigDecimal> pqBd = new PriorityQueue<BigDecimal>(Collections.reverseOrder());
		
		
		
		for(int i = 0; i< 10000000 ; i++) 
		{
			String sValue = (Math.random() * 100000) + "";		
			BigDecimal bd = new BigDecimal(sValue);		
			lstBd.add(bd);
//			pqBd.add(bd);
		}
		
		
		// List min max
		Timestamp stimestamp = new Timestamp(System.currentTimeMillis());
		log.info(stimestamp.toString());
		
		BigDecimal bdMax = new BigDecimal("0");
		BigDecimal bdMin = new BigDecimal("0");
		for(BigDecimal bd : lstBd) {
			if(bdMax.compareTo(bd) == -1) {
				bdMax = bd;
			}
		}		
		log.info(bdMax.toString());
		
		Timestamp etimestamp = new Timestamp(System.currentTimeMillis());
		log.info(etimestamp.toString());		
		log.info(etimestamp.getTime() - stimestamp.getTime() + "");		

		

		log.info("-------------------------------");
		
		// PriorityQueue
		Timestamp stimestamp2 = new Timestamp(System.currentTimeMillis());
		log.info(stimestamp.toString());
		
		for(BigDecimal bd : lstBd) {
			pqBd.add(bd);
		}		

		log.info(pqBd.poll().toString());

		Timestamp etimestamp2 = new Timestamp(System.currentTimeMillis());
		log.info(etimestamp2.toString());		
		log.info(etimestamp2.getTime() - stimestamp2.getTime() + "");		
		
	}

}
