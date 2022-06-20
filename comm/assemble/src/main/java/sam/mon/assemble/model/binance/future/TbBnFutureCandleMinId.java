package sam.mon.assemble.model.binance.future;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class TbBnFutureCandleMinId implements Serializable {
	
	private String symbol;
	private Long openTime;
}
