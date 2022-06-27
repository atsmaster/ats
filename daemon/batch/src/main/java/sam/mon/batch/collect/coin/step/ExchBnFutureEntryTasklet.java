package sam.mon.batch.collect.coin.step;

import java.sql.Timestamp;
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
import sam.mon.assemble.repo.coin.binance.TbBnFutureExchangeInfoEntryRepo;

@Slf4j
@Component
@StepScope
public class ExchBnFutureEntryTasklet implements Tasklet {

	@Autowired
	TbBnFutureExchangeInfoEntryRepo tbBnFutureExchangeInfoEntryRepo;

	@Value("${ats.daemon.batch.regid}")
	private String regId;

	@Value("#{jobParameters[requestDate]}")
	private String requestDate;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		log.info(">>>>> start ExchBnFutureEntryTasklet");


		// DB에 저장된 Entry
		List<TbBnFutureExchangeInfoEntry> lstBeforeEntry = tbBnFutureExchangeInfoEntryRepo.findAll();		
		Map<String, TbBnFutureExchangeInfoEntry> mapBeforeEntry = lstBeforeEntry.stream().collect(
		        Collectors.toMap(TbBnFutureExchangeInfoEntry::getSymbol, Function.identity()));		
		
		List<TbBnFutureExchangeInfoEntry> lstNewEntry = new LinkedList<TbBnFutureExchangeInfoEntry>();
		
		RequestOptions options = new RequestOptions();
		SyncRequestClient syncRequestClient = SyncRequestClient.create(BinanceApiConstants.API_KEY,
				BinanceApiConstants.SECRET_KEY, options);		
		for (ExchangeInfoEntry resEntry : syncRequestClient.getExchangeInformation().getSymbols()) {
			
			// update
			if(mapBeforeEntry.keySet().contains(resEntry.getSymbol())) {
				TbBnFutureExchangeInfoEntry beforeEntry = mapBeforeEntry.get(resEntry.getSymbol());	
				beforeEntry.setSymbol(resEntry.getSymbol());
				beforeEntry.setStatus(resEntry.getStatus());
				beforeEntry.setMaintMarginPercent(resEntry.getMaintMarginPercent());
				beforeEntry.setRequiredMarginPercent(resEntry.getRequiredMarginPercent());
				beforeEntry.setBaseAsset(resEntry.getBaseAsset());
				beforeEntry.setQuoteAsset(resEntry.getQuoteAsset());
				beforeEntry.setPricePrecision(resEntry.getPricePrecision());
				beforeEntry.setQuantityPrecision(resEntry.getQuantityPrecision());
				beforeEntry.setBaseAsset(resEntry.getBaseAsset());
				beforeEntry.setOnboardDate(new Timestamp(resEntry.getOnboardDate()));
				beforeEntry.setOrderTypes(resEntry.getOrderTypes());
				beforeEntry.setTimeInForce(resEntry.getTimeInForce());		
				
			}else { // new
				TbBnFutureExchangeInfoEntry entry = new TbBnFutureExchangeInfoEntry();				
				entry.setPersisNew(true); 				
				entry.setSymbol(resEntry.getSymbol());
				entry.setStatus(resEntry.getStatus());
				entry.setMaintMarginPercent(resEntry.getMaintMarginPercent());
				entry.setRequiredMarginPercent(resEntry.getRequiredMarginPercent());
				entry.setBaseAsset(resEntry.getBaseAsset());
				entry.setQuoteAsset(resEntry.getQuoteAsset());
				entry.setPricePrecision(resEntry.getPricePrecision());
				entry.setQuantityPrecision(resEntry.getQuantityPrecision());
				entry.setBaseAsset(resEntry.getBaseAsset());
				entry.setOnboardDate(new Timestamp(resEntry.getOnboardDate()));
				entry.setOrderTypes(resEntry.getOrderTypes());
				entry.setTimeInForce(resEntry.getTimeInForce());
				entry.setPriceUseYn(false);
				entry.setRegDate(new Timestamp(System.currentTimeMillis()));
				entry.setRegId(regId);
				
				
				
				lstNewEntry.add(entry);
			}
		}		
		// 신규(Insert) Entry 처리
		tbBnFutureExchangeInfoEntryRepo.saveAll(lstNewEntry);		
		
		// 변경(Update) Entry처리
		List lstUpdatedEntry = tbBnFutureExchangeInfoEntryRepo.saveAll(lstBeforeEntry);		
		
		
		
		
		
		
		
		
		
		
		
//		tbBnFutureExchangeInfoEntryRepo.saveAll(lstAfterEntry);
		


		return null;
	}

}
