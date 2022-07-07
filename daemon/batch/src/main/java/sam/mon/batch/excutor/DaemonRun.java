package sam.mon.batch.excutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import sam.mon.assemble.api.coin.binance.BnFutureApi;
import sam.mon.batch.utils.security.Aes256Util;


@Component
public class DaemonRun implements ApplicationListener<ApplicationStartedEvent>{

	@Autowired
	BnFutureApi bnFutureApi;
	

	@Value("${ats.apikey.bn.future}")
	private String apikey;
	

	@Value("${ats.scrkey.bn.future}")
	private String scrKey;

	
	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		
		// Binance Future
		Aes256Util aes = Aes256Util.getInstance();
		String encApiKey = aes.AES_Decode(apikey);
		String encscrKey = aes.AES_Decode(scrKey);
		bnFutureApi.setBnFutureApi(encApiKey, encscrKey);
	}
}
