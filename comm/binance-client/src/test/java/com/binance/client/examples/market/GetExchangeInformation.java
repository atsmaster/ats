package com.binance.client.examples.market;

import java.util.List;

import com.binance.client.RequestOptions;
import com.binance.client.SyncRequestClient;
import com.binance.client.examples.constants.PrivateConfig;
import com.binance.client.model.market.ExchangeInfoEntry;
import com.binance.client.model.market.ExchangeInformation;

public class GetExchangeInformation {
    public static void main(String[] args) {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(syncRequestClient.getExchangeInformation());
        
        ExchangeInformation ei = syncRequestClient.getExchangeInformation();
        List<ExchangeInfoEntry> lstEie = ei.getSymbols();
       
    }
}
