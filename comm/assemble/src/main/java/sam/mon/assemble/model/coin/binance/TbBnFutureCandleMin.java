package sam.mon.assemble.model.coin.binance;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.binance.client.model.market.Candlestick;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_bn_future_candle_min")
public class TbBnFutureCandleMin extends TbBnFutureCandle{
	
	public TbBnFutureCandleMin() {};
	
    public TbBnFutureCandleMin(String symbol, Candlestick candlestick) {
    	super.setTbBinanceFutureCandleId(new TbBnFutureCandleId());     	
    	super.getTbBinanceFutureCandleId().setSymbol(symbol);
    	super.getTbBinanceFutureCandleId().setTimeOpen(new Timestamp(candlestick.getOpenTime()));
    	super.setOpen(candlestick.getOpen()); 
    	super.setHigh(candlestick.getHigh()); 
    	super.setLow(candlestick.getLow()); 
    	super.setClose(candlestick.getClose()); 
    	super.setVolume(candlestick.getVolume()); 
    	super.setQuoteAssetVolume(candlestick.getQuoteAssetVolume()); 
    	super.setNumTrades(candlestick.getNumTrades()); 
    	super.setTakerBuyBaseAssetVolume(candlestick.getTakerBuyBaseAssetVolume());
    	super.setTakerBuyQuoteAssetVolume(candlestick.getTakerBuyQuoteAssetVolume());
    }
}
