package sam.mon.batch.collect.coin.step;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.binance.client.RequestOptions;
import com.binance.client.SyncRequestClient;
import com.binance.client.constant.BinanceApiConstants;
import com.binance.client.model.market.ExchangeInfoEntry;

import lombok.extern.slf4j.Slf4j;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntryHist;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntryHistId;
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

	@PersistenceContext
	private EntityManager entityManager;
	
	@Value("${ats.daemon.batch.regid}")
	private String regId;

	@Value("#{jobParameters[requestDate]}")
	private String requestDate;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		/**
		 * 바이낸스 선물 거래소의 종목별, N봉별 저장
		 *  
		 * 
		 * */
		
		List<TbBnFutureExchangeInfoEntry> lstEntry = tbBnFutureExchangeInfoEntryRepo.findAll();
		
		for(TbBnFutureExchangeInfoEntry entry : lstEntry) {
			
		}
		
		log.info(">>>>> start ExchBnFutureCandleTasklet");

		
		
		return null;
	}

}
