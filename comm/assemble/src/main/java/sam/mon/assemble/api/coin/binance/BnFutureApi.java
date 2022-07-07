package sam.mon.assemble.api.coin.binance;

import org.springframework.stereotype.Component;

import com.binance.client.RequestOptions;
import com.binance.client.SyncRequestClient;

@Component
public class BnFutureApi {

	private String API_KEY;
	private String SCR_KEY;	
	private RequestOptions options;
	
	public void setBnFutureApi(String apiKey, String scrKey) {
		API_KEY = apiKey;
		SCR_KEY = scrKey;		
		options = new RequestOptions();
	}
	
	public SyncRequestClient syncRequestClient() {
		return SyncRequestClient.create(API_KEY, SCR_KEY,  options);
	}
}
