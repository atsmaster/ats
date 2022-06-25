package sam.mon.assemble.model.coin;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import sam.mon.assemble.model.util.convert.BooleanToYNConverter;

@Data
@Entity
@Table(name = "tb_coin_future_entry")
public class TbCoinFutureEntry {
	
    @Id    
    @Column(columnDefinition = "varchar(50) not null comment '종목명'")
    private String symbol;    

    @Column(columnDefinition = "varchar(50) comment '기초자산'")
    private String baseAsset;

    @Column(columnDefinition = "varchar(50) comment '주문자산'")
    private String quoteAsset;
    
    @Column(columnDefinition = "timestamp comment '최초 상장일시'")
    private Timestamp initOnboardDate;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(columnDefinition = "char(1) comment '가격 사용 가능 유무'")
    private Boolean priceUseYn;

    @Column(columnDefinition = "timestamp comment '등록일시'")
    private Timestamp regDate;
    
    @Column(columnDefinition = "varchar(50) comment '등록ID'")
    private String regId;    
}
