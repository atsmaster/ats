package sam.mon.assemble.api.coin.binance.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.binance.client.model.market.Candlestick;
import com.binance.client.model.market.ExchangeInfoEntry;

import sam.mon.assemble.api.coin.binance.ApiRequest;
import sam.mon.assemble.api.coin.binance.BnFutureApi;
import sam.mon.assemble.model.CandleInterval;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandle;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandleMin;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;

@Component
public class ApiRequestImpl implements ApiRequest{

	@Autowired
	BnFutureApi bnFutureApi;
	
	public List<TbBnFutureExchangeInfoEntry> getExchangeInformation() {		
		List<TbBnFutureExchangeInfoEntry> lstResEntry = new ArrayList<TbBnFutureExchangeInfoEntry>();
		for(ExchangeInfoEntry eie : bnFutureApi.syncRequestClient().getExchangeInformation().getSymbols()) {
			TbBnFutureExchangeInfoEntry tbBnFutureExchangeInfoEntry = new TbBnFutureExchangeInfoEntry(eie);
			lstResEntry.add(tbBnFutureExchangeInfoEntry);
		}
		return lstResEntry;
	}

	@Override
	public List<TbBnFutureCandle> getCandle(String symbol, CandleInterval interval, Long startTime, Long endTime, Integer limit) {
//		List<TbBnFutureCandleMinn> lstResCandle = new ArrayList<TbBnFutureCandleMinn>();
		
		
		List<TbBnFutureCandle> lstResCandle = new ArrayList<TbBnFutureCandle>(); 
		for(Candlestick cd : bnFutureApi.syncRequestClient().getCandlestick(symbol, interval.getCandlestickInterval(), startTime, endTime, limit)) {
			TbBnFutureCandle tbfcm = new TbBnFutureCandleMin(symbol, cd);
			lstResCandle.add(tbfcm);
		}		
		return lstResCandle;
	}			
}
