package com.binance.client.model.market;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.binance.client.constant.BinanceApiConstants;

import lombok.Data;

@Data
public class ExchangeInfoEntry {

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

    public Long onboardDate;

    private List<String> orderTypes;

    private List<String> timeInForce;

    private List<List<Map<String, String>>> filters;


    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE).append("symbol", symbol)
                .append("status", status).append("maintMarginPercent", maintMarginPercent)
                .append("requiredMarginPercent", requiredMarginPercent).append("baseAsset", baseAsset)
                .append("quoteAsset", quoteAsset).append("pricePrecision", pricePrecision)
                .append("quantityPrecision", quantityPrecision).append("baseAssetPrecision", baseAssetPrecision)
                .append("quotePrecision", quotePrecision).append("orderTypes", orderTypes)
                .append("timeInForce", timeInForce).append("filters", filters).toString();
    }
}
