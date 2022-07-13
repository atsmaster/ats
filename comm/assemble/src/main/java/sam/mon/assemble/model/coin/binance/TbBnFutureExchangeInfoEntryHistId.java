package sam.mon.assemble.model.coin.binance;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbBnFutureExchangeInfoEntryHistId implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String symbol;
	private Timestamp symbolInfoChgDate;
}
