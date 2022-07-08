
package sam.mon.batch.collect.coin.task;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import sam.mon.assemble.model.CandleInterval;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandle;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;
import sam.mon.assemble.repo.coin.binance.TbBnFutureCandleMinRepo;
import sam.mon.assemble.repo.coin.binance.TbBnFutureExchangeInfoEntryHistRepo;
import sam.mon.assemble.repo.coin.binance.TbBnFutureExchangeInfoEntryRepo;

@Slf4j
@Component
@StepScope
public class ExchBnFutureCandleTasklet implements Tasklet {

	@Autowired
	TbBnFutureExchangeInfoEntryRepo tbBnFutureExchangeInfoEntryRepo;
	
	@Autowired
	TbBnFutureExchangeInfoEntryHistRepo tbBnFutureExchangeInfoEntryHistRepo;
	
	@Autowired
	TbBnFutureCandleMinRepo tbBnFutureCandleMinRepo;

	@Autowired
	ApiRequestImpl apiRequestImpl;
	
	
	@Value("${ats.daemon.batch.regid}")
	private String regId;

	@Value("#{jobParameters[requestDate]}")
	private String requestDate;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		log.info(">>>>> start ExchBnFutureCandleTasklet");


		// 시스템에 저장된 Db Entry 
		List<TbBnFutureExchangeInfoEntry> lstDbEntry = tbBnFutureExchangeInfoEntryRepo.findAll();		
		Map<String, TbBnFutureExchangeInfoEntry> mapDbEntry = lstDbEntry.stream().collect(	
		        Collectors.toMap(TbBnFutureExchangeInfoEntry::getSymbol, Function.identity()));	// Map ("BTCUSDT", TbBnFutureExchangeInfoEntry)
		
		
		TbBnFutureExchangeInfoEntry ei = mapDbEntry.get("BTCUSDT");

		List<TbBnFutureCandle> lstResEntry = apiRequestImpl.getCandle(ei.getSymbol(), CandleInterval.ONE_MINUTE, 1657260000000L, 1657261000000L, 100);
		
		
		System.out.println("asdsadoksa");
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
