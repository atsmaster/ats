package sam.mon.assemble.model.coin.binance;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Data;
import sam.mon.assemble.model.util.convert.StringArrayConverter;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class) 
@Table(name = "tb_bn_future_exchang_info_entry_hist")
public class TbBnFutureExchangeInfoEntryHist implements Persistable<TbBnFutureExchangeInfoEntryHistId> {
	
    @EmbeddedId    		
    TbBnFutureExchangeInfoEntryHistId tbBnFutureExchangeInfoEntryHistId;	
	
    @Column(columnDefinition = "varchar(50) comment '거래상태'")
    private String status;

    @Column(columnDefinition = "decimal(7,4) comment '유지 증거금률(%)'")
    private BigDecimal maintMarginPercent;

    @Column(columnDefinition = "decimal(7,4) comment '요구 증거금률(%)'")
    private BigDecimal requiredMarginPercent;

    @Column(columnDefinition = "varchar(50) comment '기초자산'")
    private String baseAsset;

    @Column(columnDefinition = "bigint(10) comment '기초자산 가격 소수점 자리'")
    private Long baseAssetPrecision;
    
    @Column(columnDefinition = "varchar(50) comment '주문자산'")
    private String quoteAsset;
    
    @Column(columnDefinition = "bigint(10) comment '주문자산 가격 소수점 자리'")
    private Long pricePrecision;

    @Column(columnDefinition = "bigint(10) comment '주문자산 거래량 소수점 자리'")
    private Long quantityPrecision;

    @Column(columnDefinition = "timestamp comment '상장일시'")
    private Timestamp onboardDate;

    @Convert(converter = StringArrayConverter.class)
    @Column(columnDefinition = "varchar(255) comment '주문가능 유형'")
    private List<String> orderTypes;

    @Convert(converter = StringArrayConverter.class)
    @Column(columnDefinition = "varchar(255) comment '주문실행 계획'")
    private List<String> timeInForce;    

    // ------------------------------ 비교 속성 제외
    
//    @Convert(converter = BooleanToYNConverter.class)
//    @Column(columnDefinition = "char(1) comment '가격 사용 가능 유무'")
//    private Boolean priceUseYn;

    @Column(columnDefinition = "timestamp comment '등록일시'")
    private Timestamp regDate;
    
    @Column(columnDefinition = "varchar(50) comment '등록ID'")
    private String regId;    
    

    private boolean persisNew;
    
	@Override
	public TbBnFutureExchangeInfoEntryHistId getId() {
		return tbBnFutureExchangeInfoEntryHistId;
	}

	@Override
	public boolean isNew() {
		return persisNew;
	}    
	
	public void setNew(boolean b) {
		this.persisNew = b;
	
	}    
	
	public TbBnFutureExchangeInfoEntryHist() {};

	@Builder
	public TbBnFutureExchangeInfoEntryHist(String symbol, Timestamp symbolInfoChgDate, String status,  BigDecimal maintMarginPercent, BigDecimal requiredMarginPercent, String baseAsset, Long baseAssetPrecision,
			String quoteAsset, Long pricePrecision, Long quantityPrecision, Timestamp onboardDate, List<String> orderTypes, List<String> timeInForce) {
		
		TbBnFutureExchangeInfoEntryHistId tbBnFutureExchangeInfoEntryHistId = new TbBnFutureExchangeInfoEntryHistId();
		tbBnFutureExchangeInfoEntryHistId.setSymbol(symbol);
		tbBnFutureExchangeInfoEntryHistId.setSymbolInfoChgDate(symbolInfoChgDate);		
		
		this.tbBnFutureExchangeInfoEntryHistId = tbBnFutureExchangeInfoEntryHistId;
		this.status = status;
		this.maintMarginPercent = maintMarginPercent;
		this.requiredMarginPercent = requiredMarginPercent;
		this.baseAsset = baseAsset;
		this.baseAssetPrecision = baseAssetPrecision;
		this.quoteAsset = quoteAsset;
		this.pricePrecision = pricePrecision;
		this.quantityPrecision = quantityPrecision;
		this.onboardDate = onboardDate;
		this.orderTypes = orderTypes;
		this.timeInForce = timeInForce;		
	}
}
