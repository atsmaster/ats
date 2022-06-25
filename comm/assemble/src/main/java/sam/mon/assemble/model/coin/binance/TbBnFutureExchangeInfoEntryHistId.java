package sam.mon.assemble.model.coin.binance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class TbBnFutureExchangeInfoEntryHistId implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "varchar(50) not null comment '종목명'")
	private String symbol;

	@Column(columnDefinition = "bigint(10) not null comment '종목 정보 변경 일시'")
	private Long symbolInfoChgDate;
}
