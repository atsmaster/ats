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

import com.binance.client.RequestOptions;
import com.binance.client.SyncRequestClient;
import com.binance.client.constant.BinanceApiConstants;

import lombok.extern.slf4j.Slf4j;
import sam.mon.assemble.model.TbTest;
import sam.mon.assemble.model.binance.future.TbBnFutureCandleMin;
import sam.mon.assemble.model.binance.future.TbBnFutureCandleMinId;
import sam.mon.assemble.model.binance.future.TbBnFutureExchangeInfoEntry;
import sam.mon.assemble.repo.TbTestRepo;
import sam.mon.assemble.repo.binance.future.TbBnFutureCandleMinRepo;

@Slf4j
@Component
@StepScope
public class SampleTasklet implements Tasklet, StepExecutionListener{

	@Autowired
	TbTestRepo tbTestRepo;
	
	@Autowired
	TbBnFutureCandleMinRepo tbBinanceFutureCandleMinRepo;
	
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
		
		TbTest tt = new TbTest();
		tt.setTbTestStr("hihihi");
		tbTestRepo.save(tt);	
		
		TbBnFutureCandleMin tbfcm = new TbBnFutureCandleMin();		
		TbBnFutureCandleMinId tbfcmId = new TbBnFutureCandleMinId();
		tbfcmId.setOpenTime(1655560200000l);
		tbfcmId.setSymbol("BTCUSDT");
		tbfcm.setTbBinanceFutureCandleMinId(tbfcmId);
		tbfcm.setCloseTime(1655560259999l);		
		tbBinanceFutureCandleMinRepo.save(tbfcm);
		
		
		TbBnFutureExchangeInfoEntry tbfeie = new TbBnFutureExchangeInfoEntry();
		

        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(BinanceApiConstants.API_KEY, BinanceApiConstants.SECRET_KEY,
                options);
        System.out.println(syncRequestClient.getExchangeInformation());
        
        
		return null;
	}

}
