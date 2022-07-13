package sam.mon.assemble.api.coin.binance;

import java.util.List;

import sam.mon.assemble.model.coin.binance.TbBnFutureCandleOneMin;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;
import sam.mon.assemble.model.enums.CandleInterval;

public interface ApiRequest {

	List<TbBnFutureExchangeInfoEntry> getExchangeInformation();
	
	List<TbBnFutureCandleOneMin> getCandle(String symbol, CandleInterval interval, Long startTime, Long endTime, Integer limit);
}
