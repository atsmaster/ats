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
public class ExchBnFutureEntryTasklet implements Tasklet {

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
		 * 바이낸스 선물 거래소 종목 데이터 수집
		 *  
		 * 
		 * */
		
		
		log.info(">>>>> start ExchBnFutureEntryTasklet");


		// DB에 저장된 Entry
		List<TbBnFutureExchangeInfoEntry> lstBeforeEntry = tbBnFutureExchangeInfoEntryRepo.findAll();		
		Map<String, TbBnFutureExchangeInfoEntry> mapBeforeEntry = lstBeforeEntry.stream().collect(	
		        Collectors.toMap(TbBnFutureExchangeInfoEntry::getSymbol, Function.identity()));	// Map ("BTCUSDT", TbBnFutureExchangeInfoEntry)
		

		List<TbBnFutureExchangeInfoEntry> lstNewEntry = new LinkedList<TbBnFutureExchangeInfoEntry>();
		List<TbBnFutureExchangeInfoEntry> lstUpdateEntry = new LinkedList<TbBnFutureExchangeInfoEntry>();	
		
		List<TbBnFutureExchangeInfoEntryHist> lstNewEntryHist = new LinkedList<TbBnFutureExchangeInfoEntryHist>();
		List<TbBnFutureExchangeInfoEntryHist> lstUpdateEntryHist = new LinkedList<TbBnFutureExchangeInfoEntryHist>();		
		
