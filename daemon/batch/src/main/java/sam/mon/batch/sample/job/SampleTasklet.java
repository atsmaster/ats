package sam.mon.batch.sample.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sam.mon.assemble.repo.TbTestRepo;
import sam.mon.assemble.repo.coin.binance.TbBnFutureCandleMinRepo;
import sam.mon.assemble.repo.coin.binance.TbBnFutureExchangeInfoEntryHistRepo;
import sam.mon.assemble.repo.coin.binance.TbBnFutureExchangeInfoEntryRepo;

@Slf4j
@Component
@StepScope
public class SampleTasklet implements Tasklet, StepExecutionListener{

	@Autowired
	TbTestRepo tbTestRepo;
	
	@Autowired
	TbBnFutureCandleMinRepo tbBinanceFutureCandleMinRepo;
	
	@Autowired
	TbBnFutureExchangeInfoEntryRepo tbBnFutureExchangeInfoEntryRepo;
	
	@Autowired
	TbBnFutureExchangeInfoEntryHistRepo tbBnFutureExchangeInfoEntryHistRepo;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		
//		TbTest tt = new TbTest();
//		tt.setTbTestStr("hihihi");
//		tbTestRepo.save(tt);	
//		
//		TbBnFutureCandleMin tbfcm = new TbBnFutureCandleMin();		
//		TbBnFutureCandleMinId tbfcmId = new TbBnFutureCandleMinId();
//		tbfcmId.setOpenTime(1655560200000l);
//		tbfcmId.setSymbol("BTCUSDT");
//		tbfcm.setTbBinanceFutureCandleMinId(tbfcmId);
//		tbfcm.setCloseTime(1655560259999l);		
//		tbBinanceFutureCandleMinRepo.save(tbfcm);
//		
//		
//		TbBnFutureExchangeInfoEntry tbfeie = new TbBnFutureExchangeInfoEntry();
//		
//
//        RequestOptions options = new RequestOptions();
//        SyncRequestClient syncRequestClient = SyncRequestClient.create(BinanceApiConstants.API_KEY, BinanceApiConstants.SECRET_KEY,
//                options);
//        System.out.println(syncRequestClient.getExchangeInformation());
        

//        RequestOptions options = new RequestOptions();
//        SyncRequestClient syncRequestClient = SyncRequestClient.create(BinanceApiConstants.API_KEY, BinanceApiConstants.SECRET_KEY,
//                options);
//        
//        List<TbBnFutureExchangeInfoEntry> lstTbfeie = new ArrayList<TbBnFutureExchangeInfoEntry>();
//        for(ExchangeInfoEntry eie : syncRequestClient.getExchangeInformation().getSymbols()) {
//        	TbBnFutureExchangeInfoEntry tbBnFutureExchangeInfoEntry = new TbBnFutureExchangeInfoEntry();
//        	tbBnFutureExchangeInfoEntry.setSymbol(eie.getSymbol());
//        	tbBnFutureExchangeInfoEntry.setStatus(eie.getStatus());
//        	tbBnFutureExchangeInfoEntry.setMaintMarginPercent(eie.getMaintMarginPercent());
//        	tbBnFutureExchangeInfoEntry.setRequiredMarginPercent(eie.getRequiredMarginPercent());
//        	tbBnFutureExchangeInfoEntry.setBaseAsset(eie.getBaseAsset());
//        	tbBnFutureExchangeInfoEntry.setQuoteAsset(eie.getQuoteAsset());
//        	tbBnFutureExchangeInfoEntry.setPricePrecision(eie.getPricePrecision());
//        	tbBnFutureExchangeInfoEntry.setQuantityPrecision(eie.getQuantityPrecision());
//        	tbBnFutureExchangeInfoEntry.setBaseAsset(eie.getBaseAsset());        	
//        	tbBnFutureExchangeInfoEntry.setOnboardDate(new Timestamp(eie.getOnboardDate()));
//        	tbBnFutureExchangeInfoEntry.setOrderTypes(eie.getOrderTypes());
//        	tbBnFutureExchangeInfoEntry.setTimeInForce(eie.getTimeInForce());
//
//        	tbBnFutureExchangeInfoEntry.setPriceUseYn(true);        	
//        	lstTbfeie.add(tbBnFutureExchangeInfoEntry);       	
//        	
//        	
//        	break;
//        }
//        tbBnFutureExchangeInfoEntryRepo.saveAll(lstTbfeie);
        
        
//        Optional<TbBnFutureExchangeInfoEntry> tbb = tbBnFutureExchangeInfoEntryRepo.findById("1000LUNCBUSD");        
//        tbb.get().setStatus("NOT_TRD");
//        tbBnFutureExchangeInfoEntryRepo.save(tbb.get());
        
//		TbBnFutureExchangeInfoEntry eie2 = new TbBnFutureExchangeInfoEntry();
//        eie2.setSymbol("BTC22");
//        eie2.setStatus("TRADING");        
//        tbBnFutureExchangeInfoEntryRepo.save(eie2);

//		TbBnFutureExchangeInfoEntry eie3 = new TbBnFutureExchangeInfoEntry();
//        eie2.setSymbol("BTC22");
//        eie2.setStatus("Not_TRADING");    
//        tbBnFutureExchangeInfoEntryRepo.save(eie2);

        
		return null;
	}

}
