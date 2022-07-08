package sam.mon.assemble.model.coin.binance;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // @CreatedDate, @LastModifiedDate
public abstract class TbBnFutureCandle {	

    @EmbeddedId
	private TbBnFutureCandleId tbBinanceFutureCandleId;	

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

    @Column(columnDefinition = "decimal(30,10) comment '주문자산 거래량'")
    private BigDecimal quoteAssetVolume;

    @Column(columnDefinition = "decimal(30,10) comment '주문수'")
    private Integer numTrades;

    @Column(columnDefinition = "decimal(30,10) comment '기초자산 시장가 매수 거래량'")
    private BigDecimal takerBuyBaseAssetVolume;

    @Column(columnDefinition = "decimal(30,10) comment '주문자산 시장가 매수 거래량'")
    private BigDecimal takerBuyQuoteAssetVolume;
    
    // --------------------------------
	
    @Column(columnDefinition = "timestamp comment '등록일시'")
    @CreatedDate
    private Timestamp regDate;
    
    @Column(columnDefinition = "timestamp comment '수정일시'")
    @LastModifiedDate
    private Timestamp corrDate;
    
  // ------------------------------- ID getter, setter    
  
  public String getSymbol() {
  	return tbBinanceFutureCandleId.getSymbol();
  }
  
  public void setSymbol(String symbol) {
  	tbBinanceFutureCandleId.setSymbol(symbol);
  }
  
  public Timestamp getTimeOpen() {
  	return tbBinanceFutureCandleId.getTimeOpen();
  }

  
  public void setTimeOpen(Timestamp timeOpen) {
  	tbBinanceFutureCandleId.setTimeOpen(timeOpen);
  }
}
