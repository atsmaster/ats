package sam.mon.batch.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sam.mon.batch.utils.security.Aes256Util;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:application.properties"}) // to-be : create test properties 
public class Aes256UtilTest {

	
	@Test
	public void Test(){

		Aes256Util aes = Aes256Util.getInstance();
		String aesApiKey = aes.AES_Encode("");
		String aesScrKey = aes.AES_Encode("");
		

		System.out.println("aesApiKey : " + aesApiKey);
		System.out.println("aesScrKey : " + aesScrKey);
	}
	
}
