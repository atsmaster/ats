
package sam.mon.batch.collect.coin.task;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
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

	
	@Value("${ats.daemon.batch.regid}")
	private String regId;

	@Value("#{jobParameters[requestDate]}")
	private String requestDate;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		Optional<TbBnFutureExchangeInfoEntry> aa = tbBnFutureExchangeInfoEntryRepo.findById("BTCUSDT");
		Timestamp onboard =  new Timestamp( aa.get().getOnboardDate().getTime());
		Timestamp curr =  new Timestamp(System.currentTimeMillis());

		Timestamp startTime = onboard;
		Timestamp endTime;
		long min = 1500l;
		int cnt = 0;
		while(true) {
			if(curr.compareTo(onboard)==-1)
				break;			
			
			endTime = new Timestamp(startTime.getTime() + TimeUnit.MINUTES.toMillis(min-1)); 
			
			List<TbBnFutureCandle> lstResEntry = apiRequestImpl.getCandle("BTCUSDT", CandleInterval.ONE_MIN, startTime, endTime, 1500);
			tbBnFutureCandleRepo.saveAll(lstResEntry);
			
			log.info(startTime  + "");
			log.info(endTime  + "");
			log.info("-----------------------------------------------------" + ++cnt);
			
			startTime.setTime(startTime.getTime() + TimeUnit.MINUTES.toMillis(min));
			Thread.sleep(1000);
		}
			
		
		
		
		
//		List<TbBnFutureCandle> lstResEntry = apiRequestImpl.getCandle("BTCUSDT", CandleInterval.ONE_MIN, null, null, 1500);
//		tbBnFutureCandleRepo.saveAll(lstRessEntry);
		
//		 List<String> aaa = tbBnFutureCandleMinRepo.findAllMaxOpenTime();

//		// 시스템에 저장된 Db Entry 
//		for(TbBnFutureExchangeInfoEntry ei : tbBnFutureExchangeInfoEntryRepo) {
//			
//			tbBnFutureExchangeInfoEntryRepo.findMaxOpenTimeBySymbol()
//		}
		
		
//		List<TbBnFutureCandle> lstResEntry = apiRequestImpl.getCandle(ei.getSymbol(), CandleInterval.ONE_MINUTE, 1657260000000L, 1657261000000L, 100);

		
		
		
//		List<TbBnFutureExchangeInfoEntry> lstEntry = tbBnFutureExchangeInfoEntryRepo.findAll();
//		Map<String, TbBnFutureExchangeInfoEntry> mapEntry = lstEntry.stream().collect(	
//		        Collectors.toMap(TbBnFutureExchangeInfoEntry::getSymbol, Function.identity()));	// Map ("BTCUSDT", TbBnFutureExchangeInfoEntry)
//		
//		
//		for(TbBnFutureCandleMin cm : tbBnFutureCandleMinRepo.findMaxOpenTimeBySymbol()) {
//			
//		}

		
		
		return null;
	}

}
