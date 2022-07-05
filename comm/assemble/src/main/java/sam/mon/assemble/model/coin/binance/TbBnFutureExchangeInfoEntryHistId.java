package sam.mon.assemble.model.coin.binance;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Embeddable
@Data
public class TbBnFutureExchangeInfoEntryHistId implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "varchar(50) comment '종목명'")
	private String symbol;

	@CreatedDate
	@Column(columnDefinition = "timestamp comment '종목 정보 변경 일시'")    
	private Timestamp symbolInfoChgDate;
}