		RequestOptions options = new RequestOptions();
		SyncRequestClient syncRequestClient = SyncRequestClient.create(BinanceApiConstants.API_KEY,
				BinanceApiConstants.SECRET_KEY, options);		
		for (ExchangeInfoEntry resEntry : syncRequestClient.getExchangeInformation().getSymbols()) {			
			
			if(mapBeforeEntry.keySet().contains(resEntry.getSymbol())) { // update
				TbBnFutureExchangeInfoEntry entry = mapBeforeEntry.get(resEntry.getSymbol());	

				if(!entry.equalsApiVo(resEntry)) {	// db와 res결과가 다를때
					// TbBnFutureExchangeInfoEntry
					entry.setStatus(resEntry.getStatus());
					entry.setMaintMarginPercent(resEntry.getMaintMarginPercent());
					entry.setRequiredMarginPercent(resEntry.getRequiredMarginPercent());
					entry.setBaseAsset(resEntry.getBaseAsset());
					entry.setBaseAssetPrecision(resEntry.getBaseAssetPrecision());
					entry.setQuoteAsset(resEntry.getQuoteAsset());
					entry.setPricePrecision(resEntry.getPricePrecision());
					entry.setQuantityPrecision(resEntry.getQuantityPrecision());
					entry.setOnboardDate(new Timestamp(resEntry.getOnboardDate()));
					entry.setOrderTypes(resEntry.getOrderTypes());
					entry.setTimeInForce(resEntry.getTimeInForce());	
					lstUpdateEntry.add(entry);
					
					// TbBnFutureExchangeInfoEntryHist
					TbBnFutureExchangeInfoEntryHist entryHist = new TbBnFutureExchangeInfoEntryHist();
	 				TbBnFutureExchangeInfoEntryHistId entryHistId = new TbBnFutureExchangeInfoEntryHistId();		
	 				entryHist.setPersisNew(true); 			
	 				entryHistId.setSymbol(resEntry.getSymbol());
//	 				entryHistId.setSymbolInfoChgDate();
	 				entryHist.setTbBnFutureExchangeInfoEntryHistId(entryHistId);
	 				entryHist.setStatus(resEntry.getStatus());                                     
	 				entryHist.setMaintMarginPercent(resEntry.getMaintMarginPercent());             
	 				entryHist.setRequiredMarginPercent(resEntry.getRequiredMarginPercent());       
	 				entryHist.setBaseAsset(resEntry.getBaseAsset());                               
	 				entryHist.setBaseAssetPrecision(resEntry.getBaseAssetPrecision());             
	 				entryHist.setQuoteAsset(resEntry.getQuoteAsset());                             
	 				entryHist.setPricePrecision(resEntry.getPricePrecision());                     
	 				entryHist.setQuantityPrecision(resEntry.getQuantityPrecision());               
	 				entryHist.setOnboardDate(new Timestamp(resEntry.getOnboardDate()));            
	 				entryHist.setOrderTypes(resEntry.getOrderTypes());                             
	 				entryHist.setTimeInForce(resEntry.getTimeInForce());	  
	 				lstUpdateEntryHist.add(entryHist);	 				
				}				
				
			}else { // new
				// TbBnFutureExchangeInfoEntry
				TbBnFutureExchangeInfoEntry entry = new TbBnFutureExchangeInfoEntry();				
				entry.setPersisNew(true); 				
				entry.setSymbol(resEntry.getSymbol());
				entry.setStatus(resEntry.getStatus());
				entry.setMaintMarginPercent(resEntry.getMaintMarginPercent());
				entry.setRequiredMarginPercent(resEntry.getRequiredMarginPercent());
				entry.setBaseAsset(resEntry.getBaseAsset());
				entry.setBaseAssetPrecision(resEntry.getBaseAssetPrecision());
				entry.setQuoteAsset(resEntry.getQuoteAsset());
				entry.setPricePrecision(resEntry.getPricePrecision());
				entry.setQuantityPrecision(resEntry.getQuantityPrecision());
				entry.setOnboardDate(new Timestamp(resEntry.getOnboardDate()));
				entry.setOrderTypes(resEntry.getOrderTypes());
				entry.setTimeInForce(resEntry.getTimeInForce());	
				entry.setPriceUseYn(false);						
				lstNewEntry.add(entry);
				
				// TbBnFutureExchangeInfoEntryHist
				TbBnFutureExchangeInfoEntryHist entryHist = new TbBnFutureExchangeInfoEntryHist();
 				TbBnFutureExchangeInfoEntryHistId entryHistId = new TbBnFutureExchangeInfoEntryHistId();		
 				entryHist.setPersisNew(true); 			
 				entryHistId.setSymbol(resEntry.getSymbol());
// 				entryHistId.setSymbolInfoChgDate();
 				entryHist.setTbBnFutureExchangeInfoEntryHistId(entryHistId);
 				entryHist.setStatus(resEntry.getStatus());                                     
 				entryHist.setMaintMarginPercent(resEntry.getMaintMarginPercent());             
 				entryHist.setRequiredMarginPercent(resEntry.getRequiredMarginPercent());       
 				entryHist.setBaseAsset(resEntry.getBaseAsset());                               
 				entryHist.setBaseAssetPrecision(resEntry.getBaseAssetPrecision());             
 				entryHist.setQuoteAsset(resEntry.getQuoteAsset());                             
 				entryHist.setPricePrecision(resEntry.getPricePrecision());                     
 				entryHist.setQuantityPrecision(resEntry.getQuantityPrecision());               
 				entryHist.setOnboardDate(new Timestamp(resEntry.getOnboardDate()));            
 				entryHist.setOrderTypes(resEntry.getOrderTypes());                             
 				entryHist.setTimeInForce(resEntry.getTimeInForce());	  
 				lstNewEntryHist.add(entryHist);
			}
		}		
		// 신규(Insert) Entry 처리
		tbBnFutureExchangeInfoEntryRepo.saveAll(lstNewEntry);
		tbBnFutureExchangeInfoEntryHistRepo.saveAll(lstNewEntryHist);
		
		// 변경(Update) Entry처리
		tbBnFutureExchangeInfoEntryRepo.saveAll(lstUpdateEntry);
		tbBnFutureExchangeInfoEntryHistRepo.saveAll(lstUpdateEntryHist);

		return null;
	}

}
