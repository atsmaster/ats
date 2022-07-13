package sam.mon.assemble.model.coin.binance;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.binance.client.model.market.ExchangeInfoEntry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import sam.mon.assemble.model.util.convert.BooleanToYNConverter;
import sam.mon.assemble.model.util.convert.StringArrayConverter;

@Data
@Entity
@Table(name = "tb_bn_future_exchang_info_entry")
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class) // @CreatedDate, @LastModifiedDate
public class TbBnFutureExchangeInfoEntry implements Persistable<String> {
	
    @Id    
    @Column(columnDefinition = "varchar(50) comment '종목명'")
    private String symbol;
    
    @Column(columnDefinition = "varchar(50) comment '거래상태'")
    private String status;

    @Column(columnDefinition = "decimal(7,4) comment '유지 증거금률(%)'")
	@EqualsAndHashCode.Exclude
    private BigDecimal maintMarginPercent;

    @Column(columnDefinition = "decimal(7,4) comment '요구 증거금률(%)'")
	@EqualsAndHashCode.Exclude
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
    
    @EqualsAndHashCode.Include
    private BigDecimal bdEq1() { // @EqualsAndHashCode 사용시 Bigdecimal 예외처리
        return maintMarginPercent.stripTrailingZeros();    
    }

    @EqualsAndHashCode.Include
    private BigDecimal bdEq2() { // @EqualsAndHashCode 사용시 Bigdecimal 예외처리
        return requiredMarginPercent.stripTrailingZeros();    
    }    
    
    // ------------------------------ 비교 속성 제외 (이하)
    
    @Convert(converter = BooleanToYNConverter.class)
    @Column(columnDefinition = "char(1) comment '거래소 상장 유무'")
    @Exclude
    private Boolean listYn;
    
    @Convert(converter = BooleanToYNConverter.class)
    @Column(columnDefinition = "char(1) comment '가격 사용 가능 유무'")
    @Exclude
    private Boolean priceUseYn;

    @Column(columnDefinition = "timestamp comment '등록일시'")
    @Exclude
    @CreatedDate
    private Timestamp regDate;
    
    @Column(columnDefinition = "timestamp comment '수정일시'")
    @Exclude
    @LastModifiedDate
    private Timestamp corrDate;
    
    // ------------------------------ 영속성 관리를 위함

    @Exclude
    private boolean persisNew;
    
	@Override
	public String getId() {
		return symbol;
	}

	@Override
	public boolean isNew() {
		return persisNew;
	}    
	
	public void setNew(boolean b) {
		this.persisNew = b;	
	}
	
	// -------------------------------

	public TbBnFutureExchangeInfoEntry() {}
	public TbBnFutureExchangeInfoEntry(ExchangeInfoEntry exchangeInfoEntry) {
		this.symbol = exchangeInfoEntry.getSymbol();
		this.status = exchangeInfoEntry.getStatus();
		this.maintMarginPercent = exchangeInfoEntry.getMaintMarginPercent();
		this.requiredMarginPercent = exchangeInfoEntry.getRequiredMarginPercent();
		this.baseAsset = exchangeInfoEntry.getBaseAsset();
		this.baseAssetPrecision = exchangeInfoEntry.getBaseAssetPrecision();
		this.quoteAsset = exchangeInfoEntry.getQuoteAsset();
		this.pricePrecision = exchangeInfoEntry.getPricePrecision();
		this.quantityPrecision = exchangeInfoEntry.getQuantityPrecision();
		this.onboardDate = new Timestamp(exchangeInfoEntry.getOnboardDate());
		this.orderTypes = exchangeInfoEntry.getOrderTypes();
		this.timeInForce = exchangeInfoEntry.getTimeInForce();		
	}
}
