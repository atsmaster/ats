
package sam.mon.batch.collect.coin.task;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import sam.mon.assemble.api.coin.binance.impl.ApiRequestImpl;
import sam.mon.assemble.model.coin.binance.MaxTimeOpenOfSymbol;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandle;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;
import sam.mon.assemble.model.enums.CandleInterval;
import sam.mon.assemble.repo.coin.binance.TbBnFutureCandleRepo;
import sam.mon.assemble.repo.coin.binance.TbBnFutureExchangeInfoEntryRepo;

@Slf4j
@Component
@StepScope
@Transactional(propagation = Propagation.REQUIRES_NEW)
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

		
		long min = 1500l;
		
		// 상장된 종목
		List<TbBnFutureExchangeInfoEntry> lstEntry = tbBnFutureExchangeInfoEntryRepo.findByListYn(true); 
		Map<String, TbBnFutureExchangeInfoEntry> mapEntry = lstEntry.stream().collect(
				Collectors.toMap(TbBnFutureExchangeInfoEntry::getSymbol, Function.identity()));	// Map ("BTCUSDT", TbBnFutureExchangeInfoEntry)
		
		// 종목별 max candle time 세팅
		for(MaxTimeOpenOfSymbol mto : tbBnFutureCandleRepo.findMaxOpenTimeOfSymbol()) {
			TbBnFutureExchangeInfoEntry ei = mapEntry.get(mto.getSymbol());			
			if(ei != null) {
				ei.setOnboardDate(mto.getTimeOpen());
			}
		}

		int whileCnt = 0;

		for ( String key : mapEntry.keySet() ) {			
			TbBnFutureExchangeInfoEntry ei = mapEntry.get(key);			

			whileCnt++;
			if (whileCnt == 2)
				break;

			Timestamp startTime = new Timestamp(ei.getOnboardDate().getTime() - TimeUnit.MINUTES.toMillis(min));
			Timestamp endTime = new Timestamp(ei.getOnboardDate().getTime());
			Timestamp curr = new Timestamp(System.currentTimeMillis());
			int cnt = 0;
			while (true) {

				startTime.setTime(startTime.getTime() + TimeUnit.MINUTES.toMillis(min));
				endTime.setTime(startTime.getTime() + TimeUnit.MINUTES.toMillis(min-1));				
				log.info("[Start-End] " + startTime + " - " + endTime);				
				log.info("-----------------------------------------------------" + ++cnt);

				try {
					List<TbBnFutureCandle> lstResEntry = apiRequestImpl.getCandle(ei.getSymbol(),
							CandleInterval.ONE_MIN, startTime, endTime, 1500);
					tbBnFutureCandleRepo.saveAll(lstResEntry);

					if (startTime.compareTo(curr) >= 0)
						break;
					Thread.sleep(300);
				} catch (Exception e) {

				}
			}

		}

		return null;
	}

}
