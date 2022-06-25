package sam.mon.assemble.model.coin;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class TbCoinFutureEntryHistId implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "varchar(50) not null comment '종목명'")
	private String symbol;

	@Column(columnDefinition = "timestamp not null comment '종목 정보 변경 일시'")
	private Timestamp symbolInfoChgDate;  
}
