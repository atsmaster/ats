package sam.mon.assemble.model.coin.binance;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class TbBnFutureCandleMinId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "varchar(50) comment '종목명'")
	private String symbol;
	
	@Column(columnDefinition = "timestamp comment '시작일시'")
	private Timestamp timeOpen; // jpa 복합키는 알파벳 순서로 생성되기 때문에 symbol 보다 늦은순서인 timeopen으로 수정
	
	
}
