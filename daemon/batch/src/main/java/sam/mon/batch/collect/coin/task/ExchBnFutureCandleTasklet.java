
package sam.mon.batch.collect.coin.task;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sam.mon.assemble.api.coin.binance.impl.ApiRequestImpl;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandle;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;
import sam.mon.assemble.model.enums.CandleInterval;
import sam.mon.assemble.repo.coin.binance.TbBnFutureCandleRepo;
import sam.mon.assemble.repo.coin.binance.TbBnFutureExchangeInfoEntryRepo;


@Slf4j
@Component
@StepScope
public class ExchBnFutureCandleTasklet implements Tasklet {

	@Autowired
	TbBnFutureCandleRepo tbBnFutureCandleRepo;
	
	@Autowired
	TbBnFutureExchangeInfoEntryRepo tbBnFutureExchangeInfoEntryRepo;

	@Autowired
	ApiRequestImpl apiRequestImpl;

	@Autowired
	EntityManager entityManager;
	
	
	@Value("${ats.daemon.batch.regid}")
	private String regId;

	@Value("#{jobParameters[requestDate]}")
	private String requestDate;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		

		long min = 1500l;

		List<String> aa = Arrays.asList("1000LUNCBUSD","1000SHIBUSDT", "ADAUSDT", "BTCUSDT");
		
		for(TbBnFutureExchangeInfoEntry ei : tbBnFutureExchangeInfoEntryRepo.findByListYn(true)){
			if(aa.contains(ei.getSymbol())){
				break;
			}
			
			

			Timestamp startTime = new Timestamp( ei.getOnboardDate().getTime()- TimeUnit.MINUTES.toMillis(min));
			Timestamp endTime = new Timestamp( ei.getOnboardDate().getTime()- TimeUnit.MINUTES.toMillis(1));
			Timestamp curr =  new Timestamp(System.currentTimeMillis());
			int cnt = 0;
			while(true) {
				
				startTime.setTime(startTime.getTime() + TimeUnit.MINUTES.toMillis(min));
				endTime.setTime(startTime.getTime() + TimeUnit.MINUTES.toMillis(min)); 		
				log.info(startTime  + "");
				log.info(endTime  + "");
				log.info("-----------------------------------------------------" + ++cnt);	
				
				try {
					List<TbBnFutureCandle> lstResEntry = apiRequestImpl.getCandle(ei.getSymbol(), CandleInterval.ONE_MIN, startTime, endTime, 1500);
					tbBnFutureCandleRepo.saveAll(lstResEntry);

					if(startTime.compareTo(curr) >= 0)
						break;
					
					Thread.sleep(300);
				}catch(Exception e) {
					
				}
				
				entityManager.getTransaction().commit();
			}
			
		}

		
		
		
		return null;
	}

}
