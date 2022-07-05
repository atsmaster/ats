package sam.mon.batch;



import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import sam.mon.assemble.model.TbTest;

@RunWith(SpringRunner.class)
public class JustTest {
	
	@Test
	public void testSum(){
		TbTest tt = new TbTest();
		tt.setTbTestBd(new BigDecimal("1.01"));
		tt.setExclueField("1");
		
		TbTest tt2 = new TbTest();
		tt2.setTbTestBd(new BigDecimal("1.00"));
		tt2.setExclueField("2");
		
		
		System.out.println(tt.equals(tt2)); 
		
		
		
	}


}
