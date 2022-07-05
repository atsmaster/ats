package sam.mon.batch.collect.coin.task;

import java.util.ArrayList;
import java.util.LinkedList;
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

import com.binance.client.RequestOptions;
import com.binance.client.SyncRequestClient;
import com.binance.client.constant.BinanceApiConstants;
import com.binance.client.model.market.ExchangeInfoEntry;

import lombok.extern.slf4j.Slf4j;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntryHist;
import sam.mon.assemble.repo.coin.binance.TbBnFutureExchangeInfoEntryHistRepo;
import sam.mon.assemble.repo.coin.binance.TbBnFutureExchangeInfoEntryRepo;

@Slf4j
@Component
@StepScope
public class ExchBnFutureEntryTasklet implements Tasklet {

	@Autowired
	TbBnFutureExchangeInfoEntryRepo tbBnFutureExchangeInfoEntryRepo;
	
	@Autowired
	TbBnFutureExchangeInfoEntryHistRepo tbBnFutureExchangeInfoEntryHistRepo;
	
	@Value("${ats.daemon.batch.regid}")
	private String regId;

	@Value("#{jobParameters[requestDate]}")
	private String requestDate;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		/**
		 * 바이낸스 선물 거래소 종목 데이터 수집
		 *  
		 * 
		 * */
		
		
		log.info(">>>>> start ExchBnFutureEntryTasklet");


		// 시스템에 저장된 Db Entry 
		List<TbBnFutureExchangeInfoEntry> lstDbEntry = tbBnFutureExchangeInfoEntryRepo.findAll();		
		Map<String, TbBnFutureExchangeInfoEntry> mapDbEntry = lstDbEntry.stream().collect(	
		        Collectors.toMap(TbBnFutureExchangeInfoEntry::getSymbol, Function.identity()));	// Map ("BTCUSDT", TbBnFutureExchangeInfoEntry)
		
		// 거래소에 요청한 Res Entry
		RequestOptions options = new RequestOptions();
		SyncRequestClient syncRequestClient = SyncRequestClient.create(BinanceApiConstants.API_KEY,
				BinanceApiConstants.SECRET_KEY, options);
		List<TbBnFutureExchangeInfoEntry> lstResEntry = new ArrayList<TbBnFutureExchangeInfoEntry>();
		for(ExchangeInfoEntry eie : syncRequestClient.getExchangeInformation().getSymbols()) {
			TbBnFutureExchangeInfoEntry tbBnFutureExchangeInfoEntry = new TbBnFutureExchangeInfoEntry(eie);
			lstResEntry.add(tbBnFutureExchangeInfoEntry);
		}
		Map<String, TbBnFutureExchangeInfoEntry> mapResEntry = lstResEntry.stream().collect(
		        Collectors.toMap(TbBnFutureExchangeInfoEntry::getSymbol, Function.identity()));	// Map ("BTCUSDT", TbBnFutureExchangeInfoEntry)
		
		
		// TbBnFutureExchangeInfoEntry DTO
		List<TbBnFutureExchangeInfoEntry> lstNewEntry = new LinkedList<TbBnFutureExchangeInfoEntry>();
		List<TbBnFutureExchangeInfoEntry> lstUpdateEntry = new LinkedList<TbBnFutureExchangeInfoEntry>();	

		// TbBnFutureExchangeInfoEntryHist DTO
		List<TbBnFutureExchangeInfoEntryHist> lstNewEntryHist = new LinkedList<TbBnFutureExchangeInfoEntryHist>();	
		

		/**
		 * 비교시작
		 * 1. db, res 합집합군 처리	(변경된 정보 업데이트)
		 * 2. db  여집합군 처리		(신규 상장)
		 * 3. res 여집합군 처리		(상장 폐지)
		 * 
		 * */		
		for (TbBnFutureExchangeInfoEntry resEntry : lstResEntry) {
			if(mapDbEntry.keySet().contains(resEntry.getSymbol())) { //1. db, res 합집합군 처리 (변경된 정보 업데이트)
				TbBnFutureExchangeInfoEntry dbEntry = mapDbEntry.get(resEntry.getSymbol());
				if(!dbEntry.equals(resEntry)) {					
					
					// TbBnFutureExchangeInfoEntry
					resEntry.setListYn(true);
					resEntry.setPriceUseYn(dbEntry.getPriceUseYn());
					lstUpdateEntry.add(resEntry);

					//TbBnFutureExchangeInfoEntryHist
					TbBnFutureExchangeInfoEntryHist resEntryHist = new TbBnFutureExchangeInfoEntryHist(resEntry);
					resEntryHist.setListYn(true);
					lstNewEntryHist.add(resEntryHist);					
				}
				
			}else { // 2. db  여집합군 처리 (신규 상장)			
				
				// TbBnFutureExchangeInfoEntry
				resEntry.setListYn(true);
				resEntry.setPriceUseYn(false);
				resEntry.setNew(true);
				lstNewEntry.add(resEntry);
				
				//TbBnFutureExchangeInfoEntryHist
				TbBnFutureExchangeInfoEntryHist resEntryHist = new TbBnFutureExchangeInfoEntryHist(resEntry);
				resEntryHist.setListYn(true);
				resEntryHist.setNew(true);
				lstNewEntryHist.add(resEntryHist);
			}
		}

		for(TbBnFutureExchangeInfoEntry dbEntry : lstDbEntry) {
			if(!mapResEntry.keySet().contains(dbEntry.getSymbol())) { // 3. res 여집합군 처리 (상장 폐지)
				
				// TbBnFutureExchangeInfoEntry
				dbEntry.setListYn(false);
				lstUpdateEntry.add(dbEntry);
				
				//TbBnFutureExchangeInfoEntryHist
				TbBnFutureExchangeInfoEntryHist dbEntryHist = new TbBnFutureExchangeInfoEntryHist(dbEntry);
				dbEntryHist.setListYn(true);
				dbEntryHist.setNew(true);
				lstNewEntryHist.add(dbEntryHist);
			}
		}		
		
		// 신규(Insert) Entry 처리
		tbBnFutureExchangeInfoEntryRepo.saveAll(lstNewEntry);
		tbBnFutureExchangeInfoEntryHistRepo.saveAll(lstNewEntryHist);
		
		// 변경(Update) Entry처리
		tbBnFutureExchangeInfoEntryRepo.saveAll(lstUpdateEntry);

		return null;
	}

}
