package sam.mon.assemble.model.binance.future;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import sam.mon.assemble.model.util.StringArrayConverter;

@Data
@Entity
@Table(name = "tb_bn_future_exchang_info_entry")
public class TbBnFutureExchangeInfoEntry {
	
    @Id    
    private String symbol;

    private String status;

    private BigDecimal maintMarginPercent;

    private BigDecimal requiredMarginPercent;

    private String baseAsset;

    private String quoteAsset;

    private Long pricePrecision;

    private Long quantityPrecision;

    private Long baseAssetPrecision;

    private Long quotePrecision;

    @Convert(converter = StringArrayConverter.class)
    private List<String> orderTypes;

    @Convert(converter = StringArrayConverter.class)
    private List<String> timeInForce;

//    private List<List<Map<String, String>>> filters;
}
