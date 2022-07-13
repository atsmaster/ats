package sam.mon.assemble.api.coin.binance.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.binance.client.model.market.Candlestick;
import com.binance.client.model.market.ExchangeInfoEntry;

import sam.mon.assemble.api.coin.binance.ApiRequest;
import sam.mon.assemble.api.coin.binance.BnFutureApi;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandleOneMin;
import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;
import sam.mon.assemble.model.enums.CandleInterval;

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
	public List<TbBnFutureCandleOneMin> getCandle(String symbol, CandleInterval interval, Long startTime, Long endTime, Integer limit) {
		List<TbBnFutureCandleOneMin> lstResCandle = new ArrayList<TbBnFutureCandleOneMin>(); 
		for(Candlestick cd : bnFutureApi.syncRequestClient().getCandlestick(symbol, interval.getCandlestickInterval(), startTime, endTime, limit)) {
			TbBnFutureCandleOneMin tbfcm = new TbBnFutureCandleOneMin(symbol, cd);
			lstResCandle.add(tbfcm);
		}		
		return lstResCandle;
	}			
}
