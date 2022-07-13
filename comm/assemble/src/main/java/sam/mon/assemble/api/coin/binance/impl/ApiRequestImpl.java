package sam.mon.assemble.api.coin.binance.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.binance.client.model.enums.CandlestickInterval;
import com.binance.client.model.market.Candlestick;
import com.binance.client.model.market.ExchangeInfoEntry;

import sam.mon.assemble.api.coin.binance.ApiRequest;
import sam.mon.assemble.api.coin.binance.BnFutureApi;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandle;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;
import sam.mon.assemble.model.enums.CandleInterval;

@Component
public class ApiRequestImpl implements ApiRequest {

	@Autowired
	BnFutureApi bnFutureApi;

	public List<TbBnFutureExchangeInfoEntry> getExchangeInformation() {
		List<TbBnFutureExchangeInfoEntry> lstResEntry = new ArrayList<TbBnFutureExchangeInfoEntry>();
		for (ExchangeInfoEntry eie : bnFutureApi.syncRequestClient().getExchangeInformation().getSymbols()) {
			TbBnFutureExchangeInfoEntry tbBnFutureExchangeInfoEntry = new TbBnFutureExchangeInfoEntry(eie);
			lstResEntry.add(tbBnFutureExchangeInfoEntry);
		}
		return lstResEntry;
	}

	@Override
	public List<TbBnFutureCandle> getCandle(String symbol, CandleInterval interval, Timestamp startTime, Timestamp endTime, Integer limit) {
		List<TbBnFutureCandle> lstResCandle = new ArrayList<TbBnFutureCandle>();
		for (Candlestick cd : bnFutureApi.syncRequestClient().getCandlestick(symbol, mapToBnCandleInterval(interval), startTime.getTime(), endTime.getTime(), limit)) {
			TbBnFutureCandle tbfcm = new TbBnFutureCandle(symbol, cd);
			tbfcm.setTimeEnumTp(interval.getCode());
			lstResCandle.add(tbfcm);
		}
		return lstResCandle;
	}

	public CandlestickInterval mapToBnCandleInterval(CandleInterval ci) {

		switch (ci.getCode()) {
		case "1m":
			return CandlestickInterval.ONE_MINUTE;
		case "5m":
			return CandlestickInterval.FIVE_MINUTES;
		default:
			return null;
		}		
	}
}
