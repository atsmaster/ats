package sam.mon.batch.collect.coin.step;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

		RequestOptions options = new RequestOptions();
		SyncRequestClient syncRequestClient = SyncRequestClient.create(BinanceApiConstants.API_KEY,
				BinanceApiConstants.SECRET_KEY, options);

		List<TbBnFutureExchangeInfoEntry> lstAfterEntry = new ArrayList<TbBnFutureExchangeInfoEntry>();

		
		List<TbBnFutureExchangeInfoEntry> lstBeforeEntry = tbBnFutureExchangeInfoEntryRepo.findAll();		

		for (ExchangeInfoEntry resEntry : syncRequestClient.getExchangeInformation().getSymbols()) {
			TbBnFutureExchangeInfoEntry entry = new TbBnFutureExchangeInfoEntry();
			
			entry.setSymbol(resEntry.getSymbol());
			entry.setStatus(resEntry.getStatus());
			entry.setMaintMarginPercent(resEntry.getMaintMarginPercent());
			entry.setRequiredMarginPercent(resEntry.getRequiredMarginPercent());
			entry.setBaseAsset(resEntry.getBaseAsset());
			entry.setQuoteAsset(resEntry.getQuoteAsset());
			entry.setPricePrecision(resEntry.getPricePrecision());
			entry.setQuantityPrecision(resEntry.getQuantityPrecision());
			entry.setBaseAsset(entry.getBaseAsset());
			entry.setOnboardDate(new Timestamp(resEntry.getOnboardDate()));
			entry.setOrderTypes(resEntry.getOrderTypes());
			entry.setTimeInForce(resEntry.getTimeInForce());
			// 비교항목 제외
			entry.setPriceUseYn(true);
			entry.setRegDate(Timestamp.valueOf("2020-12-12 01:24:25"));
			entry.setRegId(regId);			
			
			lstAfterEntry.add(entry);
		}
		
		tbBnFutureExchangeInfoEntryRepo.saveAll(lstAfterEntry);

		return null;
	}

}
