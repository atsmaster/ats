package sam.mon.assemble.model.enums;

import com.binance.client.model.enums.CandlestickInterval;

public enum CandleInterval {
	ONE_MINUTE("1m", CandlestickInterval.ONE_MINUTE);
//    THREE_MINUTES("3m"),
//    FIVE_MINUTES("5m"),
//    FIFTEEN_MINUTES("15m"),
//    HALF_HOURLY("30m"),
//    HOURLY("1h"),
//    TWO_HOURLY("2h"),
//    FOUR_HOURLY("4h"),
//    SIX_HOURLY("6h"),
//    EIGHT_HOURLY("8h"),
//    TWELVE_HOURLY("12h"),
//    DAILY("1d"),
//    THREE_DAILY("3d"),
//    WEEKLY("1w"),
//    MONTHLY("1M");

    private final String code;
    private final CandlestickInterval candlestickInterval;

    CandleInterval(String code, CandlestickInterval candlestickInterval) {
        this.code = code;
        this.candlestickInterval = candlestickInterval;
    }

    public String getCode() {
        return code;
    }
    
    public CandlestickInterval getCandlestickInterval() {
        return candlestickInterval;
    }
}
