package sam.mon.assemble.model.binance.future;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class TbBnFutureCandleMinId implements Serializable {
	
	@Column(columnDefinition = "varchar(50) not null comment '종목명'")
	private String symbol;
	
	@Column(columnDefinition = "timestamp comment '시작일시'")
	private Timestamp openTime;
}
