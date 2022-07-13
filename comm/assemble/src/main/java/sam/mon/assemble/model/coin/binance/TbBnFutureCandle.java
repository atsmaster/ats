package sam.mon.assemble.model.coin.binance;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.binance.client.model.market.Candlestick;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_bn_future_candle")
@IdClass(TbBnFutureCandleId.class)
@EntityListeners(AuditingEntityListener.class) // @CreatedDate, @LastModifiedDate
public class TbBnFutureCandle implements Persistable<TbBnFutureCandleId>{
	
	@Id
	@Column(columnDefinition = "varchar(50) comment '종목명'")
	private String symbol;

	@Id
	@Column(columnDefinition = "varchar(3) comment '시간 타입'")
	private String timeEnumTp;
	
	@Id	
	@Column(columnDefinition = "timestamp comment '시작일시'")
	private Timestamp timeOpen; // jpa 복합키는 알파벳 순서로 생성되기 때문에 symbol 보다 늦은순서인 timeopen으로 수정

    @Column(columnDefinition = "decimal(30,10) comment '시가'")    
    private BigDecimal open;

    @Column(columnDefinition = "decimal(30,10) comment '고가'")
    private BigDecimal high;

    @Column(columnDefinition = "decimal(30,10) comment '저가'")
    private BigDecimal low;

    @Column(columnDefinition = "decimal(30,10) comment '종가'")
    private BigDecimal close;

    @Column(columnDefinition = "decimal(30,10) comment '기초자산 거래량'")
    private BigDecimal volume;

    @Column(columnDefinition = "decimal(30,10) comment '주문수'")
    private Integer numTrades;
    
    // --------------------------------
	
    @Column(columnDefinition = "timestamp comment '등록일시'")
    @CreatedDate
    private Timestamp regDate;
    
    @Column(columnDefinition = "timestamp comment '수정일시'")
    @LastModifiedDate
    private Timestamp corrDate;    

    
    // ------------------------------ 영속성 관리를 위함
	@Override
	public boolean isNew() {
		return true;
	}
	
	@Override
	public TbBnFutureCandleId getId() {
		return TbBnFutureCandleId.builder()
				.symbol(symbol)
				.timeEnumTp(timeEnumTp)
				.timeOpen(timeOpen).build();	
	}	
	
    public TbBnFutureCandle(String symbol, Candlestick candlestick) {
    	this.symbol = symbol;
    	this.timeOpen = new Timestamp(candlestick.getOpenTime());		
    	this.open = candlestick.getOpen();
    	this.high = candlestick.getHigh();
    	this.low = candlestick.getLow();
    	this.close = candlestick.getClose();
    	this.volume = candlestick.getVolume();
    	this.numTrades = candlestick.getNumTrades();
    }
}
