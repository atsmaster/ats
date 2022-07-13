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
public class TbBnFutureCandleId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String symbol;	
	private String timeEnumTp;
	private Timestamp timeOpen; // jpa 복합키는 알파벳 순서로 생성되기 때문에 symbol 보다 늦은순서인 timeopen으로 수정
	
	
}
