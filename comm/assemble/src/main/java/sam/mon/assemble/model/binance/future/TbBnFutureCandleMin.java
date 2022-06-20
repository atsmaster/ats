package sam.mon.assemble.model.binance.future;

import java.math.BigDecimal;

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
	

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private BigDecimal volume;

    private Long closeTime;

    private BigDecimal quoteAssetVolume;

    private Integer numTrades;

    private BigDecimal takerBuyBaseAssetVolume;

    private BigDecimal takerBuyQuoteAssetVolume;
}
