package sam.mon.assemble.api.coin.binance.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.binance.client.model.market.ExchangeInfoEntry;

import sam.mon.assemble.api.coin.binance.ApiRequest;
import sam.mon.assemble.api.coin.binance.BnFutureApi;
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
}
