package sam.mon.batch.utils.property;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PropertyUtil {
    
    @Autowired
    private Environment loader;
    private static Environment sloader;
    
    @PostConstruct
    public void registerInstance() {
    	PropertyUtil.sloader = loader;
    }

    public static String getProperty(String key){    	
        return sloader.getProperty(key);
    }
}
