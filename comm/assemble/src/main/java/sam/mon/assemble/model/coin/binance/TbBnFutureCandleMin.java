package sam.mon.assemble.model.coin.binance;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_bn_future_candle_min")
public class TbBnFutureCandleMin {

    @EmbeddedId
//	private String symbol;
//  private Long openTime;
	private TbBnFutureCandleMinId tbBinanceFutureCandleMinId;	

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

    @Column(columnDefinition = "timestamp comment '등록일시'")
    private Timestamp regDd;
    
    @Column(columnDefinition = "varchar(50) comment '등록ID'")
    private String regId;

    // ---------------------------------------------------------------
    
    public TbBnFutureCandleMin() {
    	this.tbBinanceFutureCandleMinId = new TbBnFutureCandleMinId();
    }
    
    
    public String getSymbol() {
    	return tbBinanceFutureCandleMinId.getSymbol();
    }
    
    public void setSymbol(String symbol) {
    	tbBinanceFutureCandleMinId.setSymbol(symbol);
    }
    
    public Timestamp getTimeOpen() {
    	return tbBinanceFutureCandleMinId.getTimeOpen();
    }

    
    public void setTimeOpen(Timestamp timeOpen) {
    	tbBinanceFutureCandleMinId.setTimeOpen(timeOpen);
    }
    
    
}
