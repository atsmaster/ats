package sam.mon.assemble.model.binance.future;

import java.math.BigDecimal;

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
	TbBnFutureCandleMinId tbBinanceFutureCandleMinId;	

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
}
