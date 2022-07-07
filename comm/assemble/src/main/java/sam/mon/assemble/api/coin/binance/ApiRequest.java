package sam.mon.assemble.api.coin.binance;

import java.util.List;

import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;

public interface ApiRequest {

	List<TbBnFutureExchangeInfoEntry> getExchangeInformation();
}
