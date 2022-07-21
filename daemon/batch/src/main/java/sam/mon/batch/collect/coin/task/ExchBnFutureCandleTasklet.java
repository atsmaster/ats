
package sam.mon.batch.collect.coin.task;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
public class ExchBnFutureCandleTasklet implements Tasklet {

	@Autowired
	TbBnFutureCandleRepo tbBnFutureCandleRepo;

	@Autowired
	TbBnFutureExchangeInfoEntryRepo tbBnFutureExchangeInfoEntryRepo;

	@Autowired
	ApiRequestImpl apiRequestImpl;
	
	@Autowired
	PlatformTransactionManager transactionManager;	

	@Value("${ats.daemon.batch.regid}")
	private String regId;

	@Value("#{jobParameters[requestDate]}")
	private String requestDate;

	@Value("#{jobParameters[endTime]}")
	private Timestamp endTime;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {		
		
		long minFreq = 1500l;		
		
		List<MaxTimeOpenOfSymbol> lstCandleMax = tbBnFutureCandleRepo.findMaxOpenTimeOfSymbol();
		Map<String, Timestamp> mapCandleMax = lstCandleMax.stream().collect(
				Collectors.toMap(MaxTimeOpenOfSymbol::getSymbol, MaxTimeOpenOfSymbol::getTimeOpen));	// Map ("BTCUSDT", "2022-07-13 09:28:57")	

		for ( TbBnFutureExchangeInfoEntry ei : tbBnFutureExchangeInfoEntryRepo.findByListYn(true)) {				
			
			// API요청 전체 시작일시 설정
			// - 신규 종목 : 상장일시
			// - 기존 종목 : max(open_time)
			Timestamp startTime = ei.getOnboardDate();
			Timestamp candleMaxTime = mapCandleMax.get(ei.getSymbol()); 
			if(candleMaxTime!=null) {
				candleMaxTime.setTime(candleMaxTime.getTime() + TimeUnit.MINUTES.toMillis(1));
				startTime = candleMaxTime;
			}						
			// API요청 전체 종료일시 설정
			Timestamp endTime = this.endTime;
			// API 요청 부분 시작일시 설정
//			Timestamp reqStartTime = new Timestamp(startTime.getTime() - TimeUnit.MINUTES.toMillis(minFreq));			
			Timestamp reqStartTime = Timestamp.valueOf("2021-03-01 09:24:23");
			// API 요청 부분 종료일시 설정
			Timestamp reqEndTime = new Timestamp(startTime.getTime());
			
			if(startTime.compareTo(endTime) >= 0)
				continue;

			
			while (true) {
				try {
					reqStartTime.setTime(reqStartTime.getTime() + TimeUnit.MINUTES.toMillis(minFreq));
					reqEndTime.setTime(reqStartTime.getTime() + TimeUnit.MINUTES.toMillis(minFreq-1));				
					log.info(ei.getSymbol() + " [Start-End] " + reqStartTime + " - " + reqEndTime);							

					if (reqEndTime.compareTo(endTime) >= 0) {
						saveAndCommit(apiRequestImpl.getCandle(ei.getSymbol(), CandleInterval.ONE_MIN, reqStartTime, endTime, 1500));					
						break;
					}					
					saveAndCommit(apiRequestImpl.getCandle(ei.getSymbol(), CandleInterval.ONE_MIN, reqStartTime, reqEndTime, 1500));					
					Thread.sleep(200);					
				}catch(Exception e) {		
					log.info(e.toString());
					break;
				}
			}
			
			break;
		}
		return null;
	}

	
	private void saveAndCommit(List<TbBnFutureCandle> values) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		tbBnFutureCandleRepo.saveAll(values);
		
		transactionManager.commit(status);
	}	
}
