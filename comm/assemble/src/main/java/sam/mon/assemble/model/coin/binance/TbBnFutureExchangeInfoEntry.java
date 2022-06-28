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
import sam.mon.assemble.model.util.convert.BooleanToYNConverter;
import sam.mon.assemble.model.util.convert.StringArrayConverter;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class) // @CreatedDate, @LastModifiedDate
@Table(name = "tb_bn_future_exchang_info_entry")
public class TbBnFutureExchangeInfoEntry implements Persistable<String> {
	
    @Id    
    @Column(columnDefinition = "varchar(50) not null comment '종목명'")
    private String symbol;
    
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
    
    @Convert(converter = BooleanToYNConverter.class)
    @Column(columnDefinition = "char(1) comment '가격 사용 가능 유무'")
    private Boolean priceUseYn;

    @Column(columnDefinition = "timestamp comment '등록일시'")
    @CreatedDate
    private Timestamp regDate;
    
    @Column(columnDefinition = "timestamp comment '수정일시'")
    @LastModifiedDate
    private Timestamp corrDate;
    
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
	
	public boolean equalsApiVo(ExchangeInfoEntry eie) {
		if(!this.symbol.equals(eie.getSymbol())) {return false; }
		if(!this.status.equals(eie.getStatus())) {return false; }
		if(this.maintMarginPercent.compareTo(eie.getMaintMarginPercent())!=0) {return false; }
		if(this.requiredMarginPercent.compareTo(eie.getRequiredMarginPercent())!=0) {return false; }
		if(!this.baseAsset.equals(eie.getBaseAsset())) {return false; }
		if(this.baseAssetPrecision.compareTo(eie.getBaseAssetPrecision())!=0) {return false; }
		if(!this.quoteAsset.equals(eie.getQuoteAsset())) {return false; }
		if(this.pricePrecision.compareTo(eie.getPricePrecision())!=0) {return false; }
		if(this.quantityPrecision.compareTo(eie.getQuantityPrecision())!=0) {return false; }
		if(this.onboardDate.compareTo(new Timestamp(eie.getOnboardDate()))!=0) {return false; }
		if(!this.orderTypes.toString().equals(eie.getOrderTypes().toString())) {return false; }
		if(!this.timeInForce.toString().equals(eie.getTimeInForce().toString())) {return false; }
		
		return true;
	}

}
