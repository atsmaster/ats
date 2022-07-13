package sam.mon.assemble.api.coin.binance;

import java.sql.Timestamp;
import java.util.List;

import sam.mon.assemble.model.coin.binance.TbBnFutureCandle;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;
import sam.mon.assemble.model.enums.CandleInterval;

public interface ApiRequest {

	List<TbBnFutureExchangeInfoEntry> getExchangeInformation();
	
	List<TbBnFutureCandle> getCandle(String symbol, CandleInterval interval, Timestamp startTime, Timestamp endTime, Integer limit);
}
